import java.beans.PropertyEditorSupport;
import java.sql.*;

public class SQLite_agent {
    // manages connection to sqlite db
    // Valutare se scomporre in due classi distinte, una per la gestione di APM, una per le credenziali dell'utente
    Connection connection = null;

    SQLite_agent() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:/home/mksiva/IdeaProjects/APM/APM/database/APM.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    SQLite_agent(String user_id) {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:/home/mksiva/IdeaProjects/APM/APM/database/" + user_id + ".db");
        } catch (SQLException e) {
            e.printStackTrace();
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
            String sql = "select passkey from users where username = ?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, username);
            // executeQuery() serve per recuperare dati (esegue la query)
            return query.executeQuery().getString("passkey");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    void insertCredential(String url, String service, String username, String pass) {
        try {
            String sql = "insert into Credenziali(url,service,username,password) values (?,?,?,?)";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, url);
            query.setString(2, service);
            query.setString(3, username);
            query.setString(4, pass);
            // executeUpdate() serve per aggiornare lo stato del database, che sia inserimento o cancellazione
            query.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void insertUser(String username, String hashed_password, String salt) {
        try {
            Statement statement = connection.createStatement();
            String sql = "insert into users values (?,?,?)";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, username);
            query.setString(2, hashed_password);
            query.setString(3, salt);

            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
