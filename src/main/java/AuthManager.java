import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;


public class AuthManager {

    public static void userRegister(String username, String pass) {
        // creazione del digest a partire da password+salt
        String salt = getSalt(); // miglioramento della sicurezza
        String hashed_pass = get_SecurePassword(pass, salt);
        // inserimento del nuovo utente in APM.db
        SQLite_agent db_agent = new SQLite_agent();
        db_agent.insertUser(username, hashed_pass, salt);

    }

    public static boolean userLogin(String username, String password) {
        SQLite_agent db_agent = new SQLite_agent();
        boolean user_exist = db_agent.find_user(username);
        if (user_exist == false) {
            System.out.println("username non trovato");
            return false;
        }

        String hashed_pass = db_agent.get_User_Hash(username);
        String user_salt = db_agent.get_Salt(username);
        String login_hashed_pass = get_SecurePassword(password, user_salt);
        // questo metodo di comparazione me l'ha suggerito IntelliJ, credo che le stringhe si possano confrontare solo in questo modo
        // dato che sono oggetti in java
        if (Objects.equals(login_hashed_pass, hashed_pass)) {
            System.out.println("Login ok");
            return true;
        } else {
            System.out.println("password non corretta");
        }
        return false;

    }


    // spazio alle funzioni crittografiche
    // questo metodo mi consente di poter calcolare l'hash SHA-512 facendo uso di un salt generato al momento
    // il salt poi lo conservo in APM.db
    private static String get_SecurePassword(String UserPasswordToHash, String salt) {
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
    private static String getSalt() {
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] salt = new byte[16];
        Objects.requireNonNull(sr).nextBytes(salt);
        return Arrays.toString(salt);
    }


}
