import java.sql.*;
import java.util.Scanner;

public class Main {
    private static Connection connection;
    private static Statement st;
    private static PreparedStatement pstm;

    private static Scanner sc;

    public static void main(String[] args) throws Exception {
        String command;
        ResultSet res;
        sc = new Scanner(System.in);

        connection = MyConnection.getConnection();
        st = connection.createStatement();

        DDLOperations.showMetaInfo();
        DDLOperations.dropProductsTable();
        //DDLOperations.createProductsTable();

        initializeTable(10000);
        //ProductsRepository.delete(9999); работает
        printTable("PRODUCTS");

        System.out.println(ProductsRepository.get(9999));
        System.out.println(ProductsRepository.getAll());
        //printTable("PRODUCTS", 600, 1000); //лучше запросом  ;)

        //!!!!!!!!!!!!!!!!КАК ПРАВИЛЬНО ВВОДИТЬ КОМАНДУ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //Узнать Диапозон цен = "Введите 'pbc' + ' ' + (цифрами)'от' + ' ' + (цифрами)'до';
        //Узнать цену = cost + productXXX;
        //Сменить цену = setcost(sc) + productXXX + (новая цена);
        while (true) {
            System.out.println("Enter command: ");
            command = sc.nextLine();
            if (command.equals("")) break;
            else {
                String commandWords[] = command.split(" ");

                if (commandWords.length == 2 && commandWords[0].equals("cost")) {
                    try {
                        res = st.executeQuery("SELECT cost FROM Products WHERE title = '" + commandWords[1] + "'");
                        System.out.println("Цена товара " + commandWords[1] + " = " + res.getInt("COST"));
                    } catch (Exception e) {
                        System.out.println("Нет продукта с таким именем");
                    }
                } else if (commandWords.length == 3 && (commandWords[0].equals("productByCost") || commandWords[0].equals("pbc"))) {
                    try {
                        int start = Integer.parseInt(commandWords[1]);
                        int end = Integer.parseInt(commandWords[2]);
                        if (start <= end)
                            printTable("Products", start, end);
                        else
                            printTable("Products", end, start);
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong number format");
                    }
                } else if (commandWords.length == 3 && (commandWords[0].equals("setcost") || commandWords[0].equals("sc"))) {
                    try {
                        int newPrice = Integer.parseInt(commandWords[2]);
                        if (st.executeUpdate("UPDATE Products SET cost = " + newPrice + " WHERE title = '" + commandWords[1] + "'") == 1)
                            System.out.println("Цена товара " + commandWords[1] + " изменилась на " + commandWords[2]);
                        else
                            System.out.println("Нет продукта с таким именем");
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong number format");
                    } catch (Exception e) {
                        System.out.println("Нет продукта с таким именем");
                    }
                } else if (command.equals("exit")) {
                    System.out.println("Спасибо, что воспользовались нашими услугами!\n" + "Вы успешно вышли!");
                    break;
                } else {
                    System.out.println("Unknown command");
                    break;
                }
            }
        }

    }

    public static void printTable(String tableName) throws SQLException {
        ResultSet res = st.executeQuery("SELECT * FROM " + tableName);
        while (res.next()) {
            System.out.println(
                    res.getInt("ID")
                            + " " + res.getInt("PRODID")
                            + " " + res.getString("TITLE")
                            + " " + res.getInt("COST")
            );
        }
    }

    public static void initializeTable(int tableSize) throws SQLException{
        st.execute("CREATE TABLE IF NOT EXISTS Products (\n" +
                "    ID    INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "    PRODID INTEGER, \n" +
                "    TITLE TEXT,\n" +
                "    COST INTEGER);");

        st.execute("DELETE FROM Products");
        st.execute("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='Products';");

        connection.setAutoCommit(false);

        pstm = connection.prepareStatement("INSERT INTO Products (prodid, title, cost) VALUES (?, ?, ?)");
        for (int i = 1; i <= tableSize; i++) {
            pstm.setInt(1, i);
            pstm.setString(2, "product" + i);
            pstm.setInt(3, i * 10);
            pstm.addBatch();
        }

        pstm.executeBatch();
        connection.setAutoCommit(true);
    }

    public static void printTable(String tableName, int start, int end) throws SQLException {
        ResultSet res = st.executeQuery("SELECT * FROM " + tableName + " WHERE cost>= " + start + " AND cost<= " + end);
        while (res.next()) {
            System.out.println(
                    res.getInt("ID")
                            + " " + res.getInt("PRODID")
                            + " " + res.getString("TITLE")
                            + " " + res.getInt("COST")
            );
        }
    }
}
