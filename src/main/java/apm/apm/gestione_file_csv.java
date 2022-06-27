package apm.apm;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class gestione_file_csv {

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

    private int indice_campo(String[] prima_riga, String nome_campo) {
        List<String> lista_campi = Arrays.asList(prima_riga);
        return lista_campi.indexOf(nome_campo);
    }

    private Map<String, Integer> dizionario_indice(String[] prima_riga) {
        Map<String, Integer> indici_campi = new HashMap<String, Integer>();
        indici_campi.put("name", -1);
        indici_campi.put("url", -1);
        indici_campi.put("username", -1);
        indici_campi.put("password", -1);
        String[] nome_campi = {"name", "url", "username", "password"};
        for (String elem : nome_campi) {
            indici_campi.put(elem, indice_campo(prima_riga, elem));
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

    void import_file(String percorso) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(percorso));
            String line = reader.readLine();
            String[] prima_riga = separa_stringa(line);
            Map<String, Integer> diz_indici = dizionario_indice(prima_riga);
            line = reader.readLine();
            while (line != null) {
                String[] riga_successiva = separa_stringa(line);
                Map<String, String> diz_ins = inserimento_campi(riga_successiva, diz_indici);
                String username = diz_ins.get("username");
                String password = diz_ins.get("password");
                String nome_servizio = diz_ins.get("name");
                String url = diz_ins.get("url");
                RobustezzaPass valutazione_pass = new RobustezzaPass();
                ArrayList<Integer> esito_valutazione = valutazione_pass.valutazione(password);
                int somma_val = 0;
                for(int elem : esito_valutazione){
                    somma_val+=elem;
                }
                Credenziali_servizi nuovo_servizio = new Credenziali_servizi(APM.user.getId(), somma_val, username, password, url, nome_servizio);
                APM.user.portachiavi.add(nuovo_servizio);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    void export_file(ArrayList<Credenziali_servizi> lista_credenziali, String percorso){
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(percorso));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("name,url,username,password"));
            for(Credenziali_servizi elem : lista_credenziali){
                csvPrinter.printRecord(elem.getServizio(),elem.getUrl(),elem.getUsername(),elem.getPassword());
            }
            csvPrinter.flush();
            csvPrinter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
