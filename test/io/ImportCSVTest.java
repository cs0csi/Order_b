/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.util.List;
import orderClass.Order;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author csocsi
 */
public class ImportCSVTest {

    public ImportCSVTest() {
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
     * Test of readInputCsv method, of class ImportCSV.
     */
    @Test
    public void testReadInputCsv() {

        String testFileName = "import.csv";
        String expected = "import.csv";
        ImportCSV instance;
//      List<Order> result = instance.readInputCsv();
        String result = instance.readInputCsv(testFileName);
        assertEquals(expected, result);

//        System.out.println("readInputCsv");
//        ImportCSV instance = null;
//        List<Order> expResult = null;
//        List<Order> result = instance.readInputCsv();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

}
