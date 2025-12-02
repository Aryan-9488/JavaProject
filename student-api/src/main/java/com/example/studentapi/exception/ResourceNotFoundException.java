package com.example.studentapi.exception;

/**
 * Simple runtime exception to represent "not found" resources.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
