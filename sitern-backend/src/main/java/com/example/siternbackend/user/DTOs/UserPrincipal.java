package com.example.siternbackend.user.DTOs;

import com.example.siternbackend.user.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {

    private Integer id;
    private String username;

    // Additional fields based on your User entity
    // For example, you might include email, role, etc.

    public UserPrincipal(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(
                user.getId(),
                user.getUsername()
                // Add other fields if needed
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        // Replace with actual roles or authorities from your User entity
    }

    @Override
    public String getPassword() {
        // Return the hashed password from your User entity
        return user.getPasswordHashed();
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Other methods...

    public Integer getId() {
        return id;
    }
}

