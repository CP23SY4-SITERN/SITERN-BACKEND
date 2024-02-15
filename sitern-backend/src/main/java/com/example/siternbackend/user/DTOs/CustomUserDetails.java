package com.example.siternbackend.user.DTOs;

import com.example.siternbackend.user.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert the roles of your User entity to GrantedAuthority objects
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRoles().name()));
    }

    @Override
    public String getPassword() {
        return user.getPasswordHashed();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Implement account expiration logic if needed
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Implement account locking logic if needed
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Implement credentials expiration logic if needed
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Implement account activation logic if needed
        return true;
    }

    // Additional methods or constructors specific to your application

}

