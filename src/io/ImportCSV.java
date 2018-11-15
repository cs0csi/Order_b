/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import orderClass.Order;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import orderClass.Status;

/**
 *
 * @author csocsi
 */
public class ImportCSV {
Order order;
    private File orderFile;
    private final String CHAR_SET = ("UTF-8");

    public ImportCSV(File orderFile) {
        this.orderFile = orderFile;
    }
    Date date = new Date();
    String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
    EmailValidator emailValidator = new EmailValidator();

    public List<Order> readInputCsv() {
        List<Order> orders = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(orderFile, CHAR_SET)) {

            String sor, adatok[];

            while (fileScanner.hasNextLine()) {

                sor = fileScanner.nextLine();
                adatok = sor.split(";", -1);

                if (!emailValidator.validate(adatok[4])) {
                    adatok[12] = adatok[12] + " hibás email cím, ";

                }

                if (adatok[10] == null || adatok[10].isEmpty()) {

                    adatok[10] = modifiedDate;
                    adatok[12] = adatok[12] + " dátum nem lett beírva a mai lett megadva, ";

                }

                for (int i = 0; i < adatok.length - 1; i++) {
                    if (adatok[i] == null || adatok[i].isEmpty()) {

                        adatok[12] = adatok[12] + " üres cella található, ";
                    }
                }

                if (!adatok[10].matches("\\d{4}-\\d{2}-\\d{2}")) {
                    adatok[12] = adatok[12] + " hibás dátum formátum alapértelmezettre beállítva, ";
                    adatok[10] = "1900-01-01";

                }
                if (Double.parseDouble(adatok[7]) < 1.00) {
                    adatok[12] = adatok[12] + "  Saleprice kissebb mint 1.00, ";
                }
                if (Double.parseDouble(adatok[8]) < 0.00) {
                    adatok[12] = adatok[12] + "  ShippingPrice kissebb mint 0.00, ";
                }

                orders.add(new Order(Integer.parseInt(adatok[0]), (Integer.parseInt(adatok[1])), (Integer.parseInt(adatok[2])),
                        adatok[3], adatok[4], adatok[5], Integer.parseInt(adatok[6]), (Double.parseDouble(adatok[7])),
                        (Double.parseDouble(adatok[8])), Integer.parseInt(adatok[9]), adatok[10], adatok[11], adatok[12]));

               
           
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportCSV.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Input file not found");

        }

        return orders;
    }

}
