package apm.apm;

import javax.crypto.Cipher;
import java.util.Date;

public class note implements Encrypt_decrypt_info {

    String testo, nome;
    String tag;
    private int id, user_id;
    private String last_modifed;

    public note(String testo, String tag, String nome) {
        this.testo = testo;
        this.tag = tag;
        this.nome = nome;
    }


    public note(int id, int user_id, String tag, String testo, String last_modifed, String nome) {
        this.testo = testo;
        this.tag = tag;
        this.nome = nome;
        this.id = id;
        this.user_id = user_id;
        this.last_modifed = last_modifed;
    }


    public int getId() {
        return id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public String getLast_modifed() {
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
