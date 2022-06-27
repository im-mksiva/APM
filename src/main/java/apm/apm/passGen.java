package apm.apm;

import org.passay.*;

public class passGen {

    String genPass(int lunghezza){
        CharacterRule alphabets = new CharacterRule(EnglishCharacterData.Alphabetical);
        CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
        CharacterRule special = new CharacterRule(new CharacterData() {
            @Override
            public String getErrorCode() {
                return null;
            }
            @Override
            public String getCharacters() {
                return "!\\#$%&'()*+,-./:;<=>?@[\\\\]^_`{|}";
            }
        });
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        return passwordGenerator.generatePassword(lunghezza, alphabets, digits, special);
    }

}
