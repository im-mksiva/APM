package apm.apm;

import javax.crypto.Cipher;

public class Credenziali_servizi extends Credenziali {

    private String url, servizio, tag;
    private int user_id;

    Credenziali_servizi(int id, int user_id, int robustezza, int pwnd, String username, String password, String url, String servizio, String tag) {
        super(id, robustezza, pwnd, username, password);
        this.url = url;
        this.servizio = servizio;
        this.tag = tag;
        this.user_id = user_id;
    }

    Credenziali_servizi(int user_id, int robustezza, String username, String password, String url, String servizio) {
        super(username, password, robustezza);
        this.url = url;
        this.servizio = servizio;
        this.user_id = user_id;
    }

    public int getUser_id() { return user_id; }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getServizio() {
        return servizio;
    }
    public void setServizio(String servizio) { this.servizio = servizio; }

    @Override
    void update_credenziale(User logged) {
        Encrypt_Decrypt encrypt = new Encrypt_Decrypt(Cipher.ENCRYPT_MODE, logged.getEncr_key());
        String prev = this.getPassword();
        this.setPassword(encrypt.Encrypt(this.getPassword()));
        logged.db_agent.updateCredential(this);
        this.setPassword(prev);
    }

    @Override
    public void Encrypt(String encr_key) {
        Encrypt_Decrypt Crypt_pass = null;
        Crypt_pass = new Encrypt_Decrypt(Cipher.ENCRYPT_MODE, encr_key);
        this.setPassword(Crypt_pass.Encrypt(this.getPassword()));
    }

    @Override
    public void Decrypt(String encr_key) {
        Encrypt_Decrypt Decrypt_pass = null;
        Decrypt_pass = new Encrypt_Decrypt(Cipher.DECRYPT_MODE, encr_key);
        this.setPassword(Decrypt_pass.Decrypt(this.getPassword()));
    }


}
