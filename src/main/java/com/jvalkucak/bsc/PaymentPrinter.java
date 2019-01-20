package com.jvalkucak.bsc;

import java.util.concurrent.TimeUnit;

/**
 * Thread which prints all currencies with total amount of tracked payments
 */
public class PaymentPrinter extends Thread {
    private final PaymentTracker paymentTracker;
    private volatile boolean running;
    private static final int INTERVAL_TIME_SEC = 60;

    /**
     * Constructor
     * @param paymentTracker Source payment tracker
     */
    PaymentPrinter(PaymentTracker paymentTracker) {
        super("Payment Printer");
        this.paymentTracker = paymentTracker;
        this.running = true;
        start();
    }

    /**
     * Thread run method
     * In specified interval prints all currencies with total amount of tracked payments to standard output (in case amount is greater than 0)
     */
    @Override
    public void run() {
        System.out.println("Thread " + this.getName() + " started.");

        while(running) {
            try {
                TimeUnit.SECONDS.sleep(INTERVAL_TIME_SEC);
                System.out.println("Thread " + this.getName() + " - printing payments start");
                paymentTracker.printPayments();
                System.out.println("Thread " + this.getName() + " - printing payments stopped.");
            } catch (InterruptedException e) {
                running = false;
            }
        }

        System.out.println("Thread " + this.getName() + " stopped.");
    }
}
