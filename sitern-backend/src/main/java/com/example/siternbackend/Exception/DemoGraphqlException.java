package com.example.siternbackend.Exception;

public class DemoGraphqlException extends RuntimeException {
    public DemoGraphqlException(String message) {
        super("message : " + message);
    }
}
