package com.example.demo.exception;

/**
 * Custom exception thrown when a transaction is invalid or data is missing.
 */
public class InvalidTransactionException extends RuntimeException {

    public InvalidTransactionException(String message) {
        super(message);
    }
}