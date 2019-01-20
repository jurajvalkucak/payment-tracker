package com.jvalkucak.bsc;

/**
 * Class to represent Payment Exceptions
 */
public class PaymentException extends Exception {
    /**
     *
     * @param message Error Message
     */
    PaymentException(String message) {
        super(message);
    }
}
