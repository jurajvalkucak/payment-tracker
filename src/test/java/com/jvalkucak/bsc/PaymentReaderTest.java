package com.jvalkucak.bsc;

import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.Assert.assertEquals;

/**
 * Test Class
 */
public class PaymentReaderTest {

    private InputStream testInputStream;
    private static final double DELTA = 1e-15;

    @Before
    public void setUp() throws Exception {
        StringBuilder inputString = new StringBuilder();
        inputString.append("USD 100");
        inputString.append(System.getProperty("line.separator"));
        inputString.append("USD 200");
        inputString.append(System.getProperty("line.separator"));
        inputString.append("USD -50");
        inputString.append(System.getProperty("line.separator"));
        inputString.append("HUF 60");
        inputString.append(System.getProperty("line.separator"));
        inputString.append("GBP 60");
        inputString.append(System.getProperty("line.separator"));
        inputString.append("GBP 100");
        inputString.append(System.getProperty("line.separator"));
        inputString.append("GB 100"); //bad input
        inputString.append(System.getProperty("line.separator"));
        inputString.append("GBP 10a0"); //bad input
        inputString.append(System.getProperty("line.separator"));

        this.testInputStream = new ByteArrayInputStream(inputString.toString().getBytes());
    }

    @Test
    public void testNumberOfCurrencies() {
        PaymentTracker testPaymentTracker = new PaymentTracker();
        PaymentReader testPaymentReader = new PaymentReader("Test Thread Reader", testPaymentTracker, testInputStream, false);
        try {
            testPaymentReader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(3, testPaymentTracker.getCurrenciesCount());
    }

    @Test
    public void testTotalAmounts() {
        PaymentTracker testPaymentTracker = new PaymentTracker();
        PaymentReader testPaymentReader = new PaymentReader("Test Thread Reader", testPaymentTracker, testInputStream, false);
        try {
            testPaymentReader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(250, testPaymentTracker.getTotalAmount("USD"), DELTA);
        assertEquals(60, testPaymentTracker.getTotalAmount("HUF"), DELTA);
        assertEquals(160, testPaymentTracker.getTotalAmount("GBP"), DELTA);
    }

    @Test
    public void testNumberOfPayments() {
        PaymentTracker testPaymentTracker = new PaymentTracker();
        PaymentReader testPaymentReader = new PaymentReader("Test Thread Reader", testPaymentTracker, testInputStream, false);
        try {
            testPaymentReader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(3, testPaymentTracker.getPaymentsCount("USD"));
        assertEquals(1, testPaymentTracker.getPaymentsCount("HUF"));
        assertEquals(2, testPaymentTracker.getPaymentsCount("GBP"));
        assertEquals(0, testPaymentTracker.getPaymentsCount("CZK"));
    }
}
