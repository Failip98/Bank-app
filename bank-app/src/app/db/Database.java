package app.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class Database {

    private static Database ourInstance = new Database();
    public static Database getInstance() {
        return ourInstance;
    }

    private Database() {
        connectToDb();
    }

    final String connectionURL = "";
    private Connection conn = null;
    private HashMap<String, PreparedStatement> preparedStatements = new HashMap<>();

    public PreparedStatement prepareStatement(String SQLQuery){
        PreparedStatement ps = preparedStatements.get(SQLQuery);
        if (ps == null) {
            try { ps = conn.prepareStatement(SQLQuery); }
            catch (SQLException e) { e.printStackTrace(); }
        }
        return ps;
    }

    private void connectToDb(){
        try { conn = DriverManager.getConnection(connectionURL); }
        catch (SQLException e) { e.printStackTrace();
        }
    }
}
