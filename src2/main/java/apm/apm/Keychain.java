package apm.apm;

import java.io.IOException;
import java.security.cert.CertSelector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Keychain extends base_operations {
    private User user;
    private ArrayList<Credenziali_servizi> lista_credenziali;
    SQLite_agent db_agent;

    Keychain(User user) {
        this.user = user;
        this.db_agent = user.db_agent;
        lista_credenziali = new ArrayList<>();
    }

    public ArrayList getLista_credenziali() {
        ResultSet result = db_agent.get_all_Credential(user.getId());
        boolean continua;
        try {
            continua = result.next();
            while (continua) {
                Credenziali_servizi credenziale = new Credenziali_servizi(
                        result.getInt("id"),
                        result.getInt("user_id"),
                        result.getInt("strenght"),
                        result.getInt("pwnd"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("url"),
                        result.getString("service"),
                        result.getString("tag")
                );
                // sblocco la password così l'utente se vuole la può vedere
                credenziale.Decrypt(user.getEncr_key());
                lista_credenziali.add( new credentialTableCell(credenziale));
                continua = result.next();

            }
            System.out.println(lista_credenziali.size());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista_credenziali;
    }


    @Override
    public void add(Object credenziale) {
        Credenziali_servizi cred_da_inserire = (Credenziali_servizi) credenziale;
        cred_da_inserire.Encrypt(user.getEncr_key());
        db_agent.insertCredential(cred_da_inserire);

    }

    @Override
    public void remove(Object credenziale) {

        Credenziali_servizi toRemove = (Credenziali_servizi) credenziale;
        db_agent.deleteRecord(toRemove.getId(), "credenziali", "id");


    }

    @Override
    void removeAll() {
        db_agent.deleteRecord(user.getId(), "credenziali", "user_id");
    }

    @Override
    public ArrayList find(String text) {

        ResultSet result = db_agent.search(text, user.getId(), 0);
        ArrayList<credentialTableCell> lista = new ArrayList<>();
        boolean continua;
        try {
            continua = result.next();
            while (continua) {
                Credenziali_servizi credenziale = new Credenziali_servizi(
                        result.getInt("id"),
                        result.getInt("user_id"),
                        result.getInt("strenght"),
                        result.getInt("pwnd"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("url"),
                        result.getString("service"),
                        result.getString("tag")
                );
                // sblocco la password così l'utente se vuole la può vedere
                credenziale.Decrypt(user.getEncr_key());
                lista.add(new credentialTableCell(credenziale));
                continua = result.next();
            }

            return lista;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    void import_csv(String percorso) throws IOException {
        gestione_file_csv insert = new gestione_file_csv();
        insert.import_file(percorso, user.getId());
    }

    void listCredential() {

    }

    void export_csv() throws IOException, SQLException {
        ResultSet results = db_agent.get_all_Credential(user.getId());
        gestione_file_csv export_file = new gestione_file_csv();
        String username = user.getUsername();
        export_file.export_file(results, username);

    }
}

