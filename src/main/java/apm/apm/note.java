package apm.apm;

import javax.crypto.Cipher;

public class note implements Encrypt_decrypt_info {

    private String testo, nome, tag, last_modified;
    private int id, user_id;

    //costruttore
    note(String testo, String tag, String nome, int user_id) {
        this.testo = testo;
        this.tag = tag;
        this.nome = nome;
        this.user_id = user_id;
    }

    //overloading del costruttore
    note(int id, int user_id, String tag, String testo, String last_modifed, String nome) {
        this.testo = testo;
        this.tag = tag;
        this.nome = nome;
        this.id = id;
        this.user_id = user_id;
        this.last_modified = last_modifed;
    }

    //metodi getter e setter per accedere e settare le variabili private
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
    public void setUser_id(int user_id) {this.user_id = user_id;}
    public int getUser_id() {
        return this.user_id;
    }

    //metodo che consente di aggiornare la nota di un utente
    void update(User user) {
        this.Encrypt(user.getEncr_key());
        user.db_agent.updateNote(this);
    }

    //override del metodo fornito dall'interfaccia Encrypt_decrypt_info
    //consente di criptare il testo della nota di un utente
    @Override
    public void Encrypt(String encr_key) {
        Encrypt_Decrypt crypt = new Encrypt_Decrypt(Cipher.ENCRYPT_MODE, encr_key);
        this.testo = crypt.Encrypt(this.testo);
    }

    //override del metodo fornito dall'interfaccia Encrypt_decrypt_info
    //consente di decriptare il testo della nota di un utente
    @Override
    public void Decrypt(String encr_key) {
        Encrypt_Decrypt decrypt = new Encrypt_Decrypt(Cipher.DECRYPT_MODE, encr_key);
        this.testo = decrypt.Decrypt(this.testo);
    }


}
