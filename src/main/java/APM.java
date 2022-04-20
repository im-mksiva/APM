import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class APM {
    public static void main(String[] args) {
        String percorso="C:\\Users\\calog\\IdeaProjects\\test\\src\\Password Chrome.csv";
        try {
            gestione_file_csv.import_file(percorso);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
