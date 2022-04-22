import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Keychain implements base_operations {
    private int keychain_id;
    private ArrayList<Credenziali_servizi> lista_credenziali;
    private SQLite_agent db;

    Keychain(int user_id) {
        db = new SQLite_agent();
        this.keychain_id = user_id;
    }

    public int getKeychain_id() {
        return keychain_id;
    }

    public ArrayList<Credenziali_servizi> getLista_credenziali() {
        return lista_credenziali;
    }


    @Override
    public void add() {

    }

    @Override
    public void remove() {

    }

    @Override
    public void update() {

    }

    @Override
    public void find() {

    }

    void import_csv(String percorso) throws IOException {
        gestione_file_csv insert = new gestione_file_csv();
        insert.import_file(percorso, getKeychain_id());
    }

    File export_csv() {
        return null;
    }
}

