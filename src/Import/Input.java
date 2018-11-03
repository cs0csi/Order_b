/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Import;

import OrderClass.Order;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author csocsi
 */
public class Input implements ImpportInterface{
       private File orderFile;
       private final String CHAR_SET = ("UTF-8");

public Input(File orderFile) {
        this.orderFile = orderFile;
    }
public List<Order> orderList() throws Exception {
    List<Order> orders = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(orderFile, CHAR_SET)) {

           String sor, adatok[];

            while (fileScanner.hasNextLine()) {
                sor = fileScanner.nextLine();
                adatok = sor.split(";");
             //   if (adatok.length == 1) {   //11111111111111111
                  

                 orders.add(new Order(Integer.parseInt(adatok[0]),(Integer.parseInt(adatok[1])),(Integer.parseInt(adatok[2])),adatok[3],adatok[4],adatok[5],Integer.parseInt(adatok[6]), (Double.parseDouble(adatok[7])),(Double.parseDouble(adatok[8])),Integer.parseInt(adatok[9]),adatok[10])); 


             //   }
                         
           

            }
        }
        return orders;
    }   
}
