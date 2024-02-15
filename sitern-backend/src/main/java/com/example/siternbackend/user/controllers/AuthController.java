package com.example.siternbackend.user.controllers;

import com.example.siternbackend.JWT.JwtTokenUtil;
import com.example.siternbackend.authentication.JwtRequest;
import com.example.siternbackend.authentication.JwtResponse;
import com.example.siternbackend.authentication.LoginRequest;
import com.example.siternbackend.user.entities.User;
import com.example.siternbackend.user.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    final AuthenticationManager authenticationManager;
    final UserService userService;
    final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    final JwtTokenUtil jwtTokenUtil;

    final Long ONE_WEEK = 604800L;
    final Long ONE_DAY = 86400L;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Validated @RequestBody LoginRequest login) {
//        return ResponseEntity.ok(new JwtResponse("", ""));
        User user = loginWithEmail(login.email(), login.password());
        return ResponseEntity.ok(new JwtResponse(createToken(user, ONE_DAY, true), createToken(user, ONE_WEEK, false)));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Validated @RequestBody JwtResponse token) throws Exception {
        try {
            jwtTokenUtil.revokeToken(token.token());
            jwtTokenUtil.revokeToken(token.refreshToken());
            return ResponseEntity.ok("token revoked");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("cannot revoke this token");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@Validated @RequestBody JwtRequest token) throws Exception {
        String username = jwtTokenUtil.getUsernameFromToken(token.token());
        User user = userService.findUserByEmail(username);
        if (jwtTokenUtil.isTokenValid(token.token(), user) && !jwtTokenUtil.isAccessToken(token.token())) {
            return ResponseEntity.ok(new JwtResponse(createToken(user, ONE_DAY, true), token.token()));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cannot refresh token");
    }

    private String createToken(User user, Long expiration, boolean isAccess) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username or password is incorrect");
        }
        if (!user.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "this account is locked");
        }
        return jwtTokenUtil.generateJWT(user, expiration, isAccess);
    }

    private User loginWithEmail(String email, String password) {
        User user = userService.findUserByEmail(email);
        return loginProcess(email, password, user);
    }

    private User loginProcess(String email, String password, User user) {
        if (!checkAuth(user, password)) {
            return null;
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            log.info("user {} successfully logged in", email);
        } catch (Exception e) {
            log.error("login process error: {}", e.getMessage());
        }
        return user;
    }

    private boolean checkAuth(User user, String password) {
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
}