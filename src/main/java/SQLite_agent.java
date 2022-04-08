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

    public void getValue(String nome){
        try {
            Statement statement = connection.createStatement();
            String query = "select nome from prova where nome = '" + nome +"'";
            // executeQuery() serve per recuperare dati
            ResultSet rs = statement.executeQuery(query);
            while(rs.next())
            {
//                 read the result set
                System.out.println("name = " + rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertValue(String value){
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
    }
