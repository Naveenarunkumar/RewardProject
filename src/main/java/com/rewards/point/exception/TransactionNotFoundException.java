package com.rewards.point.exception;

/**
 * Exception thrown when no transactions are found for a given condition.
 */
public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException() {
        super("Transaction not found.");
    }

    public TransactionNotFoundException(String message) {
        super(message);
    }
}

