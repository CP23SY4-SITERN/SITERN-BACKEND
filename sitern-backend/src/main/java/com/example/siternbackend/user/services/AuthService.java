package com.example.siternbackend.user.services;

import com.example.siternbackend.user.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.json.JSONObject;

import java.util.Base64;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Set<String> revokedTokens;
    private final DecodedTokenService decodedTokenService;
    private final UserService userService;

    @Autowired
    public AuthService(RestTemplate restTemplate, ObjectMapper objectMapper, DecodedTokenService decodedTokenService, UserService userService) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.decodedTokenService = decodedTokenService;
        this.userService = userService;
        this.revokedTokens = ConcurrentHashMap.newKeySet();
    }

//    public AuthResponse authenticate(String username, String password) throws JSONException {
//        if (username == null || password == null) {
//            return new AuthResponse(false, "Invalid username or password");
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.setBasicAuth("auth-cp23sy4", "RhkMsjJC7vYXA0uuOS2LxZLpyFfajY1J");
//
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("username", username);
//        body.add("password", password);
//        body.add("grant_type", "password");
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
//
//        ResponseEntity<TokenResponse> response = restTemplate.exchange(
//                "https://login.sit.kmutt.ac.th/realms/student-project/protocol/openid-connect/token",
//                HttpMethod.POST,
//                request,
//                TokenResponse.class
//        );
//
//        if (response.getStatusCode().is2xxSuccessful()) {
//            TokenResponse tokenResponse = response.getBody();
//            if (tokenResponse != null) {
//                DecodedToken decodedToken = decodeToken(tokenResponse.getAccessToken());
//                // Create a new user and save it to the database
//                User user = decodedTokenService.createUserFromDecodedToken(decodedToken);
//                User savedUser = userService.saveUser(user);
//                return new AuthResponse(true, tokenResponse.getAccessToken(),decodedToken,"success");
//            }
//        }
//
//        return new AuthResponse(false, "Authentication failed");
//    }
public AuthResponse authenticate(String username, String password) throws JSONException {
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
            DecodedToken decodedToken = decodeToken(tokenResponse.getAccessToken());
            // Create a new user and save it to the database
            User user = decodedTokenService.createUserFromDecodedToken(decodedToken);
            System.out.println("hey this is USERRRRR"+user);
            return new AuthResponse(true, tokenResponse.getAccessToken(), decodedToken, "success");
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


    public DecodedToken decodeToken(String accessToken) throws JSONException {
        // Extracting the payload from the JWT token
        String[] jwtParts = accessToken.split("\\.");
        String payload = jwtParts[1];

        // Decoding the payload from Base64
        byte[] decodedBytes = Base64.getDecoder().decode(payload);
        String decodedPayload = new String(decodedBytes);

        // Parse the decoded payload as JSON and extract the required fields
        JSONObject jsonPayload = new JSONObject(decodedPayload);
        System.out.println(jsonPayload);
        String name = jsonPayload.getString("name");
        String preferredUsername = jsonPayload.getString("preferred_username");
        String groupId = jsonPayload.getString("groupid");
        String email = jsonPayload.getString("email");

        String role;
        switch (groupId) {
            case "904":
                role = "STUDENT";
                break;
            case "901":
            case "902":
                role = "STAFF";
                break;
            default:
                // Handle other cases if needed
                role = "DEFAULT_ROLE";
                break;
        }
        // Create a new DecodedToken object and set its properties
        DecodedToken decodedToken = new DecodedToken();
        decodedToken.setName(name);
        decodedToken.setPreferredUsername(preferredUsername);
        decodedToken.setRole(role);
        decodedToken.setEmail(email);
        // Add more properties as needed

        return decodedToken;
    }
//    public String convertGroupIdToAuthority(String groupId) {
//        // logic to convert groupId to authority code
//        if ("904".equals(groupId)) {
//            return "STUDENT_WHITELIST";
//        } else if ("901".equals(groupId) || "902".equals(groupId)) {
//            return "STAFF_WHITELIST";
//        } else {
//            // handle other cases or return default authority
//            return "DEFAULT_AUTHORITY";
//        }
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

