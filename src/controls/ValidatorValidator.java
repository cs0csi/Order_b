package controls;

import controls.EmailValidator;
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

public class ValidatorValidator {

    Order order;
    private File orderFile;
    private final String CHAR_SET = ("UTF-8");

    public ValidatorValidator(File orderFile) {
        this.orderFile = orderFile;
    }
    Date date = new Date();
    String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
    EmailValidator emailValidator = new EmailValidator();

    public List<Order> readInputCsv() {
        List<Order> orders = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(orderFile, CHAR_SET)) {

            String row, data[];

            while (fileScanner.hasNextLine()) {

                row = fileScanner.nextLine();
                data = row.split(";", -1);

                if (!emailValidator.validate(data[4])) {
                    data[12] = data[12] + ";Wrong e-mail address";

                }

                if (data[10] == null || data[10].isEmpty()) {

                    data[10] = modifiedDate;
                    data[12] = data[12] + ";Date has not been entered - set today";

                }

                for (int i = 0; i < data.length - 1; i++) {
                    if (data[i] == null || data[i].isEmpty()) {

                        data[12] = data[12] + ";empty cell is located";
                    }
                }

                if (!data[10].matches("\\d{4}-\\d{2}-\\d{2}")) {
                    data[12] = data[12] + ";Defective date format is set to default";
                    data[10] = "1900-01-01";

                }
                if (Double.parseDouble(data[7]) < 1.00) {
                    data[12] = data[12] + ";SalePrice smaller than 1.00";
                }
                if (Double.parseDouble(data[8]) < 0.00) {
                    data[12] = data[12] + ";ShippingPrice smaller than 0.00";
                }

                orders.add(new Order(Integer.parseInt(data[0]), (Integer.parseInt(data[1])), (Integer.parseInt(data[2])),
                        data[3], data[4], data[5], Integer.parseInt(data[6]), (Double.parseDouble(data[7])),
                        (Double.parseDouble(data[8])), Integer.parseInt(data[9]), data[10], data[11], data[12]));

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ValidatorValidator.class.getName()).log(Level.SEVERE, null, ex);

        }

        return orders;
    }

}
