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

@Service
public class AuthService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
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







}

