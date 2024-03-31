package com.example.siternbackend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class KeycloakController {
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/auth")
    public ResponseEntity<Map<String, Object>> auth(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Username and password are required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Perform authentication logic here
        // You can use libraries like Spring Security or Keycloak for authentication

        try {
            // Example code for Keycloak authentication
            // Replace with actual Keycloak authentication logic
            String accessToken = performKeycloakAuthentication(username, password);

            if (accessToken != null) {
                // Authentication successful
                Map<String, Object> sessionData = new HashMap<>();
                // Populate session data as needed
                return ResponseEntity.ok().body(sessionData);
            } else {
                // Invalid credentials
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Invalid credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }
        } catch (Exception e) {
            // Error during authentication
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Example method for Keycloak authentication
    private String performKeycloakAuthentication(String username, String password) {
        String tokenEndpoint = "https://login.sit.kmutt.ac.th/realms/student-project/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("auth-cp23sy4", "RhkMsjJC7vYXA0uuOS2LxZLpyFfajY1J");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, request, Map.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return (String) response.getBody().get("access_token");
        } else {
            return null;
        }
    }
    @GetMapping("/login")
    public RedirectView login() {
        // Redirect user to Keycloak login page
        String keycloakLoginUrl = "https://login.sit.kmutt.ac.th/realms/student-project/protocol/openid-connect/auth"
                + "?client_id=auth-cp23sy4"
                + "&redirect_uri=https://capstone23.sit.kmutt.ac.th/sy4/callback"
                + "&response_type=code"
                + "&scope=openid";

        return new RedirectView(keycloakLoginUrl);
    }

    @GetMapping("/callback")
    public RedirectView handleCallback(@RequestParam("code") String code) {
        // Exchange authorization code for access token
        String accessToken = exchangeCodeForAccessToken(code);
        System.out.println(accessToken);
        // Handle the access token as needed (e.g., store it, use it for further requests)
        // Redirect user to appropriate page or perform any necessary actions
        return new RedirectView("/profile");
    }



    private String exchangeCodeForAccessToken(String code) {
        String tokenEndpoint = "https://login.sit.kmutt.ac.th/realms/student-project/protocol/openid-connect/token";
        String clientId = "auth-cp23sy4";
        String clientSecret = "RhkMsjJC7vYXA0uuOS2LxZLpyFfajY1J";
        String redirectUri = "https://capstone23.sit.kmutt.ac.th/sy4/callback";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        String requestBody = "grant_type=authorization_code"
                + "&code=" + code
                + "&redirect_uri=" + redirectUri;

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return (String) response.getBody().get("access_token");
        } else {
            // Handle error response
            return null;
        }
    }

}

