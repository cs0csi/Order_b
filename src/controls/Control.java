package controls;

import Import.Input;
import OrderClass.Order;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Control {

private final String FILE_SOURCE = "/files/input.csv";

private List<Order> orders = new ArrayList<>();

    public void Start() {
        
        
        System.out.println("asd");
        adatBevitel();
        kiiras();
    }
    
    
    private void adatBevitel() {

        try {
            File orderFile
                    = new File(this.getClass().getResource(FILE_SOURCE).toURI());
  
            Input fileInp = new Input(orderFile);
            orders = fileInp.orderList();
         

        } catch (Exception ex) {
            
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("as"+ex.toString());
        }
    }
    
    
    private void kiiras(){
    
        for (Order order : orders) {
            System.out.println(order);
        }
    
    
    }
    
    
    
}
