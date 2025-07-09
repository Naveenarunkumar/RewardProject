package com.example.demo.exception;

/**
 * Custom exception for handling "customer not found" scenarios.
 */
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException() {
        super("Customer not found.");
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
