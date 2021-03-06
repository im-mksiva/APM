package apm.apm;

import javax.crypto.Cipher;

public class note implements Encrypt_decrypt_info {

    private String testo, nome, tag, last_modified;
    private int id, user_id;

    note(String testo, String tag, String nome, int user_id) {
        this.testo = testo;
        this.tag = tag;
        this.nome = nome;
        this.user_id = user_id;
    }

    note(int id, int user_id, String tag, String testo, String last_modifed, String nome) {
        this.testo = testo;
        this.tag = tag;
        this.nome = nome;
        this.id = id;
        this.user_id = user_id;
        this.last_modified = last_modifed;
    }

    public String getTesto() {
        return testo;
    }
    public void setTesto(String testo) {
        this.testo = testo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getLast_modified() {
        return last_modified;
    }
    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {this.id = id;}
    public int getUser_id() {
        return this.user_id;
    }

    void update(User user) {
        this.Encrypt(user.getEncr_key());
        user.db_agent.updateNote(this);
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


}
