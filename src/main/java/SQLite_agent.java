import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class SQLite_agent {
    // manages connection to sqlite db
    // Valutare se scomporre in due classi distinte, una per la gestione di APM, una per le credenziali dell'utente
    Connection connection = null;
    String db = "jdbc:sqlite:C:\\Users\\calog\\IdeaProjects\\APM\\APM\\database\\APM.db";

    SQLite_agent() {
        // verifico se il file del db esiste già. Se non c'è inizializzo il db con le tabelle necessarie
        File db_file = new File("C:\\Users\\calog\\IdeaProjects\\APM\\APM\\database\\APM.db");
        if (!db_file.exists()) {
            db_create_table_users();
            db_create_credential();
        }
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\calog\\IdeaProjects\\APM\\APM\\database\\APM.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void db_create_credential() {
        try {
            this.connection = DriverManager.getConnection(db);
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE \"CREDENZIALI\" (\n" +
                    "\t\"id\"\tINTEGER,\n" +
                    "\t\"user_apm\" int,\n" +
                    "\t\"url\"\tvarchar(50) NOT NULL,\n" +
                    "\t\"service\"\tvarchar(20),\n" +
                    "\t\"username\"\tvarchar(50) NOT NULL,\n" +
                    "\t\"password\"\tvarchar(50) NOT NULL,\n" +
                    "\t\"strenght\"\tINT DEFAULT 0,\n" +
                    "\t\"pwnd\"\tINT DEFAULT 0, tag VARCHAR(30),\n" +
                    "\tPRIMARY KEY(\"id\"),\n" +
                    "\tFOREIGN KEY (user_apm) references users_apm(user_id)\n" +
                    ");";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void db_create_table_users() {
        Statement statement = null;
        try {
            this.connection = DriverManager.getConnection(db);
            statement = connection.createStatement();
            String query = "CREATE TABLE USERS_APM(\n" +
                    "user_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "username varchar(30) UNIQUE not null,\n" +
                    "password varchar(30) not null,\n" +
                    "salt varchar(30) not null,\n" +
                    "nome varchar(30) not null,\n" +
                    "cognome varchar(30) not null\n" +
                    ", robustezza integer default 0, pwnd integer default 0);";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public boolean find_user(String username) {
        try {
            String sql = "select username from users where username = ?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, username);
            ResultSet result = query.executeQuery();
            System.out.println(result.getString("username"));

            if (result.getString("username") != null)
                return true;
        } catch (SQLException e) {
            // quando viene inserito uno username non trovato viene lanciata una eccezione (ResultSet closed)
            return false;
//            throw new RuntimeException(e);

        }
        return false;
    }

    //OVERLOADING

    public String get_username(int user_id) {
        try {
            String sql = "select username from USERS_APM where user_id = ?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setInt(1, user_id);
            ResultSet result = query.executeQuery();
            if (result.getString("username") != null) {
                return result.getString("username");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return null;
    }


    public String get_Salt(String username) {
        try {
            String sql = "select salt from users where username = ?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, username);
            ResultSet result = query.executeQuery();
            return result.getString("salt");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public String get_User_Hash(String username) {
        try {
            String sql = "select password from users where username = ?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, username);
            // executeQuery() serve per recuperare dati (esegue la query)
            return query.executeQuery().getString("password");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    void searchCredential(String word) {
        try {
            String sql = "select service, username, url from Credenziali where url like ? OR service like ?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, word);
            query.setString(2, word);
            ResultSet result = query.executeQuery(sql);
            result.getObject(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    ResultSet get_all_Credential(int user_id) {
        ResultSet result = null;
        try {
            String sql = "select * from CREDENZIALI where user_apm = ?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setInt(1, user_id);
            result = query.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    void insertCredential(Credenziali_servizi nuova_credenziale) {
        try {
            String sql = "insert into CREDENZIALI(user_apm, url, service, username, password) values (?,?,?,?,?)";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setInt(1, nuova_credenziale.getUser_id());
            query.setString(2, nuova_credenziale.getUrl());
            query.setString(3, nuova_credenziale.getServizio());
            query.setString(4, nuova_credenziale.getUsername());
            query.setString(5, nuova_credenziale.getPassword());
            // executeUpdate() serve per aggiornare lo stato del database, che sia inserimento o cancellazione
            query.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    boolean insertUser(User new_user) {
        try {
//            Statement statement = connection.createStatement();
            String sql = "insert into USERS_APM ('username','password','salt','nome','cognome') values (?,?,?,?,?)";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, new_user.getUsername());
            query.setString(2, new_user.getPassword());
            query.setString(3, new_user.getSalt());
            query.setString(4, new_user.getNome());
            query.setString(5, new_user.getCognome());

            query.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1) {
                System.out.println("Sto creando un nuovo db");
                //init
                return false;
            }
        }
        return true;
    }


    User getUser(String username) {
        try {
            String sql = "select * from users where username = ?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, username);
            ResultSet result = query.executeQuery();
            return new User(
                    result.getInt("id"),
                    result.getInt("robustezza"),
                    result.getInt("pwnd"),
                    result.getString("username"),
                    result.getString("password"),
                    result.getString("nome"),
                    result.getString("cognome"),
                    result.getString("salt")
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    void insertRecord(String url, String service, String username, String password) {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:/home/mksiva/IdeaProjects/APM/APM/database/1002.db");
            String sql = "insert into Credenziali (url,service,username,password) values (?,?,?,?)";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, url);
            query.setString(2, service);
            query.setString(3, username);
            query.setString(4, password);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
