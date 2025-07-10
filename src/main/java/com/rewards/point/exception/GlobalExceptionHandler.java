package com.rewards.point.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Global exception handler for the application.
 * <p>
 * This class uses {@link RestControllerAdvice} to intercept and handle exceptions
 * thrown by controller methods, returning appropriate HTTP responses.
 * </p>
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	  /**
     * Handles {@link CustomerNotFoundException} when a requested customer is not found.
     *
     * @param ex the exception thrown when the customer is not found
     * @return a {@link ResponseEntity} with HTTP 404 status and the exception message
     */


    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFound(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles {@link TransactionNotFoundException} when a requested transaction is not found.
     *
     * @param ex the exception thrown when the transaction is not found
     * @return a {@link ResponseEntity} with HTTP 404 status and the exception message
     */



    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<String> handleTransactionNotFound(TransactionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles all other uncaught exceptions.
     *
     * @param ex the generic exception that was thrown
     * @return a {@link ResponseEntity} with HTTP 500 status and a generic error message
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + ex.getMessage());
    }

}

