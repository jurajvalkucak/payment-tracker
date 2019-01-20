package com.jvalkucak.bsc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Thread that reads payment records from input stream, validates the records and puts them to payment tracker
 */
public class PaymentReader extends Thread {
    private final PaymentTracker paymentTracker;
    private BufferedReader buffer;
    private boolean showMessages;
    private int count = 0;
    private static final String helpText = "Type <CURRENCY_CD (3 letter code)> <AMOUNT (numeric value)> and hit ENTER key, e.g. \"USD 100\", to exit program type \"quit\"";

    /**
     * Constructor
     * @param threadName Thread Name
     * @param paymentTracker Destination Payment Tracker
     * @param inputStream Source Input Stream to read payment records from
     * @param showMessages Enable/Disable printing messages to standard output
     */
    PaymentReader(String threadName, PaymentTracker paymentTracker, InputStream inputStream, boolean showMessages) {
        super(threadName);
        this.paymentTracker = paymentTracker;
        this.buffer = new BufferedReader(new InputStreamReader(inputStream));
        this.showMessages = showMessages;
        start();
    }

    /**
     * Threads run method
     * Reads lines from input stream and process them
     */
    @Override
    public void run() {
        System.out.println("Thread " + this.getName() + " started.");
        if (showMessages) System.out.println(helpText);

        String line;
        while(true) {
            try {
                line = buffer.readLine();

                if(line == null || line.toUpperCase().equals("QUIT")) {
                    break;
                } else {
                    processLine(line);
                }
            } catch (PaymentException e) {
                if (showMessages) {
                    System.out.println("Thread " + this.getName() + ": " + e.getMessage());
                    System.out.println(helpText);
                }
            } catch (IOException e) {
                System.out.printf("Unable to read from input: " + e.getMessage());
                break;
            }
        }

        System.out.println("Thread " + this.getName() + " stopped (Number of inputs processed: " + count + ").");
    }

    /**
     * Processes a single line from input stream
     * Tokenizes currency code and amount from the line
     * Validates the payment record
     * Puts it to payment tracker
     * Keeps track of processed payment records (lines)
     * @param line Input Line from input stream
     * @throws PaymentException
     */
    private void processLine(String line) throws PaymentException {
        StringTokenizer st = new StringTokenizer(line);

        //Check number of arguments
        if (st.countTokens() != 2)
            throw new PaymentException("Invalid input - bad number of input arguments!");

        String currencyCd = st.nextToken();
        String amount = st.nextToken();

        //validate currency cd
        if(!currencyCd.matches("[a-zA-Z]{3}"))
            throw new PaymentException("Invalid input - currency code bad format!");

        //validate amount
        if(!Utils.isNumeric(amount))
            throw new PaymentException("Invalid input - amount bad format!");

        //addPayment to payments tracker
        paymentTracker.addPayment(currencyCd.toUpperCase(), Double.parseDouble(amount));

        count++;
    }
}

