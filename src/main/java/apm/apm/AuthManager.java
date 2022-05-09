package apm.apm;

import javax.crypto.Cipher;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Objects;

public class AuthManager {

    public boolean userRegister(User new_user) {
        // verifica se esiste già uno username uguale, se si restituisce un messaggio di errore nella UI
        SQLite_agent db_agent = new SQLite_agent();
        if (db_agent.find_user(new_user.getUsername())) {
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


        Encrypt_Decrypt crypt_text = new Encrypt_Decrypt(Cipher.ENCRYPT_MODE, user_pass);
        new_user.setEncr_key(crypt_text.Encrypt(encr_key));

        // inserimento del nuovo utente in APM.db
        db_agent.insertUser(new_user);
        System.out.println("fatto");
        try {
            db_agent.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public User userLogin(String username, String password) {
        SQLite_agent db_agent = new SQLite_agent();
        boolean user_exist = db_agent.find_user(username);
        if (!user_exist) {
            System.out.println("username non trovato");
            return null;
        }

        String hashed_pass = db_agent.get_User_Hash(username);
        String user_salt = db_agent.get_Salt(username);
        String login_hashed_pass = get_SecurePassword(password, user_salt);
        // questo metodo di comparazione me l'ha suggerito IntelliJ, credo che le stringhe si possano confrontare solo in questo modo
        // dato che sono oggetti in java
        if (Objects.equals(login_hashed_pass, hashed_pass)) {
            System.out.println("Login ok");
            User logged = db_agent.getUser(username);
//            logged.setPassword(password);
            logged.Decrypt(password);
            return logged;
        } else {
            System.out.println("password non corretta");
        }
        return null;
    }


    void removeUser(int user_id) {
        SQLite_agent db_agent = new SQLite_agent();
        db_agent.deleteRecord(user_id, "users_apm", "user_id");
    }


    // spazio alle funzioni crittografiche
    // questo metodo mi consente di poter calcolare l'hash SHA-512 facendo uso di un salt generato al momento
    // il salt poi lo conservo in APM.db
    public String get_SecurePassword(String UserPasswordToHash, String salt) {
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

    // Add salt
    public String getSalt() {
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
