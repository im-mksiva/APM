package apm.apm;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public abstract class Credenziali implements Encrypt_decrypt_info {
    private int id, robustezza, pwnd;
    private String username, password;

    Credenziali(int id, int robustezza, int pwnd, String username, String password) {
        this.id = id;
        this.robustezza = robustezza;
        this.pwnd = pwnd;
        this.username = username;
        this.password = password;
    }


    //OVERLOADING
    Credenziali(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected void RobustezzaPass() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRobustezza() {
        return robustezza;
    }

    public void setRobustezza(int robustezza) {
        this.robustezza = robustezza;
    }

    public int getPwnd() {
        return pwnd;
    }

    public void setPwnd(int pwnd) {
        this.pwnd = pwnd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected void check_pwnd() throws NoSuchAlgorithmException, IOException {
        HaveIBeenPwned tester = new HaveIBeenPwned();
        pwnd = tester.valuta_password(this.password);
    }


}
