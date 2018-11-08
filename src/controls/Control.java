package controls;

import IO.ImportCSV;
import OrderClass.Order;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Control {

    private final String FILE_SOURCE = "/Csv/input.csv";

    private List<Order> orders = new ArrayList<>();

    public void Start() throws Exception {

        adatBevitel();
        sqlUpload();
    }

    private void adatBevitel() {

        try {
            File orderFile
                    = new File(this.getClass().getResource(FILE_SOURCE).toURI());

            ImportCSV fileInp = new ImportCSV(orderFile);
            orders = fileInp.orderList();

        } catch (Exception ex) {

            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private void sqlUpload() {

        Connection connect = null;
        Statement statement = null;
        try {
            Class.forName("org.postgresql.Driver");
            connect = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/001",
                            "postgres", "root");
            System.out.println("Opened database successfully");

            statement = connect.createStatement();
            String clearTables = "TRUNCATE TABLE orders,order_item";
            statement.executeUpdate(clearTables);

            String sqlInsertOrders, sqlInstertOrder_item;

            for (Order order : orders) {
                //   System.out.println(order);                  //kiiratás konzolra a fájlbol beolvasott értékeket
                sqlInsertOrders = "INSERT INTO orders (\"OrderId\",\"BuyerName\",\"BuyerEmail\",\"OrderDate\",\"OrderTotalValue\",\"Address\",\"Postcode\")"
                        + "VALUES ('" + order.getOrderId() + "','" + order.getBuyerName() + "','" + order.getBuyerEmail() + "','" + order.getOrderDate() + "','05','"
                        + order.getAddress() + "','" + order.getPostcode() + "')";

                statement.executeUpdate(sqlInsertOrders);
            }

            // a kettőt lehtne egyesíteni
            for (Order order : orders) {

                //    statement.executeUpdate("");
                sqlInstertOrder_item = "INSERT INTO order_item (\"OrderItemId\",\"OrderId\",\"SalePrice\",\"ShippingPrice\",\"TotalItemPrice\",\"SKU\")"
                        + "VALUES ('" + order.getOrderItemId() + "','" + order.getOrderId() + "','" + order.getSalePrice() + "','" + order.getShippingPrice() + "','"
                        + order.totalItemPrice() + "','" + order.getSku() + "')";
                statement.executeUpdate(sqlInstertOrder_item);
            }

            statement.close();
            connect.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");

    }

}
