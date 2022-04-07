import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;

import java.util.ArrayList;

public class APM {
    public static void main(String[] args) {
        passGen generatore = new passGen();
        ArrayList<CharacterRule> test = new ArrayList<>();
        test.add(new CharacterRule(EnglishCharacterData.Special));
        test.add(new CharacterRule(EnglishCharacterData.Digit));
        test.add(new CharacterRule(EnglishCharacterData.Alphabetical));
        test.add(new CharacterRule(EnglishCharacterData.LowerCase));
        test.add(new CharacterRule(EnglishCharacterData.UpperCase));

        String password = generatore.genPass(22, test);
        System.out.println(password);
    }
}

