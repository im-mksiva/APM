import org.passay.*;
import org.passay.LengthRule;

import java.util.ArrayList;
import java.util.List;

public class passGen {

    List<Rule> addRules(){
        // creo una lista di regole che dovranno essere seguite dalla password
        List<Rule> regole = new ArrayList<>();
        // aggiungiamo una serie di regole
        regole.add(new LengthRule(8, 16));
        regole.add(new CharacterRule(EnglishCharacterData.Digit, 1));
        return regole;
    }


    // ------------ OVERLOADING ------------

    String genPass(int lunghezza){
        // regole per la password generata
        CharacterRule alphabets = new CharacterRule(EnglishCharacterData.Alphabetical);
        CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
//        CharacterRule special = new CharacterRule(EnglishCharacterData.Special);
        // oggetto che crea le password
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        // creazione sulla base delle regole
        return passwordGenerator.generatePassword(lunghezza, alphabets, digits);
    }

    // ------------ OVERLOADING ------------

    String genPass(int lunghezza, ArrayList<CharacterRule> test){
        // regole per la password generata
//        CharacterRule alphabets = new CharacterRule(EnglishCharacterData.Alphabetical);
//        CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
//        CharacterRule special = new CharacterRule(EnglishCharacterData.Special);
        // oggetto che crea le password
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        // creazione sulla base delle regole
        return passwordGenerator.generatePassword(lunghezza, test);
    }


}
