package com.example.siternbackend.user.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Set<String> revokedTokens;

    @Autowired
    public AuthService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.revokedTokens = ConcurrentHashMap.newKeySet();
    }

    public AuthResponse authenticate(String username, String password) {
        if (username == null || password == null) {
            return new AuthResponse(false, "Invalid username or password");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("auth-cp23sy4", "RhkMsjJC7vYXA0uuOS2LxZLpyFfajY1J");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", username);
        body.add("password", password);
        body.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange(
                "https://login.sit.kmutt.ac.th/realms/student-project/protocol/openid-connect/token",
                HttpMethod.POST,
                request,
                TokenResponse.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            TokenResponse tokenResponse = response.getBody();
            if (tokenResponse != null) {
                return new AuthResponse(true, tokenResponse.getAccessToken(),"success");
            }
        }

        return new AuthResponse(false, "Authentication failed");
    }

    public boolean logout(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        // Create an empty request body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        // Create an HTTP entity with headers and body
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            // Make a POST request to the token revocation endpoint
            restTemplate.postForEntity(
                    "https://login.sit.kmutt.ac.th/realms/student-project/protocol/openid-connect/logout",
                    request,
                    Void.class
            );
            // If no exception is thrown, assume logout was successful
            return true;
        } catch (Exception e) {
            // Log the error if necessary
            e.printStackTrace();
            // Return false indicating logout failure
            return false;
        }
    }
//    public boolean logout(String accessToken) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.set("Authorization", "Bearer " + accessToken);
//
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
//
//        ResponseEntity<Void> response = restTemplate.exchange(
//                "https://login.sit.kmutt.ac.th/realms/student-project/protocol/openid-connect/token",
//                HttpMethod.POST,
//                request,
//                Void.class
//        );
//
//        return response.getStatusCode().is2xxSuccessful();
//    }
    public boolean revokeAccessToken(String accessToken) {
        // Add the access token to the local blacklist
        revokedTokens.add(accessToken);
        return true; // Always return true as we don't have an actual revocation endpoint
    }

    public boolean isAccessTokenRevoked(String accessToken) {
        // Check if the access token is in the blacklist
        return revokedTokens.contains(accessToken);
    }

}

