import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static Connection getConnection() throws SQLException {
        try{
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Connection conn;
        conn =  DriverManager.getConnection("jdbc:sqlite:PRODUCTS");
        return conn;

    }
}
