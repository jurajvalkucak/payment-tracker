package com.jvalkucak.bsc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Entry Class for the program
 */
public class Main {

    /**
     * Main Class
     * @param args File Path (optional)
     */
    public static void main(String[] args) {

        //Create object to track payments
        PaymentTracker myPaymentTracker = new PaymentTracker();

        //Set some exchange rates for some currencies
        myPaymentTracker.setExchangeRate("HUF", 0.003573d);
        myPaymentTracker.setExchangeRate("GBP", 1.288295d);
        myPaymentTracker.setExchangeRate("CZK", 0.044421d);
        myPaymentTracker.setExchangeRate("HKD", 0.127493d);

        //Thread to read payments from file (optional if file path is passed as argument)
        PaymentReader paymentFileReader = null;
        if (args.length > 0) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(new File(args[0]));
                paymentFileReader = new PaymentReader("File Input Payment Reader", myPaymentTracker, fileInputStream, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        //Thread to read payments from user console
        PaymentReader paymentUserReader = new PaymentReader("Console Input Payment Reader", myPaymentTracker, System.in, true);

        //Thread to print payments in 60 sec interval
        PaymentPrinter paymentPrinter = new PaymentPrinter(myPaymentTracker);

        //waiting for all threads to finish
        try {
            if (paymentFileReader != null) paymentFileReader.join();
            paymentUserReader.join();
            paymentPrinter.interrupt();
            paymentPrinter.join();
        } catch (InterruptedException e) {
            System.out.println("Main Thread interrupted");
        }
    }

}
