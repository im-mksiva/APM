import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// questa classe l'obiettivo di creare e inizializzare i database,
// che siano quelli di supporto per APM o quelli propri degli utenti

//public class db_init {
//    Connection connection;
//
//    db_init() {
//        try {
//            String db = "jdbc:sqlite:/home/mksiva/IdeaProjects/APM/APM/database/APM.db";
//            this.connection = DriverManager.getConnection(db);
//            Statement statement = connection.createStatement();
//            String query = "CREATE TABLE USERS_APM(\n" +
//                    "user_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
//                    "username varchar(30) not null,\n" +
//                    "password varchar(30) not null,\n" +
//                    "salt varchar(30) not null,\n" +
//                    "nome varchar(30) not null,\n" +
//                    "cognome varchar(30) not null\n" +
//                    ", robustezza integer default 0, pwnd integer default 0);";
//            statement.executeUpdate(query);
//
//
//            String query2 = "CREATE TABLE \"CREDENZIALI\" (\n" +
//                    "\t\"id\"\tINTEGER,\n" +
//                    "\t\"user_apm\" int,\n" +
//                    "\t\"url\"\tvarchar(50) NOT NULL,\n" +
//                    "\t\"service\"\tvarchar(20),\n" +
//                    "\t\"username\"\tvarchar(50) NOT NULL,\n" +
//                    "\t\"password\"\tvarchar(50) NOT NULL,\n" +
//                    "\t\"strenght\"\tINT DEFAULT 0,\n" +
//                    "\t\"pwnd\"\tINT DEFAULT 0,\n" +
//                    "\tPRIMARY KEY(\"id\"),\n" +
//                    "\tFOREIGN KEY (user_apm) references users_apm(user_id)\n" +
//                    ");";
//            statement = connection.createStatement();
//            statement.executeUpdate(query2);
//            connection.close();
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//
//        }
//    }


// in questo caso serve per creare il db degli utenti che fanno uso del software, APM.db
//}
