package com.sharon.govtech.assignment;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;


/**
* Unit testing code for app.
*
* @author  Tan Yee Ping Sharon
* @version 1.0
* @since   2018-04-30 
*/

public class AppTest      
{

    @Test(timeout = 5000)
    public void testVerifyAndUpdateDatesInput() {
                            
        try{

           ByteArrayOutputStream outContent = new ByteArrayOutputStream();

           App testApp = new App("jan-2016" ,"feb-2017");

           // for testing of this method, we can assume there will always only be two inputs 
           // because the main method already checks for the correct number of inputs

           // test whether method correctly takes valid input (case-insensitive) from user and converts the input
           // into a format that the MAS API accepts
           String[] correctInput = {"jan-2016" ,"feb-2017"};
           String[] correctOutput = {"2016-01" ,"2017-02"};
           String[] correctInputAllCaps = {"JAN-2016" ,"FEB-2017"};
           String[] correctOutputAllCaps = {"2016-01" ,"2017-02"};

           assertEquals(correctOutput, testApp.verifyAndUpdateDatesInput(correctInput));
           assertEquals(correctOutputAllCaps, testApp.verifyAndUpdateDatesInput(correctInputAllCaps));

           // test whether the method recognizes 1 or 2 invalid inputs
           String[] twoIncorrectInput = {"random" ,"characters"};
           String[] oneIncorrectInputVersionOne = {"jan-2016" ,"gibberish"};
           String[] oneIncorrectInputVersionTwo = {"gibberish" ,"feb-2017"};

           assertNull(testApp.verifyAndUpdateDatesInput(twoIncorrectInput));
           assertNull(testApp.verifyAndUpdateDatesInput(oneIncorrectInputVersionOne));
           assertNull(testApp.verifyAndUpdateDatesInput(oneIncorrectInputVersionTwo));

            /*
             * check whether the method recognizes a few invalid scenarios:
             * 1) end date is before start date
             * 2) start date is in the future
             * 3) end date is in the future
             * 4) date range is more than 100 months
             * 5) wrong number of inputs
             * check that the method outputs to console the correct error message
             * and return a null value
             */

           System.setOut(new PrintStream(outContent));

           String[] inputWrongOrder = {"feb-2017","jan-2016"};
           String[] inputFutureStartDate = {"feb-3017","jan-4016"};
           String[] inputFutureEndDate = {"feb-2017","jan-4016"};
           String[] inputHundredMonthsApart = {"feb-2000","jan-2016"};
           String[] inputOneOnly = {"feb-2000"};
           String[] inputThree = {"feb-2000","feb-2001","feb-2002"};

           assertNull(testApp.verifyAndUpdateDatesInput(inputWrongOrder));
           assertEquals("Start date is greater than end date\n", outContent.toString());

           outContent.reset();
           
           assertNull(testApp.verifyAndUpdateDatesInput(inputFutureStartDate));
           assertEquals("Starting date is greater than current date\nPlease enter a date before today\n", outContent.toString());

           outContent.reset();
           
           assertNull(testApp.verifyAndUpdateDatesInput(inputFutureEndDate));
           assertEquals("End date is greater than current date\nPlease enter a date before today\n", outContent.toString());

           outContent.reset();
           
           assertNull(testApp.verifyAndUpdateDatesInput(inputHundredMonthsApart));
           assertEquals("Start date and end date are more than 100 months apart\n", outContent.toString());

           outContent.reset();
           
           assertNull(testApp.verifyAndUpdateDatesInput(inputOneOnly));
           assertEquals("Invalid number of inputs\n", outContent.toString());
           
           outContent.reset();
           
           assertNull(testApp.verifyAndUpdateDatesInput(inputThree));
           assertEquals("Invalid number of inputs\n", outContent.toString());

           // cancel redirecting output from console to byteArrayOutputStream
           System.setOut(System.out);

        } catch (Exception e) {
            System.out.println("Error retrieving records");
        } 
    }

    @Test(timeout = 5000)
    public void testCompareRate() 
    {
        try{
            App testApp = new App("jan-2016" ,"feb-2017");

            /*
             * Test whether the method correctly compares the interest rate of companies and banks.
             * There is no need to test for validity of input because method verifyAndUpdateDatesInput() 
             * will test the validity of inputs so we can assume the input is always valid
             */

            assertEquals("lower", testApp.compareRate(2.4f,1.2f));
            assertEquals("lower", testApp.compareRate(2f,1f));

            assertEquals("higher", testApp.compareRate(1.4f,2.6f));
            assertEquals("higher", testApp.compareRate(1,2));

            assertEquals("same", testApp.compareRate(1.2f,1.2f));
            assertEquals("same", testApp.compareRate(1f,1f));

        } catch (Exception e) {
        } 
    }

    @Test(timeout = 5000)
    public void testCompareTrend() {
                            
        try{
            App testApp = new App("jan-2016" ,"feb-2017");

            /*
             * Test whether the method correctly deduces the trend of the given time period
             * There is no need to test for validity of input because method verifyAndUpdateDatesInput() 
             * will test the validity of inputs so we can assume the input is always valid
             */

            assertEquals("upward", testApp.compareTrend(2.4f,1.2f));
            assertEquals("upward", testApp.compareTrend(2,1));

            assertEquals("downward", testApp.compareTrend(1.4f,2.6f));
            assertEquals("downward", testApp.compareTrend(1,2));

            assertEquals("stagnant", testApp.compareTrend(1.2f,1.2f));
            assertEquals("stagnant", testApp.compareTrend(2,2));

        } catch (Exception e) {
            System.out.println("Error retrieving records");
        } 
    }
}

