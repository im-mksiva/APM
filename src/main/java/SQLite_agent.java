import java.sql.*;

public class SQLite_agent {
    // manages connection to sqlite db
    Connection connection = null;

    SQLite_agent(){
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:/home/mksiva/IdeaProjects/APM/APM/database/APM.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Select(){}

    public String[] get_User_Hash(String username){
        String db_hash;
        String db_salt;
        String[] result = new String[2]; // molto brutto, vorrei trovare una soluzione migliore
        try {
            String sql = "select passkey,salt from users where username = ?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1,username);
            // executeQuery() serve per recuperare dati (esegue la query)
            db_hash = query.executeQuery().getString("passkey");
            db_salt = query.executeQuery().getString("salt");
            result[0] = db_hash;
            result[1] = db_salt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    void insertValue(String value){
        try {
            Statement statement = connection.createStatement();
            String query = "insert into prova values ('" + value + "')";
            System.out.println(query);
            // executeUpdate() serve per aggiornare lo stato del database, che sia inserimento o cancellazione
            statement.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void insertUser(String username, String hashed_password, String salt) {
        try {
            Statement statement = connection.createStatement();
            String sql = "insert into users values (?,?,?)";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1,username);
            query.setString(2,hashed_password);
            query.setString(3,salt);

            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
