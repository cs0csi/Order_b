package controls;

import controls.ImportValidatorTest;
import controls.EmailValidator;
import org.junit.Test;
import static org.junit.Assert.*;

public class ImportValidatorTest {

    @Test
    public void badEmailAddress() {
        String email = "imre@freemailhu";
        EmailValidator emailValidator = new EmailValidator();
        assertFalse(emailValidator.validate(email));
    }

    @Test
    public void badDate() {
        String date = "1999-01--01";

        assertFalse(date.matches("\\d{4}-\\d{2}-\\d{2}"));
    }

    @Test
    public void reachFile() throws Exception {
        String reach = "/Csv/inputFile.csv";
        java.net.URL url = ImportValidatorTest.class.getResource(reach);

    }

}
