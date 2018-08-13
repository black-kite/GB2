import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductsRepository {
    public static Products get(int id){
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Products usr = null;

        try{
            conn = MyConnection.getConnection();
            st = conn.prepareStatement("SELECT * FROM PRODUCTS WHERE ID = ?");
            st.setInt(1,id);
            rs = st.executeQuery();

            while (rs.next()){
                usr = new Products(rs.getInt("PRODID"),rs.getString("TITLE"), rs.getInt("COST"));
                usr.setId(rs.getInt("ID"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usr;
    }

    public static List<Products> getAll(){
        Connection conn = null;
        Statement st = null;
        ResultSet rs;
        List<Products> lst = new ArrayList<Products>();

        try{
            conn = MyConnection.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM PRODUCTS");
            while (rs.next()){
                Products usr = new Products(rs.getInt("PRODID"), rs.getString("TITLE"), rs.getInt("COST"));
                usr.setId(rs.getInt("ID"));

                lst.add(usr);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return lst;
    }

    public static void create(Products prod){
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = MyConnection.getConnection();
            st = conn.prepareStatement("INSERT INTO PRODUCTS (TITLE, COST, PRODID) VALUES(?,?,?)");
            st.setInt(1, prod.getProdid());
            st.setString(2,prod.getTitle());
            st.setInt(3,prod.getCost());
            st.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                st.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
    }

    public static void delete(int id){
        Connection conn = null;
        Statement st = null;
        try {
            conn = MyConnection.getConnection();
            conn.setAutoCommit(false);
            st = conn.createStatement();
            st.execute("DELETE FROM PRODUCTS WHERE id = "+id);

            conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                st.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
    }
    public static void update(Products prod){
        Connection conn = null;
        Statement st = null;
        try {
            conn = MyConnection.getConnection();
            st = conn.createStatement();
            st.execute("UPDATE PRODUCTS SET PRODID  = " + prod.getProdid() + ", " +
                    ", TITLE = " + prod.getTitle() + "COST = " + prod.getCost() + " WHERE ID = " + prod.getId());
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                st.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
    }

}
