package controls;

import io.ImportCSV;
import orderClass.Order;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Control {

    private final String INPUT_FILE_SOURCE = "/Csv/input.csv";
    private final String OUTPUT_FILE_SOURCE = "src/Csv/output.csv";

    private List<Order> orders = new ArrayList<>();

    public void Start() throws Exception {

        adatBevitel();
        sqlUpload();
        writeToCSV();
    }

    private void adatBevitel() {

        try {
            File orderFile = new File(this.getClass().getResource(INPUT_FILE_SOURCE).toURI());

            ImportCSV fileInp = new ImportCSV(orderFile);
            orders = fileInp.readInputCsv();

        } catch (Exception ex) {

            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private static final String CSV_SEPARATOR = ";";

    private void writeToCSV() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUT_FILE_SOURCE), "UTF-8"));
            for (Order order : orders) {
                StringBuilder oneLine = new StringBuilder();
                oneLine.append(order.getLineNumber() <= 0 ? "" : order.getLineNumber());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(order.getBuyerName().trim().length() == 0 ? "" : order.getBuyerName());
                oneLine.append(CSV_SEPARATOR);

                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (UnsupportedEncodingException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
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

            // megnézni hogy létezik-e az order ID az adatbázisban
//https://stackoverflow.com/questions/16099382/java-mysql-check-if-value-exists-in-database
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
