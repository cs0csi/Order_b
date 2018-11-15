/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderClass;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alien
 */
public class OrderTest {
    
    public OrderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of totalItemPrice method, of class Order.
     */
    @Test
    public void testTotalItemPrice() {
        System.out.println("totalItemPrice");
        Order order;
        order = null;
        double expResult = 0.0;
        double result = order.totalItemPrice();
        assertEquals(  order.totalItemPrice(),0);
      
    }

    @Test
    public void testKonst() {
    Order order = new Order(1,2,3,"asd","xcy","asfds",4,2.333333,2.333333,5,"sdfsdfs","dsfsdfdsfs","dfsdsd");
    assertEquals(1, order.getLineNumber());
    }
    
}
