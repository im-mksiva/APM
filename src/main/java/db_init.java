import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// questa classe l'obiettivo di creare e inizializzare i database,
// che siano quelli di supporto per APM o quelli propri degli utenti

public class db_init {
    Connection connection;
    // in questo caso serve per creare il db degli utenti che fanno uso del software, APM.db
    db_init(){
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:/home/mksiva/IdeaProjects/APM/APM/database/APM.db");
            Statement statement = connection.createStatement();
            String query = "create table users (username varchar(30),passkey password, salt varchar(10))";
            statement.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    // OVERLOADING - questo serve per contenere le credenziali degli utenti
    db_init(String user_id){
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:/home/mksiva/IdeaProjects/APM/APM/database/" + user_id + ".db");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
