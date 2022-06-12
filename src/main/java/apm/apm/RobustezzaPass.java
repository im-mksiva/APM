package apm.apm;

import org.passay.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RobustezzaPass {

    //metodo che valuta la robustezza di una password fornita in input
    ArrayList<Integer> valutazione(String password_da_valutare) {
        List<Rule> lista_regole = new ArrayList<>();
        //lunghezza password min 8 max 100
        lista_regole.add(new LengthRule(8, 500));
        //la password deve contenere almeno un carattere alfabetico maiuscolo
        lista_regole.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
        //la password deve contenere almeno un numero
        lista_regole.add(new CharacterRule(EnglishCharacterData.Digit, 1));
        //la password deve contenere almeno un carattere speciale
        lista_regole.add(new CharacterRule(EnglishCharacterData.Special, 1));
        ArrayList<Integer> val_finale_pass = new ArrayList<>();
        PasswordData password = new PasswordData(password_da_valutare);
        for (Rule singola_regola : lista_regole) {
            RuleResult esito_regola = singola_regola.validate(password);
            if (esito_regola.isValid()) {
                val_finale_pass.add(1);
            } else {
                val_finale_pass.add(0);
            }
        }

        password_da_valutare = password_da_valutare.toLowerCase();
        String[] lista = password_da_valutare.split("[^a-z]");
        List<String> new_lista = new ArrayList<String>(Arrays.asList(lista));
        new_lista.removeAll(Collections.singleton(""));
        boolean esito = false;
        int count = 0;
        while (esito == false && count < new_lista.size()) {
            esito = parole_comuni(new_lista.get(count));
            count++;
            //System.out.println(new_lista.get(count));
        }

        if (!esito) {
            val_finale_pass.add(1);
        } else {
            val_finale_pass.add(0);
        }

        return val_finale_pass;
    }

    //metodo utilizzato dal metodo valutazione di questa classe che valuta,
    //controllando in una tabella del DB, se una password contiene parole comuni.
    private boolean parole_comuni(String password_da_valutare) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\calog\\IdeaProjects\\test\\database\\APM.db");
            String sql = "select * from DIZIONARIO where vocabolo = ?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, password_da_valutare);
            ResultSet result = query.executeQuery();
            result.getInt("ID");
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}