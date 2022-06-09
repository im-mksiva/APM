package apm.apm;

import org.passay.*;
import org.passay.LengthRule;

import java.util.ArrayList;
import java.util.List;

public class passGen {

    // ------------ OVERLOADING ------------

    String genPass(int lunghezza){
        // regole per la password generata
        CharacterRule alphabets = new CharacterRule(EnglishCharacterData.Alphabetical);
        CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
        CharacterRule special = new CharacterRule(new CharacterData() {
            @Override
            public String getErrorCode() {
                return null;
            }

            @Override
            public String getCharacters() {
                return "\"!\\\"#$%&'()*+,-./:;<=>?@[\\\\]^_`{|}";
            }
        });
        // oggetto che crea le password
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        // creazione sulla base delle regole
        return passwordGenerator.generatePassword(lunghezza, alphabets, digits, special);
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
