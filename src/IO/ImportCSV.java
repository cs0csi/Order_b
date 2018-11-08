/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import OrderClass.Order;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author csocsi
 */
public class ImportCSV {

    private File orderFile;
    private final String CHAR_SET = ("UTF-8");

    public ImportCSV(File orderFile) {
        this.orderFile = orderFile;
    }

    public List<Order> orderList() throws Exception {
        List<Order> orders = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(orderFile, CHAR_SET)) {

            String sor, adatok[];

            while (fileScanner.hasNextLine()) {
                EmailValidator emailValidator = new EmailValidator();

                Date date = new Date();
                String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                System.out.println(modifiedDate);

                sor = fileScanner.nextLine();
                adatok = sor.split(";");
                //   if (adatok.length == 1) {   //11111111111111111

                if (!emailValidator.validate(adatok[4])) {
                    adatok[4] = "hibás"; /// statusba kell majd írni
                }

                if (adatok[10] == null && adatok[10].isEmpty()) {

                    adatok[10] = modifiedDate.toString();
                }

                if (!adatok[10].matches("\\d{4}-\\d{2}-\\d{2}")) {
                    adatok[10] = "2000-01-01";   //statusba irni hogy hibás email formatum
                }

                orders.add(new Order(Integer.parseInt(adatok[0]), (Integer.parseInt(adatok[1])), (Integer.parseInt(adatok[2])), adatok[3], adatok[4], adatok[5], Integer.parseInt(adatok[6]), (Double.parseDouble(adatok[7])), (Double.parseDouble(adatok[8])), Integer.parseInt(adatok[9]), adatok[10]));

                //   }
            }

        }
        return orders;
    }
}
