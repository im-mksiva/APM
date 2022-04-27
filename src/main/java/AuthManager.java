import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

//todo chiave master per rendere indipendente la chiave di criptazione dalla password dell'utente
//todo crittografia della chiave master con la password dell'utente
//todo meccanismo per la gestione della crittografia con password utente con un numero di caratteri diverso da 16

public class AuthManager {

    public boolean userRegister(User new_user) {
        // verifica se esiste già uno username uguale, se si restituisce un messaggio di errore nella UI
        SQLite_agent db_agent = new SQLite_agent();
        if (db_agent.find_user(new_user.getUsername())) {
            return false;
        }

        // creazione del digest a partire da password+salt
        new_user.setSalt(getSalt()); // miglioramento della sicurezza
        new_user.setPassword(get_SecurePassword(new_user.getPassword(), new_user.getSalt()));
        new_user.setPwnd(0);
        new_user.setRobustezza(0);
        // inserimento del nuovo utente in APM.db

//        System.out.println(username + hashed_pass + salt);
        if (!db_agent.insertUser(new_user)) {
            //riprovo
            db_agent.insertUser(new_user);
        }
        ;
        System.out.println("fatto");
        return true;
    }

    public User userLogin(String username, String password) {
        SQLite_agent db_agent = new SQLite_agent();
        boolean user_exist = db_agent.find_user(username);
        if (user_exist == false) {
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
            return db_agent.getUser(username);

        } else {
            System.out.println("password non corretta");
        }
        return null;

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
//        System.out.println(salt);
        Objects.requireNonNull(sr).nextBytes(salt);
        String s = new String(salt, StandardCharsets.UTF_8);
        System.out.println(s);
        return s;

    }
}
