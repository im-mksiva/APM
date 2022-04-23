import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class gestione_file_csv {

    //prende la singola riga come stringa e restituisce un array di stringhe
    // in cui ciascun elemento è un campo del file
    private String[] separa_stringa(String riga) {
        String[] campi_riga = riga.split(",", 4);
        if (riga.contains("\"")) {
            for (int j = 0; j < campi_riga.length; j++) {
                if (!Objects.equals(campi_riga[j], "")) {
                    campi_riga[j] = campi_riga[j].substring(1, campi_riga[j].length() - 1);
                }
            }
        }
        return campi_riga;
    }

    //prende la prima riga per conoscere la struttura del file
    //trasforma l'array in una lista dinamica per poter usare il metodo indexof
    //il quale prende il ingresso un valore (passato come paramentro del metodo: nome_campo)
    //e restituisce l'indice della posizione in cui si trova nell'array
    private int indice_campo(String[] prima_riga, String nome_campo) {
        List<String> lista_campi = Arrays.asList(prima_riga);
        return lista_campi.indexOf(nome_campo);
    }

    //riceve un array di stringhe in cui ciascun elemento è un campo del file
    //viene creato un dizionario in modo da avere la corrispondenza tra un campo del file
    //e l'indice in cui si trova nell'array di stringhe
    private Map<String, Integer> dizionario_indice(String[] prima_riga) {
        //System.out.println("prima riga:" + prima_riga[0]);
        Map<String, Integer> indici_campi = new HashMap<String, Integer>();
        indici_campi.put("name", -1);
        indici_campi.put("url", -1);
        indici_campi.put("username", -1);
        indici_campi.put("password", -1);
        String[] nome_campi = {"name", "url", "username", "password"};
        for (String elem : nome_campi) {
            if (indici_campi.containsKey(elem)) {
                indici_campi.put(elem, indice_campo(prima_riga, elem));
                //System.out.println("dentro l'if hashmap:  " + indici_campi);
            }
        }
        return indici_campi;
    }

    private Map<String, String> inserimento_campi(String[] riga, Map<String, Integer> diz_indici) {
        String[] nome_campi = {"name", "url", "username", "password"};
        Map<String, String> dizionario_inserimento = new HashMap<String, String>();
        for (String elem : nome_campi) {
            if (diz_indici.get(elem) != -1) {
                dizionario_inserimento.put(elem, riga[diz_indici.get(elem)]);
            } else {
                dizionario_inserimento.put(elem, null);
            }
        }
        return dizionario_inserimento;
    }

    void import_file(String percorso, int Keychain_id) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(percorso));
        //prima riga -> struttura del file
        String line = reader.readLine();
        String[] prima_riga = separa_stringa(line);
        Map<String, Integer> diz_indici = dizionario_indice(prima_riga);
        //System.out.println("dizionario indici:\n" + diz_indici);
        //dalla seconda riga in poi
        line = reader.readLine();
        while (line != null) {
            //System.out.println("line prima di essere spezzata " + line);
            String[] riga_successiva = separa_stringa(line);
            Map<String, String> diz_ins = inserimento_campi(riga_successiva, diz_indici);
            //aggiungere metodo per inserimento dati nel DB
            String username = diz_ins.get("username");
            String password = diz_ins.get("password");
            String nome_servizio = diz_ins.get("name");
            String url = diz_ins.get("url");
            Credenziali_servizi nuovo_servizio = new Credenziali_servizi(Keychain_id, username, password, nome_servizio, url);
            SQLite_agent inserimento_servizio = new SQLite_agent();
            inserimento_servizio.insertCredential(nuovo_servizio);
            //System.out.println("\n\ndizionario inserimento dati:\n" + diz_ins);
            line = reader.readLine();
        }
    }

    void export_file(ResultSet results, String username) throws SQLException, IOException {
        // Open CSV file.
        BufferedWriter writer = Files.newBufferedWriter(Paths.get("C:\\Users\\calog\\IdeaProjects\\APM\\" + username + "_credenziali.csv"));

        // Add table headers to CSV file.
        int count = results.getMetaData().getColumnCount();
        String intestazione = new String();
        for (int i = 1; i < count; i++) {
            intestazione += results.getMetaData().getColumnName(i) + ",";
        }
        intestazione = intestazione.substring(0, intestazione.length() - 1);
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(intestazione));
        // Add data rows to CSV file.
        while (results.next()) {
            csvPrinter.printRecord(
                    results.getInt(1),
                    results.getString(2),
                    results.getString(3),
                    results.getString(4),
                    results.getString(5));
        }
        csvPrinter.flush();
        csvPrinter.close();
    }


}