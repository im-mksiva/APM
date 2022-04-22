public class User extends Credenziali {
    private String nome, cognome, salt;
    private archivio_note note;
    private Keychain portachiavi;

    // quando faccio il login e mi serve l'oggetto User completo
    public User(int id, int robustezza, int pwnd, String username, String password, String nome, String cognome, String salt) {
        super(id, robustezza, pwnd, username, password);
        this.nome = nome;
        this.cognome = cognome;
        this.salt = salt;
    }

    // nella fase di registrazione
    public User(String username, String password, String nome, String cognome) {
        super(username, password);
        this.nome = nome;
        this.cognome = cognome;
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

}
