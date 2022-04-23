import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Credenziali_servizi extends Credenziali implements Encrypt_decrypt_info {

    private String url, servizio, tag;

    public int getUser_id() {
        return user_id;
    }

    private int user_id;

    // creo l'oggetto dal database
    public Credenziali_servizi(int id, int user_id, int robustezza, int pwnd, String username, String password, String url, String servizio, String tag) {
        super(id, robustezza, pwnd, username, password);
        this.url = url;
        this.servizio = servizio;
        this.tag = tag;
    }

    //OVERLOADING per quando creo una nuova credenziale, non posso sapere parametri come l'id visto che se ne occupa il db. TRAMITE INTERFACCIA GRAFICA
    public Credenziali_servizi(int user_id, int robustezza, String username, String password, String url, String servizio) {
        super(username, password);
        this.url = url;
        this.servizio = servizio;
        this.user_id = user_id;

    }

    public Credenziali_servizi(int user_id, String username, String password, String servizio, String url) {
        super(username, password);
        this.url = url;
        this.servizio = servizio;
        this.user_id = user_id;
    }


    public String getUrl() {
        return url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServizio() {
        return servizio;
    }

    public void setServizio(String servizio) {
        this.servizio = servizio;
    }

    @Override
    public String Encrypt(String user_pass) {
        Encrypt_Decrypt Crypt_pass = null;
        Crypt_pass = new Encrypt_Decrypt(Cipher.ENCRYPT_MODE, user_pass);
        return Crypt_pass.Encrypt(this.getPassword());
    }

    @Override
    public String Decrypt(String user_pass) {
        Encrypt_Decrypt Decrypt_pass = null;
        Decrypt_pass = new Encrypt_Decrypt(Cipher.DECRYPT_MODE, user_pass);
        return Decrypt_pass.Decrypt(this.getPassword());
    }
}
