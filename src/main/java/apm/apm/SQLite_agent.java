package apm.apm;


import java.io.File;
import java.sql.*;


public class SQLite_agent {
    // manages connection to sqlite db
    // Valutare se scomporre in due classi distinte, una per la gestione di APM, una per le credenziali dell'utente
//    Class.forName("org.sqlite.JDBC");
    Connection connection = null;
//    String percorso_db_file = "/home/mksiva/IdeaProjects/APM/database/APM.db";
    String percorso_db_file = "C:\\Users\\calog\\IdeaProjects\\APM\\database\\APM.db";
    String db_conn = "jdbc:sqlite:" + percorso_db_file;

    SQLite_agent() {
        // Verifico se il file del db esiste già. Se non c'è inizializzo il db con le tabelle necessarie
        File db_file = new File(percorso_db_file);
        if (!db_file.exists()) {
            db_create_table_users();
            db_create_table_credential();
            db_create_table_note();
            db_create_table_tag();
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

    void db_create_table_credential() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE \"CREDENZIALI\" (\n" +
                    "\t\"id\"\tINTEGER,\n" +
                    "\t\"user_id\" int,\n" +
                    "\t\"url\"\tvarchar(50) NOT NULL,\n" +
                    "\t\"service\"\tvarchar(20),\n" +
                    "\t\"username\"\tvarchar(50) NOT NULL,\n" +
                    "\t\"password\"\tvarchar(50) NOT NULL,\n" +
                    "\t\"strenght\"\tINT DEFAULT 0,\n" +
                    "\t\"pwnd\"\tINT DEFAULT 0, tag VARCHAR(30) default null,\n" +
                    "\tPRIMARY KEY(\"id\"),\n" +
                    "\tFOREIGN KEY (user_id) references users_apm(user_id)\n" +
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
                    ", robustezza integer default 0, pwnd integer default 0, encrypt_key varchar(200) NOT NULL);";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void db_create_table_note() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String query = "CREATE TABLE NOTE (\n" +
                    "\tnote_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "\tuser_id INT NOT NULL,\n" +
                    "\ttag varchar(30) DEFAULT null,\n" +
                    "\ttesto TEXT(255),\n" +
                    "\tlast_modified DATETIME DEFAULT CURRENT_TIMESTAMP, nome VARCHAR(30) NOT NULL,\n" +
                    "\tCONSTRAINT NOTE_FK FOREIGN KEY (user_id) REFERENCES USERS_APM(user_id),\n" +
                    "\tCONSTRAINT NOTE_FK_1 FOREIGN KEY (note_id) REFERENCES TAG_NOTE(tag_id)\n" +
                    ");";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void db_create_table_tag() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String query = "CREATE TABLE TAG(\n" +
                    "tag_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "nome_tag varchar(30) not null,\n" +
                    "user_id int not null);";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            String sql = "insert into CREDENZIALI(user_id, url, service, username, password, tag, strenght) values (?,?,?,?,?,?,?)";
            System.out.println(nuova_credenziale.getUser_id() + " - " + nuova_credenziale.getUrl()+ " - " +
                    nuova_credenziale.getServizio() + " - " + nuova_credenziale.getUsername() + " - " +
                    nuova_credenziale.getPassword() + " - " + nuova_credenziale.getTag() + " - " + nuova_credenziale.getRobustezza());
            PreparedStatement query = connection.prepareStatement(sql);
            query.setInt(1, nuova_credenziale.getUser_id());
            query.setString(2, nuova_credenziale.getUrl());
            query.setString(3, nuova_credenziale.getServizio());
            query.setString(4, nuova_credenziale.getUsername());
            query.setString(5, nuova_credenziale.getPassword());
            query.setString(6,nuova_credenziale.getTag());
            query.setInt(7,nuova_credenziale.getRobustezza());
            // executeUpdate() serve per aggiornare lo stato del database, che sia inserimento o cancellazione
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    void updateCredential(Credenziali_servizi credenziale_aggiornata) {
        try {
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
            e.printStackTrace();
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

    void update_user_pass(User user, String hashed_pass) {
        try {
            String sql = "UPDATE USERS_APM SET password=?, salt=?, robustezza=?, pwnd=0, encrypt_key=? WHERE user_id=?;";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, hashed_pass);
            query.setString(2, user.getSalt());
            query.setInt(3, user.getRobustezza());
            query.setString(4, user.getEncr_key());
            query.setInt(5, user.getId());
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    User getUser(String username) {
        User logged = null;
        try {
            String sql = "select * from USERS_APM where username = ?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, username);
            ResultSet result = query.executeQuery();
            if (!result.next()){
                return null;
            }
            logged = new User(
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return logged;
    }

    void insert_note(note new_nota) {
        try {
            String sql = "insert into NOTE (user_id,tag,nome,testo) values (?,?,?,?)";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setInt(1, new_nota.getUser_id());
            query.setString(2, new_nota.tag);
            query.setString(3, new_nota.nome);
            query.setString(4, new_nota.testo);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ResultSet get_all_Note(int user_id) {
        ResultSet result = null;
        try {
            String sql = "select note_id, user_id, tag, testo, STRFTIME('%d/%m/%Y %H:%M:%S', DATETIME(last_modified, 'localtime')) as last_modified, nome from NOTE where user_id = ?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setInt(1, user_id);
            result = query.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    void updateNote(note nota_aggiornata) {
        try {
            String sql = "UPDATE NOTE SET tag=?, testo=?, nome=? WHERE note_id=?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, nota_aggiornata.getTag());
            query.setString(2, nota_aggiornata.getTesto());
            //query.setString(3, nota_aggiornata.getLast_modified());
            query.setString(3, nota_aggiornata.getNome());
            query.setInt(4, nota_aggiornata.getId());
            // executeUpdate() serve per aggiornare lo stato del database, che sia inserimento o cancellazione
            query.executeUpdate();
            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






}
