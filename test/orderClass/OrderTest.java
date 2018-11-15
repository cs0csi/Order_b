package orderClass;

import org.junit.Test;
import static org.junit.Assert.*;

public class OrderTest {

    @Test
    public void testTotalItemPrice() {
        Order order = new Order(1, 2, 3, "Imre", "imre@freemail.hu", "Pécs", 4, 2.5, 2.6, 5, "1999-11-15", "IN_STOCK", "");

        double expResult = 5.1;
        double result = order.totalItemPrice();
        assertEquals(expResult, result, 0.00);

    }

    @Test
    public void testRounding() {
        Order order = new Order(1, 2, 3, "Imre", "imre@freemail.hu", "Pécs", 4, 2.555555, 2.6666666, 5, "1999-11-15", "IN_STOCK", "");
        assertEquals(2.56, order.getSalePrice(), 0.00);
    }

}
