import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DDLOperations {
    public static void createProductsTable(){
        Connection conn;
        Statement st = null;
        try{
            conn = MyConnection.getConnection();
            st = conn.createStatement();
            st.execute("CREATE TABLE PRODUCTS("+
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "PRODID  INTEGER(4)," +
                    "TITLE CHAR(50) NOT NULL,"+
                    "COST  INTEGER");
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void dropProductsTable(){
        Connection conn = null;
        Statement st = null;
        try{
            conn = MyConnection.getConnection();
            st = conn.createStatement();
            st.execute("DROP TABLE PRODUCTS");
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void showMetaInfo(){
        Connection conn = null;
        try{
            conn = MyConnection.getConnection();
            ResultSet rs = conn.getMetaData().getTables(null, null, null, new String[]{"TABLE"});
            while(rs.next())
            {
                //Print
                System.out.println(rs.getString("TABLE_NAME"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

}
