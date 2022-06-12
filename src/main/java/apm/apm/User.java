package apm.apm;

import javax.crypto.Cipher;

public class User extends Credenziali {
    private String nome, cognome, salt, encr_key;
    archivio_note note;
    Keychain portachiavi;
    SQLite_agent db_agent;


    // quando faccio il login e mi serve l'oggetto User completo
    User(int id, int robustezza, int pwnd, String username, String password, String nome, String cognome, String salt, String encr_key) {
        super(id, robustezza, pwnd, username, password);
        this.nome = nome;
        this.cognome = cognome;
        this.salt = salt;
        this.encr_key = encr_key; // in questa fase è ancora criptata
        this.db_agent = new SQLite_agent();
        portachiavi = new Keychain(this);
        note = new archivio_note(this);
    }

    //overload constructor
    // nella fase di registrazione
    User(String username, String password, String nome, String cognome) {
        super(username, password);
        this.nome = nome;
        this.cognome = cognome;

    }

    //metodi getter e setter per accedere e settare i dati private
    public String getEncr_key() {
        return encr_key;
    }
    public void setEncr_key(String encr_key) {
        this.encr_key = encr_key;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }

    //override del metodo fornito dall'interfaccia Encrypt_decrypt_info
    //consente di criptare la master_key con la password dell'utente
    @Override
    public void Encrypt(String user_pass) {
        Encrypt_Decrypt crypt = new Encrypt_Decrypt(Cipher.ENCRYPT_MODE, user_pass);
        this.encr_key = crypt.Encrypt(encr_key);
    }

    //override del metodo fornito dall'interfaccia Encrypt_decrypt_info
    //consente di decriptare la master_key con la password dell'utente
    @Override
    public void Decrypt(String user_pass) {
        Encrypt_Decrypt decrypt = new Encrypt_Decrypt(Cipher.DECRYPT_MODE, user_pass);
        this.encr_key = decrypt.Decrypt(encr_key);
    }

    //metodo che consente di aggiornare la password del singolo utente
    @Override
    void update_credenziale(User logged) {
        AuthManager change_pass = new AuthManager();
        this.setSalt(change_pass.getSalt());
        String hashed_pass = change_pass.get_SecurePassword(this.getPassword(), this.getSalt());
        //this.setPassword(new_pass);
        Encrypt_Decrypt encrypt_pass = new Encrypt_Decrypt(Cipher.ENCRYPT_MODE, this.getPassword()); //cifrario con la nuova password
        this.setEncr_key(encrypt_pass.Encrypt(encr_key));
        db_agent.update_user_pass(this, hashed_pass);

    }

    //metodo che consente di rimuovere un utente, eliminando tutti i dati ad esso associati: credenziali, note
    void self_unregister() {
        portachiavi.removeAll();
        note.removeAll();
        AuthManager authManager = new AuthManager();
        authManager.removeUser(getId());
    }
}
