import java.io.File;
import java.sql.*;

public class SQLite_agent {
    // manages connection to sqlite db
    // Valutare se scomporre in due classi distinte, una per la gestione di APM, una per le credenziali dell'utente
    Connection connection = null;
    String percorso_db_file = "/home/mksiva/IdeaProjects/APM/APM/database/APM.db";
    String db_conn = "jdbc:sqlite:" + percorso_db_file;

    SQLite_agent() {
        // Verifico se il file del db esiste già. Se non c'è inizializzo il db con le tabelle necessarie
        File db_file = new File(percorso_db_file);
        if (!db_file.exists()) {
            db_create_table_users();
            db_create_credential();
        }
        try {
            this.connection = DriverManager.getConnection(db_conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    void deleteRecord(int id, String table, String column) {
        try {
            String sql = "delete from " + table + " where " + column + "= ?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setInt(1, id);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    void db_create_credential() {
        try {
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
        PreparedStatement query = null;
        try {
            String sql = "select username from users_apm where username = ?";
            query = connection.prepareStatement(sql);
            query.setString(1, username);
            ResultSet result = query.executeQuery();
            System.out.println(result.getString("username"));
            if (result.getString("username") != null) {
                query.close();
                return true;
            }
        } catch (SQLException e) {
            // quando viene inserito uno username non trovato viene lanciata una eccezione (ResultSet closed)
            return false;
//            throw new RuntimeException(e);
        }
        try {
            query.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                String username = result.getString("username");
                query.close();
                return username;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return null;
    }


    public String get_Salt(String username) {
        try {
            String sql = "select salt from USERS_APM where username = ?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, username);
            ResultSet result = query.executeQuery();
            String salt = result.getString("salt");
            query.close();
            return salt;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public String get_User_Hash(String username) {
        try {
            String sql = "select password from USERS_APM where username = ?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, username);
            // executeQuery() serve per recuperare dati (esegue la query)
            ResultSet result = query.executeQuery();
            String hash = result.getString("password");
            query.close();
            return hash;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    ResultSet searchCredential(String text, int user_id) {
        try {
            String sql = "select service, username, url from CREDENZIALI where url like '%" + text + "%' OR service like '%" + text + "%' and user_id = " + user_id;
            Statement query = connection.createStatement();
            ResultSet result = query.executeQuery(sql);

            return result;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    ResultSet get_all_Credential(int user_id) {
        ResultSet result = null;
        try {
            String sql = "select * from CREDENZIALI where user_id = ?";
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
            String sql = "insert into CREDENZIALI(user_id, url, service, username, password) values (?,?,?,?,?)";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setInt(1, nuova_credenziale.getUser_id());
            query.setString(2, nuova_credenziale.getUrl());
            query.setString(3, nuova_credenziale.getServizio());
            query.setString(4, nuova_credenziale.getUsername());
            query.setString(5, nuova_credenziale.getPassword());
            // executeUpdate() serve per aggiornare lo stato del database, che sia inserimento o cancellazione
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void updateCredential(Credenziali_servizi credenziale_aggiornata) {
        try {
//            connection.close();

            String sql = "UPDATE CREDENZIALI SET url=?, service=?, username=?, password=?, strenght=?, pwnd=?, tag=? WHERE id=?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, credenziale_aggiornata.getUrl());
            query.setString(2, credenziale_aggiornata.getServizio());
            query.setString(3, credenziale_aggiornata.getUsername());
            query.setString(4, credenziale_aggiornata.getPassword());
            query.setInt(5, credenziale_aggiornata.getRobustezza());
            query.setInt(6, credenziale_aggiornata.getPwnd());
            query.setString(7, credenziale_aggiornata.getTag());
            query.setInt(8, credenziale_aggiornata.getId());

            // executeUpdate() serve per aggiornare lo stato del database, che sia inserimento o cancellazione
            query.executeUpdate();
            query.close();

        } catch (SQLException e) {

        }
    }


    boolean insertUser(User new_user) {
        try {
//            Statement statement = connection.createStatement();
            String sql = "insert into USERS_APM ('username','password','salt','nome','cognome',encrypt_key) values (?,?,?,?,?,?)";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, new_user.getUsername());
            query.setString(2, new_user.getPassword());
            query.setString(3, new_user.getSalt());
            query.setString(4, new_user.getNome());
            query.setString(5, new_user.getCognome());
            query.setString(6, new_user.getEncr_key());
            query.executeUpdate();
            query.close();
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
            String sql = "select * from USERS_APM where username = ?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, username);
            ResultSet result = query.executeQuery();
            User logged = new User(
                    result.getInt("user_id"),
                    result.getInt("robustezza"),
                    result.getInt("pwnd"),
                    result.getString("username"),
                    result.getString("password"),
                    result.getString("nome"),
                    result.getString("cognome"),
                    result.getString("salt"),
                    result.getString("encrypt_key")
            );
            query.close();
            return logged;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void insert_note(int user_id, note new_nota) {
        try {
            String sql = "insert into NOTE (user_id,tag,nome,testo) values (?,?,?,?)";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setInt(1, user_id);
            query.setInt(2, new_nota.tag);
            query.setString(3, new_nota.nome);
            query.setString(4, new_nota.testo);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
