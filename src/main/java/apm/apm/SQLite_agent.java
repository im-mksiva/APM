package apm.apm;
import java.io.File;
import java.sql.*;

public class SQLite_agent {

    Connection connection = null;

    SQLite_agent() {
        try {
            File file = new File("database/APM.db");
            String db_conn = "jdbc:sqlite:"+file.getAbsolutePath();
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
            PreparedStatement query = connection.prepareStatement(sql);
            query.setInt(1, nuova_credenziale.getUser_id());
            query.setString(2, nuova_credenziale.getUrl());
            query.setString(3, nuova_credenziale.getServizio());
            query.setString(4, nuova_credenziale.getUsername());
            query.setString(5, nuova_credenziale.getPassword());
            query.setString(6,nuova_credenziale.getTag());
            query.setInt(7,nuova_credenziale.getRobustezza());
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
            query.executeUpdate();
            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void insertUser(User new_user) {
        try {
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
            e.printStackTrace();
        }
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
            query.setString(2, new_nota.getTag());
            query.setString(3, new_nota.getNome());
            query.setString(4, new_nota.getTesto());
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
            query.setString(3, nota_aggiornata.getNome());
            query.setInt(4, nota_aggiornata.getId());
            query.executeUpdate();
            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
