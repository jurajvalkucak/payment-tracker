package com.jvalkucak.bsc;

import java.util.*;
import java.util.stream.Stream;

/**
 * Class to Track Payments
 */
public class PaymentTracker {

    private HashMap<String, PaymentData> payments;

    /**
     * Constructor
     */
    PaymentTracker() {
        payments = new HashMap<>();
    }

    /**
     * Add payment record to tracker under specified currency code
     * Thread Safe
     * @param currencyCd Currency Code
     * @param amount Amount
     */
    synchronized public void addPayment(String currencyCd, double amount) {
        if (payments.containsKey(currencyCd.toUpperCase())) {
            payments.get(currencyCd.toUpperCase()).addPayment(amount);
        } else {
            payments.put(currencyCd.toUpperCase(), new PaymentData());
            payments.get(currencyCd.toUpperCase()).addPayment(amount);
        }
    }

    /**
     * Sets exchange rate for USD for given currency code
     * Thread Safe
     * @param currencyCd Currency Code
     * @param exchangeRate Exchange Rate
     */
    synchronized public void setExchangeRate(String currencyCd, double exchangeRate) {
        if (payments.containsKey(currencyCd.toUpperCase())) {
            payments.get(currencyCd.toUpperCase()).setExchangeRate(exchangeRate);
        }
        else {
            payments.put(currencyCd.toUpperCase(), new PaymentData(exchangeRate));
        }
    }

    /**
     * Get count of tracked Currency Codes
     * @return Count of Tracked Currency Codes
     */
    public int getCurrenciesCount() {
        return payments.size();
    }

    /**
     * Get Total Amount for given currency code
     * @param currencyCd Currency Code
     * @return Total Amount
     */
    public double getTotalAmount(String currencyCd) {
        if (payments.containsKey(currencyCd.toUpperCase())) {
            return payments.get(currencyCd.toUpperCase()).getTotalAmount();
        } else return 0.0;
    }

    /**
     * Get Count of Payment Records for given Currency Code
     * @param currencyCd Currency Code
     * @return Count of Payment Records
     */
    public int getPaymentsCount(String currencyCd) {
        if (payments.containsKey(currencyCd.toUpperCase())) {
            return payments.get(currencyCd.toUpperCase()).getPaymentsCount();
        } else return 0;
    }

    /**
     * Prints all tracked payments to standard output
     */
    public void printPayments() {
        Iterator<Map.Entry<String, PaymentData>> itEntry = payments.entrySet().iterator();
        while(itEntry.hasNext()) {
            Map.Entry<String, PaymentData> entry = itEntry.next();
            if (entry.getValue().getTotalAmount() > 0)
                System.out.println(
                        entry.getKey() + " " +
                        entry.getValue().getTotalAmount() +
                        ((entry.getValue().getExchangeRate() > 0 && entry.getKey() != "USD") ? " (USD " + entry.getValue().getTotalAmount() * entry.getValue().getExchangeRate()  + ")" : "")
                );
        }
    }

    /**
     * Internal Nested Class to store payments and exchange rate for currency code
     */
    private class PaymentData {

        PaymentData(double exchangeRate) {
            this.payments = new ArrayList<>();
            this.exchangeRate = exchangeRate;
        }

        PaymentData() {
            this(0);
        }

        private void setExchangeRate(double exchangeRate) {
            this.exchangeRate = exchangeRate;
        }

        private double getExchangeRate() {
            return exchangeRate;
        }

        private void addPayment(double amount) {
            this.payments.add(amount);
        }

        private double getTotalAmount() {
            if (!this.payments.isEmpty()) {
                Stream<Double> stream = this.payments.stream();
                return stream.mapToDouble(Double::doubleValue).sum();
            } else return 0;
        }

        private int getPaymentsCount() {
            return this.payments.size();
        }

        private List<Double> payments;
        private double exchangeRate;
    }
}
