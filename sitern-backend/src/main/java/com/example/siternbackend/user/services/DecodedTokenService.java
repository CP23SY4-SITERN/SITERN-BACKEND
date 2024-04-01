package com.example.siternbackend.user.services;

import com.example.siternbackend.user.entities.Authorities;
import com.example.siternbackend.user.entities.Roles;
import com.example.siternbackend.user.entities.User;
import com.example.siternbackend.user.repositories.AuthoritiesRepository;
import com.example.siternbackend.user.repositories.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
@Service
public class DecodedTokenService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    public User decodeToken(String accessToken) throws JSONException {
        // ทำการ decode token และเซ็ตข้อมูลลงใน user และ role
        DecodedToken decodedToken = decodeTokenData(accessToken);
        User user = new User();
        user.setFromDecodedToken(decodedToken.getName(), decodedToken.getPreferredUsername(), decodedToken.getRole(), decodedToken.getEmail());

        // สร้าง role ใหม่จากข้อมูลที่ decode มา
        Roles role = Roles.valueOf(decodedToken.getRole());
        Authorities authorities = new Authorities();
        authorities.setRoles(role);

        // เซ็ต authorities ใหม่ลงใน user
        user.setAuthorities(List.of(authorities));

        return user;
    }
    private DecodedToken decodeTokenData(String accessToken) throws JSONException {

        // ใส่โค้ดการ decode token และ return อ็อบเจกต์ DecodedToken
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
    public User createUserFromDecodedToken(DecodedToken decodedToken) {
        // Create role enum from decoded token role
        Roles role = Roles.valueOf(decodedToken.getRole());
        System.out.printf(String.valueOf(decodedToken));
        // Check if authorities with the same role exist
        Authorities existingAuthorities = authoritiesRepository.findAuthoritiesByRoles(role);

        // If authorities do not exist, create new authorities
        if (existingAuthorities == null) {
            Authorities authorities = new Authorities();
            authorities.setRoles(role);
            existingAuthorities = authoritiesRepository.save(authorities);
        }

        User user = new User();
        user.setFromDecodedToken(decodedToken.getName(), decodedToken.getPreferredUsername(), decodedToken.getRole(), decodedToken.getEmail());

        // Set authorities to user
        user.setAuthorities(List.of(existingAuthorities));
        if(userRepository.findByEmail(user.getEmail()).isEmpty()){
            return userRepository.save(user);
        }
        return user;
    }
    public User getUserFromToken(String accessToken) throws JSONException {
        // Decode token data
        DecodedToken decodedToken = decodeTokenData(accessToken);

        // Check if user already exists
        User existingUser = userRepository.findByEmail(decodedToken.getEmail()).orElse(null);

        if (existingUser != null) {
            return existingUser;
        }

        // If user doesn't exist, create a new user from decoded token
        return createUserFromDecodedToken(decodedToken);
    }

}
