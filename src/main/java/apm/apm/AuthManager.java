package apm.apm;

import javax.crypto.Cipher;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Objects;


public class AuthManager {
    SQLite_agent db_agent;
    //costruttore che crea un oggetto di tipo DB agent
    AuthManager (){
        this.db_agent = new SQLite_agent();
    }

    //metodo che consente la registrazione del singolo utente
    boolean userRegister(User new_user) {
        // verifica se esiste già uno username uguale, se si restituisce un messaggio di errore nella GUI
        if (db_agent.getUser(new_user.getUsername()) != null) {
            System.out.println("l'utente c'è già");
            return false;
        }
        String user_pass = new_user.getPassword();
        // creazione del digest a partire da password+salt
        new_user.setSalt(getSalt()); // miglioramento della sicurezza
        new_user.setPassword(get_SecurePassword(new_user.getPassword(), new_user.getSalt()));
        new_user.setPwnd(0);
        new_user.setRobustezza(0);
        //generazione della chiave di criptazione per l'utente
        String encr_key = getSalt().substring(0, 16);

        //inizializzazione del cifrario
        Encrypt_Decrypt crypt_text = new Encrypt_Decrypt(Cipher.ENCRYPT_MODE, user_pass);
        //criptazione della chiave master
        new_user.setEncr_key(crypt_text.Encrypt(encr_key));

        // inserimento del nuovo utente in APM.db
        db_agent.insertUser(new_user);
        try {
            db_agent.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    //metodo che consente all'utente l'accesso al sistema
    User userLogin(String username, String password) {
        User logged = db_agent.getUser(username);
        if (logged == null) {
            return null;
        }

        //genero l'hash della password inserita dell'utente
        String login_hashed_pass = get_SecurePassword(password, logged.getSalt());
        //si confronta l'hash della pass appena inserita dall'utente con l'hash della pass presente del DB
        if (Objects.equals(login_hashed_pass, logged.getPassword())) {
            logged = db_agent.getUser(username);
            logged.Decrypt(password);
            return logged;
        }
        return null;
    }

    //metodo che consente la rimozione di un utente dal sistema
    void removeUser(int user_id) {
        db_agent.deleteRecord(user_id, "users_apm", "user_id");
    }

    // spazio alle funzioni crittografiche
    // questo metodo mi consente di poter calcolare l'hash SHA-512 facendo uso di un salt generato al momento
    // il salt poi lo conservo in APM.db
    String get_SecurePassword(String UserPasswordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(UserPasswordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    //metodo che consente la creazione un salt
    String getSalt() {
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] salt = new byte[16];
        Objects.requireNonNull(sr).nextBytes(salt);
        String salt_as_string = Base64.getEncoder().encodeToString(salt);

        return salt_as_string;

    }
}
