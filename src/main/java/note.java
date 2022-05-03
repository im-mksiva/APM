import javax.crypto.Cipher;
import java.util.Date;

public class note implements Encrypt_decrypt_info {

    String testo, nome;
    int tag;
    private int id, user_id;
    private Date last_modifed;

    public note(String testo, int tag, String nome) {
        this.testo = testo;
        this.tag = tag;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public Date getLast_modifed() {
        return last_modifed;
    }


    @Override
    public void Encrypt(String encr_key) {
        Encrypt_Decrypt crypt = new Encrypt_Decrypt(Cipher.ENCRYPT_MODE, encr_key);
        this.testo = crypt.Encrypt(this.testo);
    }

    @Override
    public void Decrypt(String encr_key) {
        Encrypt_Decrypt decrypt = new Encrypt_Decrypt(Cipher.DECRYPT_MODE, encr_key);
        this.testo = decrypt.Decrypt(this.testo);
    }

    void update(SQLite_agent db_agent) {
        
    }
}
