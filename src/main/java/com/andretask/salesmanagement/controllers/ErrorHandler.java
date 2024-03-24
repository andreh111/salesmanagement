package com.andretask.salesmanagement.controllers;

import com.andretask.salesmanagement.exceptions.*;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("Failed to read request: {}", e.getMessage());
        return new ResponseEntity<>(createErrorBody("Failed to read request", HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return new ResponseEntity<>(createErrorBody("Invalid input.", HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public final ResponseEntity<Object> handleInvalidParameterException(InvalidParameterException e) {
        logger.error("InvalidParameterException: {}", e.getMessage());
        return new ResponseEntity<>(createErrorBody("InvalidParameterException.", HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        logger.error("MethodArgumentTypeMismatchException: {}", e.getMessage());
        return new ResponseEntity<>(createErrorBody("Invalid parameter value.", HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorAttributes.put(fieldName, errorMessage);
        });

        // Log the detailed error message including the fields and error messages
        logger.error("Validation failed for fields: {}", errorAttributes);
        return new ResponseEntity<>(errorAttributes, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        logger.error("ConstraintViolationException: {}", e.getMessage());
        String errorMessage = e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(createErrorBody(errorMessage, HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ClientNotFoundException.class)
    public final ResponseEntity<Object> handleExerciseOverlapException(ClientNotFoundException e, WebRequest request) {
        logger.error("Client not found", e);
        return new ResponseEntity<>(createErrorBody("Client not found", HttpStatus.CONFLICT, e.getMessage()), HttpStatus.CONFLICT);

    }

    @ExceptionHandler(DuplicateClientException.class)
    public final ResponseEntity<Object> handleExerciseOverlapException(DuplicateClientException e, WebRequest request) {
        logger.error("Client with this phone number already exists", e);
        return new ResponseEntity<>(createErrorBody("Client already exists", HttpStatus.CONFLICT, e.getMessage()), HttpStatus.CONFLICT);

    }

    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<Object> handleExerciseOverlapException(ProductNotFoundException e, WebRequest request) {
        logger.error("Product not found", e);
        return new ResponseEntity<>(createErrorBody("Product not found", HttpStatus.CONFLICT, e.getMessage()), HttpStatus.CONFLICT);

    }

    @ExceptionHandler(DuplicateProductException.class)
    public final ResponseEntity<Object> handleExerciseOverlapException(DuplicateProductException e, WebRequest request) {
        logger.error("Product already exists", e);
        return new ResponseEntity<>(createErrorBody("Product already exists", HttpStatus.CONFLICT, e.getMessage()), HttpStatus.CONFLICT);

    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public final ResponseEntity<Object> handleExerciseOverlapException(TransactionNotFoundException e, WebRequest request) {
        logger.error("Transaction not found", e);
        return new ResponseEntity<>(createErrorBody("Transaction not found", HttpStatus.CONFLICT, e.getMessage()), HttpStatus.CONFLICT);

    }

    @ExceptionHandler(SaleNotFoundException.class)
    public final ResponseEntity<Object> handleExerciseOverlapException(SaleNotFoundException e, WebRequest request) {
        logger.error("Sale not found", e);
        return new ResponseEntity<>(createErrorBody("Sale not found", HttpStatus.CONFLICT, e.getMessage()), HttpStatus.CONFLICT);

    }

    @ExceptionHandler(InvalidDefinitionException.class)
    public final ResponseEntity<Object> handleInvalidDefinition(InvalidDefinitionException e, WebRequest request) {
        logger.error("Invalid request definition", e);
        return new ResponseEntity<>(createErrorBody("Invalid request definition", HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e, WebRequest request) {
        logger.error("Entity not found", e);
        return new ResponseEntity<>(createErrorBody("Entity not found", HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(NoResourceFoundException.class)
    public final ResponseEntity<Object> handleGeneralException(NoResourceFoundException e, WebRequest request) {
        logger.error("NoResourceFoundException", e);
        return new ResponseEntity<>(createErrorBody("NoResourceFoundException",
                HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleGeneralException(Exception e, WebRequest request) {
        logger.error("An unexpected error occurred", e);
        return new ResponseEntity<>(createErrorBody("An unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> createErrorBody(String message, HttpStatus status, String details) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("timestamp", LocalDateTime.now());
        errorAttributes.put("status", status.value());
        errorAttributes.put("error", status.getReasonPhrase());
        errorAttributes.put("message", message);
        errorAttributes.put("more details", details);
        return errorAttributes;
    }
}

