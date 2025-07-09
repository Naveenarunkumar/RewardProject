package com.example.demo.exception;

/**
 * Thrown when a transaction has a negative amount, which is invalid.
 */
public class NegativeAmountException extends RuntimeException {

    public NegativeAmountException() {
        super("Transaction amount cannot be negative.");
    }

    public NegativeAmountException(String message) {
        super(message);
    }

    public NegativeAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}

