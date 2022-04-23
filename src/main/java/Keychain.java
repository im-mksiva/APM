import com.opencsv.CSVWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    void export_csv() throws IOException, SQLException {
        ResultSet results = db.get_all_Credential(getKeychain_id());
        gestione_file_csv export_file = new gestione_file_csv();
        String username = db.get_username(getKeychain_id());
        export_file.export_file(results, username);
    }
}

