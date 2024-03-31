package com.example.siternbackend.student.exception;

public class StudentProfileNotFoundException extends RuntimeException {
    public StudentProfileNotFoundException(String message) {
        super(message);
    }

    public StudentProfileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
