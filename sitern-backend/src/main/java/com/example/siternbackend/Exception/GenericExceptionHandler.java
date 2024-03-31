package com.example.siternbackend.Exception;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@NoArgsConstructor
@Slf4j
@ControllerAdvice
public class GenericExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleAllExceptions(Exception ex) {
//      String errorMessage = ex.getMessage();
//       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
//    }
}
