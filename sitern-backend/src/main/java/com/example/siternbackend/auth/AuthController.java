//package com.example.siternbackend.auth;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Controller
//public class AuthController {
//
//    @PostMapping("/auth")
//    public ResponseEntity<Map<String, Object>> auth(@RequestBody Map<String, String> credentials) {
//        String username = credentials.get("username");
//        String password = credentials.get("password");
//
//        if (username == null || password == null) {
//            Map<String, Object> errorResponse = new HashMap<>();
//            errorResponse.put("error", "Username and password are required");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
//        }
//
//        // Perform authentication logic here
//        // You can use libraries like Spring Security or Keycloak for authentication
//
//        try {
//            // Example code for Keycloak authentication
//            // Replace with actual Keycloak authentication logic
//            String accessToken = performKeycloakAuthentication(username, password);
//
//            if (accessToken != null) {
//                // Authentication successful
//                Map<String, Object> sessionData = new HashMap<>();
//                // Populate session data as needed
//                return ResponseEntity.ok().body(sessionData);
//            } else {
//                // Invalid credentials
//                Map<String, Object> errorResponse = new HashMap<>();
//                errorResponse.put("error", "Invalid credentials");
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
//            }
//        } catch (Exception e) {
//            // Error during authentication
//            Map<String, Object> errorResponse = new HashMap<>();
//            errorResponse.put("error", "Internal Server Error");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//        }
//    }
//
//    // Example method for Keycloak authentication
//    private String performKeycloakAuthentication(String username, String password) {
//        // Replace this with your actual Keycloak authentication logic
//        // Example: Call Keycloak API to authenticate the user
//        // Return access token if authentication is successful, null otherwise
//        return null;
//    }
//}
//
