package apm.apm;

import javax.crypto.Cipher;

public class Credenziali_servizi extends Credenziali {

    private String url, servizio, tag;
    private int user_id;

    //costruttore
    Credenziali_servizi(int id, int user_id, int robustezza, int pwnd, String username, String password, String url, String servizio, String tag) {
        super(id, robustezza, pwnd, username, password);
        this.url = url;
        this.servizio = servizio;
        this.tag = tag;
        this.user_id = user_id;
    }

    //OVERLOADING del costruttore
    Credenziali_servizi(int user_id, int robustezza, String username, String password, String url, String servizio) {
        super(username, password);
        this.url = url;
        this.servizio = servizio;
        this.user_id = user_id;
        this.setRobustezza(robustezza);
    }

    //metodi getter e setter per accedere e settare le variabili private
    public int getUser_id() {
        return user_id;
    }
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
    public void setServizio(String servizio) {
        this.servizio = servizio;
    }

    //metodo che consente di aggiornare la password di un determinato servizio
    @Override
    void update_credenziale(User logged) {
        SQLite_agent db_agent = new SQLite_agent();
        Encrypt_Decrypt encrypt = new Encrypt_Decrypt(Cipher.ENCRYPT_MODE, logged.getEncr_key());
        this.setPassword(encrypt.Encrypt(this.getPassword()));
        db_agent.updateCredential(this);
    }

    //override del metodo fornito dall'interfaccia Encrypt_decrypt_info
    //consente di criptare la password del singolo servizio
    @Override
    public void Encrypt(String encr_key) {
        Encrypt_Decrypt Crypt_pass = null;
        Crypt_pass = new Encrypt_Decrypt(Cipher.ENCRYPT_MODE, encr_key);
        this.setPassword(Crypt_pass.Encrypt(this.getPassword()));
    }

    //override del metodo fornito dall'interfaccia Encrypt_decrypt_info
    //consente di decriptare la password del singolo servizio
    @Override
    public void Decrypt(String encr_key) {
        Encrypt_Decrypt Decrypt_pass = null;
        Decrypt_pass = new Encrypt_Decrypt(Cipher.DECRYPT_MODE, encr_key);
        this.setPassword(Decrypt_pass.Decrypt(this.getPassword()));
    }


}
