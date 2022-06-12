package apm.apm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Keychain implements base_operations {
    private User user;
    //db agent prima era default
    private SQLite_agent db_agent;

    //costruttore
    Keychain(User user) {
        this.user = user;
        this.db_agent = user.db_agent;
    }

    //metodo che consente di ottenere la lista delle credenziali di uno specifico utente, identificato per mezzo dello user_id
    ArrayList getLista_credenziali() {
        ResultSet result = db_agent.get_all_Credential(user.getId());
        ArrayList<Credenziali_servizi> lista_credenziali = new ArrayList<>();
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista_credenziali;
    }

    //metodo che deriva dall'interfaccia base_operation
    //consente di aggiungere una credenziale ad un utente
    @Override
    public void add(Object credenziale) {
        Credenziali_servizi cred_da_inserire = (Credenziali_servizi) credenziale;
        cred_da_inserire.Encrypt(user.getEncr_key());
        db_agent.insertCredential(cred_da_inserire);
    }

    //metodo che deriva dall'interfaccia base_operation
    //consente di rimuovere una credenziale di un utente
    @Override
    public void remove(Object credenziale) {
        Credenziali_servizi toRemove = (Credenziali_servizi) credenziale;
        db_agent.deleteRecord(toRemove.getId(), "credenziali", "id");
    }

    //metodo che deriva dall'interfaccia base_operation
    //consente di rimuovere tutte le credenziali di un utente
    @Override
    public void removeAll() {
        db_agent.deleteRecord(user.getId(), "credenziali", "user_id");
    }

    //metodo che consente ad un utente di aggiungere delle credenziali del proprio spazio, importandole da un file con estensione ".csv"
    void import_csv(String percorso){
        gestione_file_csv insert = new gestione_file_csv();
        insert.import_file(percorso);
    }

    //metodo che consente ad un utente di esportare le proprie credenziali salvandole su un file con estensione ".csv"
    void export_csv(String percorso){
        gestione_file_csv export_file = new gestione_file_csv();
        export_file.export_file(this.getLista_credenziali(), percorso);
    }
}

