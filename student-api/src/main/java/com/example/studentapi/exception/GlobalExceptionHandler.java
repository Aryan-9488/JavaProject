package com.example.studentapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;

/**
 * Global exception handler that returns ApiError responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // 404 - resource not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        ApiError err = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    // 400 - invalid arguments
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBadRequest(IllegalArgumentException ex, WebRequest request) {
        ApiError err = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    // 400 - malformed JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        ApiError err = new ApiError(HttpStatus.BAD_REQUEST, "Malformed JSON request", request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    // 400 - validation errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, WebRequest request) {
        String messages = ex.getBindingResult()
                            .getFieldErrors()
                            .stream()
                            .map(FieldError::getDefaultMessage)
                            .collect(Collectors.joining("; "));
        ApiError err = new ApiError(HttpStatus.BAD_REQUEST, messages, request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    // 500 - generic fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex, WebRequest request) {
        ApiError err = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred", request.getDescription(false).replace("uri=", ""));
        // log the stacktrace (use a logger in real apps)
        ex.printStackTrace();
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
