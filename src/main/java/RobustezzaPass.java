import org.passay.*;

public class RobustezzaPass {

    PasswordValidator Validator = new PasswordValidator(
            new LengthRule(8, 10),
            new CharacterRule(EnglishCharacterData.LowerCase),
            new WhitespaceRule()
    );

    void prova(){
        System.out.println("ciao");
    }

}
