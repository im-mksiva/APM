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
    public String Encrypt(String user_pass) {
        Encrypt_Decrypt decrypt = new Encrypt_Decrypt(Cipher.ENCRYPT_MODE, user_pass);
        return decrypt.Decrypt(this.testo);
    }

    @Override
    public String Decrypt(String user_pass) {
        Encrypt_Decrypt decrypt = new Encrypt_Decrypt(Cipher.DECRYPT_MODE, user_pass);
        return decrypt.Decrypt(this.testo);
    }
}
