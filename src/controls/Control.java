package controls;

import orderClass.Order;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import orderClass.Status;

public class Control {

    private final String INPUT_FILE_SOURCE = "/files/inputFile.csv";
    private final String OUTPUT_FILE_SOURCE = "src/files/responseFile.csv";
    private static final String DBUSER = "dbuser";
    private static final String DBPASS = "dbpassword";
    private static final String DATABASE = "database";
    private static final String DBDRIVER = "dbdriver";
    private static final String CSV_SEPARATOR = ";";

    private List<Order> orders = new ArrayList<>();

    public void Start() throws Exception {

        dataEntry();
        sqlUpload();
        writeToCSV();
        FTPupload.main();
    }

    private void dataEntry() {

        try {
            File orderFile = new File(this.getClass().getResource(INPUT_FILE_SOURCE).toURI());

            ImportValidator fileInp = new ImportValidator(orderFile);
            orders = fileInp.readInputCsv();
            totalCounter();
        } catch (Exception ex) {

            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private double totalCounter() {
        double sum = 0;
        for (Order order : orders) {
            sum += order.totalItemPrice();
        }
        return sum;
    }

    private void writeToCSV() {

        try {
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUT_FILE_SOURCE), "UTF-8"))) {
                for (Order order : orders) {
                    StringBuilder oneLine = new StringBuilder();

                    oneLine.append(order.getLineNumber());
                    oneLine.append(CSV_SEPARATOR);
                    if (!order.geterrorMessage().isEmpty()) {
                        oneLine.append("ERROR");
                        oneLine.append(order.geterrorMessage());
                    } else {
                        oneLine.append("OK");
                    }
                    oneLine.append(CSV_SEPARATOR);
                    bw.write(oneLine.toString());
                    bw.newLine();
                }
                bw.flush();
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    private void sqlUpload() throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream("config.properties"));

        Connection connect = null;
        Statement statement = null;
        try {
            Class.forName(prop.getProperty(DBDRIVER));
            connect = DriverManager.getConnection(prop.getProperty(DATABASE),
                    prop.getProperty(DBUSER), prop.getProperty(DBPASS));
            statement = connect.createStatement();

            String sqlInsertOrders, sqlInstertOrder_item;
            for (Order order : orders) {

                String sqlSelectOrderId
                        = "select \"OrderId\" from \"order\" where \"OrderId\" = " + order.getOrderId() + "";

                ResultSet rs = statement.executeQuery(sqlSelectOrderId);
                if (rs.next()) {
                    order.setErrorMessage(order.geterrorMessage() + ";DB erreorOrderID");
                }

                sqlInsertOrders = "INSERT INTO \"order\" (\"OrderId\",\"BuyerName\","
                        + "\"BuyerEmail\",\"OrderDate\",\"OrderTotalValue\",\"Address\",\"Postcode\")"
                        + "VALUES ('" + order.getOrderId() + "','" + order.getBuyerName()
                        + "','" + order.getBuyerEmail() + "','" + order.getOrderDate() + "','" + totalCounter() + "','"
                        + order.getAddress() + "','" + order.getPostcode() + "')";

                statement.executeUpdate(sqlInsertOrders);
            }

            for (Order order : orders) {

                Status st = Status.valueOf(order.getOnStock());

                String sqlSelectOrderItemId = "select \"OrderItemId\" from order_item where \"OrderItemId\" = "
                        + order.getOrderItemId() + "";

                ResultSet rs = statement.executeQuery(sqlSelectOrderItemId);
                if (rs.next()) {
                    order.setErrorMessage(order.geterrorMessage() + ";DB erreorOrderItemID");
                }
                sqlInstertOrder_item = "INSERT INTO order_item"
                        + " (\"OrderItemId\",\"OrderId\",\"SalePrice\",\"ShippingPrice\",\"TotalItemPrice\",\"SKU\",\"Status\")"
                        + "VALUES ('" + order.getOrderItemId() + "','" + order.getOrderId()
                        + "','" + order.getSalePrice() + "','" + order.getShippingPrice() + "','"
                        + order.totalItemPrice() + "','" + order.getSku() + "','" + st + "')";
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
