import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class AuthManager {

    public static void Register(String username, String pass) {
        // creazione del digest a partire da password+salt
        String salt = getSalt(); // miglioramento della sicurezza
        String hashed_pass = get_SecurePassword(pass,salt);
        System.out.println("L'hash della password è " + hashed_pass);
        System.out.println("il salt è " + salt);
        // inserimento del nuovo utente in APM.db
        SQLite_agent db_agent = new SQLite_agent();
        db_agent.insertUser(username,hashed_pass,salt);
    }

    public static void Login(String username, String password) {
        SQLite_agent db_agent = new SQLite_agent();
        String[] result = db_agent.get_User_Hash(username);
        String hashed_pass = get_SecurePassword(password,result[1]);
        // questo metodo di comparazione me l'ha suggerito IntelliJ, credo che le stringhe si possano confrontare solo in questo modo
        // dato che sono oggetti in java
        if (Objects.equals(result[0], hashed_pass)){
            System.out.println("Login funzionante");
        }
//        db_agebt.getValue("Users","salt",);




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
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
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
        sr.nextBytes(salt);
        return Arrays.toString(salt);
    }


}
