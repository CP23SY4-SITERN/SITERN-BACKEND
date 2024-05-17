package com.example.siternbackend.Exception;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@NoArgsConstructor
@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errors", List.of(
                Map.of("message", errorMessage)
        ));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        String errorMessage = ex.getReason();
        if (errorMessage == null) {
            errorMessage = "There was an error processing your request.";
        }

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errors", List.of(
                Map.of("message", errorMessage)
        ));

        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        String errorMessage = ex.getMessage();
        if (errorMessage == null) {
            errorMessage = "There was an error processing your request.";
        }

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errors", List.of(
                Map.of("message", errorMessage)
        ));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
        String errorMessage = ex.getMessage();
        if (errorMessage == null) {
            errorMessage = "There was an error processing your request.";
        }

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errors", List.of(
                Map.of("message", errorMessage)
        ));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

