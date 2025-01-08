package com.mcommandes.web.exceptions;

public class CommandeNotFoundException extends RuntimeException {

    // Constructor to pass a custom error message
    public CommandeNotFoundException(String message) {
        super(message); // Pass message to the parent (RuntimeException) constructor
    }

    // Optionally, add a constructor to pass both message and cause
    public CommandeNotFoundException(String message, Throwable cause) {
        super(message, cause); // Pass both message and cause to the parent (RuntimeException) constructor
    }
}
