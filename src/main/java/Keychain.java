import java.io.IOException;
import java.security.cert.CertSelector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Keychain extends base_operations {
    private User user;
    private ArrayList<Credenziali_servizi> lista_credenziali;

    Keychain(User user) {
        this.user = user;
    }

    public ArrayList<Credenziali_servizi> getLista_credenziali() {
        return lista_credenziali;
    }


    @Override
    public void add(Object credenziale) {
        SQLite_agent db_agent = new SQLite_agent();
        Credenziali_servizi cred_da_inserire = (Credenziali_servizi) credenziale;
        cred_da_inserire.Encrypt(user.getEncr_key());
        db_agent.insertCredential(cred_da_inserire);
        db_agent.closeConnection();
    }

    @Override
    public void remove(Object credenziale) {
        SQLite_agent db_agent = new SQLite_agent();
        Credenziali_servizi toRemove = (Credenziali_servizi) credenziale;
        db_agent.deleteRecord(toRemove.getId(), "credenziali", "id");
        db_agent.closeConnection();

    }

    @Override
    void removeAll() {
        SQLite_agent db_agent = new SQLite_agent();
        db_agent.deleteRecord(user.getId(), "credenziali", "user_id");
        db_agent.closeConnection();
    }

    @Override
    public ArrayList find(String text) {
        SQLite_agent db_agent = new SQLite_agent();
        ResultSet result = db_agent.searchCredential(text, user.getId());
        ArrayList<Credenziali_servizi> lista = new ArrayList<>();
        while (true) {
            try {
                if (!result.next()) {
                    Credenziali_servizi credenziale = new Credenziali_servizi(
                            result.getInt("id"),
                            result.getInt("user_id"),
                            result.getInt("strenght"),
                            result.getInt("pwnd"),
                            result.getString("username"),
                            result.getString("password"),
                            result.getString("url"),
                            result.getString("servizio"),
                            result.getString("tag")
                    );
                    // sblocco la password così l'utente se vuole la può vedere
                    credenziale.Decrypt(user.getEncr_key());
                    lista.add(credenziale);
                }
                db_agent.closeConnection();
                return lista;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    void import_csv(String percorso) throws IOException {
        gestione_file_csv insert = new gestione_file_csv();
        insert.import_file(percorso, user.getId());
    }

    void listCredential() {

    }

    void export_csv() throws IOException, SQLException {
        SQLite_agent db_agent = new SQLite_agent();
        ResultSet results = db_agent.get_all_Credential(user.getId());
        gestione_file_csv export_file = new gestione_file_csv();
        String username = user.getUsername();
        export_file.export_file(results, username);

    }
}

