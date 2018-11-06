package controls;

import IO.ImportCSV;
import IO.ImpportInterface;
import IO.SendSql;
import OrderClass.Order;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Control {

private final String FILE_SOURCE = "/Csv/input.csv";

private List<Order> orders = new ArrayList<>();

    public void Start() throws Exception {
        System.out.println("   adatbev    kezdete ");
        adatBevitel();
        System.out.println("    adatbev vége -- kiiras kezdete  ");
        
        kiiras();
        System.out.println("  kiiras vege --- sql upload kezdete     ");
        
        sqlUpload();
        System.out.println("sql vége");
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
    
    
        private void sqlUpload() throws Exception {
                    
                 Connection c = null;
      Statement stmt = null;
      try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/001",
            "postgres", "root");
         System.out.println("Opened database successfully");

         stmt = c.createStatement();
         String sql = "INSERT INTO orderx (\"OrderId\",\"BuyerName\",\"BuyerEmail\",\"OrderDate\",\"OrderTotalValue\",\"Address\",\"Postcode\")\n" +
"VALUES ('11','02','03','2018-01-01','05','06','07')";
         stmt.executeUpdate(sql);
         stmt.close();
         c.close();
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
      }
      System.out.println("Table created successfully");
            
    
            
  //      Connection kapcsolat = kapcsolodas();

     //   ImpportInterface impportInterface = new SendSql(kapcsolat);

      // orders = ImpportInterface.orderList();

       

    }

   /* private Connection kapcsolodas() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");  //org.apache.derby.jdbc.EmbeddedDriver

        String url = "jdbc:postgresql://localhost:5432/001"; //String url = "jdbc:derby://localhost:1527/utazas6";

        return DriverManager.getConnection(url, "postgres", "root");
        
       
    }*/
    
    

    private void kiiras(){
    
        for (Order order : orders) {
            System.out.println(order);
        }
    
    
    }
    
    
    
}
