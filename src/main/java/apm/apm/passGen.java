package apm.apm;

import org.passay.*;

public class passGen {

    //metodo che genera una password robusta
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

}
