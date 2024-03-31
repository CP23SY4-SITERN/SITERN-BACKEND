package com.example.siternbackend.user.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.json.JSONException;
import org.springframework.http.*;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.json.JSONObject;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
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
                return new AuthResponse(true, tokenResponse.getAccessToken(),decodedToken,"success");
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

//    public DecodedToken decodeToken(String accessToken) {
//        Jwt jwt = JwtDecoderFactory.create().decode(accessToken);
//
//        DecodedToken decodedToken = new DecodedToken();
//        decodedToken.setName(jwt.getClaimAsString(JwtClaimNames.NAME));
//        decodedToken.setPreferredUsername(jwt.getClaimAsString(JwtClaimNames.PREFERRED_USERNAME));
//        decodedToken.setGroupId(jwt.getClaimAsString("group_id")); // Assuming "group_id" claim
//        decodedToken.setEmail(jwt.getClaimAsString(JwtClaimNames.EMAIL));
//        // Add more properties as needed
//
//        return decodedToken;
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
        String name = jsonPayload.getString("name");
        String preferredUsername = jsonPayload.getString("preferred_username");
        String groupId = jsonPayload.getString("group_id");
        String email = jsonPayload.getString("email");

        // Create a new DecodedToken object and set its properties
        DecodedToken decodedToken = new DecodedToken();
        decodedToken.setName(name);
        decodedToken.setPreferredUsername(preferredUsername);
        decodedToken.setGroupId(groupId);
        decodedToken.setEmail(email);
        // Add more properties as needed

        return decodedToken;
    }
private static String secret = "RhkMsjJC7vYXA0uuOS2LxZLpyFfajY1J";
//public static DecodedToken decodeToken(String accessToken) {
//    try {
//         // กำหนดค่าของความลับที่เหมาะสำหรับคีย์
//        final Key key = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.RS256.getJcaName());
//        Claims claims = Jwts.parser()
//                .setSigningKey(key)
//                .build()
//                .parseSignedClaims(accessToken)
//                .getBody();
//
//        DecodedToken decodedToken = new DecodedToken();
//        decodedToken.setName((String) claims.get("name"));
//        decodedToken.setPreferredUsername((String) claims.get("preferred_username"));
//        decodedToken.setGroupId((String) claims.get("group_id"));
//        decodedToken.setEmail((String) claims.get("email"));
//        // Add more properties as needed
//
//        return decodedToken;
//    } catch (JwtException e) {
//        // Handle parsing exception
//        e.printStackTrace();
//        return null;
//    }
//}
//    public Claims getClaimsFromToken(String token) {
//        final Key key = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS512.getJcaName());
//        return Jwts.parser()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
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

