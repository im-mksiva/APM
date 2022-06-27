package apm.apm;

import javax.crypto.Cipher;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Objects;


public class AuthManager {
    private SQLite_agent db_agent;
    AuthManager (){
        this.db_agent = new SQLite_agent();
    }

    boolean userRegister(User new_user) {
        if (db_agent.getUser(new_user.getUsername()) != null) {
            System.out.println("l'utente c'è già");
            return false;
        }
        String user_pass = new_user.getPassword();
        new_user.setSalt(getSalt());
        new_user.setPassword(get_SecurePassword(new_user.getPassword(), new_user.getSalt()));
        new_user.setPwnd(0);
        new_user.setRobustezza(0);
        String encr_key = getSalt().substring(0, 16);

        Encrypt_Decrypt crypt_text = new Encrypt_Decrypt(Cipher.ENCRYPT_MODE, user_pass);
        new_user.setEncr_key(crypt_text.Encrypt(encr_key));

        db_agent.insertUser(new_user);
        try {
            db_agent.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    User userLogin(String username, String password) {
        User logged = db_agent.getUser(username);
        if (logged == null) {
            return null;
        }

        String login_hashed_pass = get_SecurePassword(password, logged.getSalt());

        if (Objects.equals(login_hashed_pass, logged.getPassword())) {
            //logged = db_agent.getUser(username);
            logged.Decrypt(password);
            return logged;
        }
        return null;
    }



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















    void removeUser(int user_id) {
        db_agent.deleteRecord(user_id, "users_apm", "user_id");
    }

    String get_SecurePassword(String UserPasswordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(UserPasswordToHash.getBytes());
            generatedPassword= Base64.getEncoder().encodeToString(bytes);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
















}
