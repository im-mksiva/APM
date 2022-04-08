import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;

import java.util.ArrayList;

public class APM {
    public static void main(String[] args) {
        SQLite_agent db_agent = new SQLite_agent();
        db_agent.getValue("pietro");
        db_agent.insertValue("Calogero");
    }
}

