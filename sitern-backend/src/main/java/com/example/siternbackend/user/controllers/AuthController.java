package com.example.siternbackend.user.controllers;

import com.example.siternbackend.authentication.JwtRequest;
import com.example.siternbackend.authentication.JwtResponse;
import com.example.siternbackend.authentication.LoginRequest;
import com.example.siternbackend.authentication.LogoutRequest;
import com.example.siternbackend.user.entities.User;
import com.example.siternbackend.user.services.AuthResponse;
import com.example.siternbackend.user.services.AuthService;
import com.example.siternbackend.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.broker.provider.AuthenticationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.AuthenticationException;
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    final AuthenticationManager authenticationManager;
    final UserService userService;
    final AuthService authService;
//    final AuthResponse authResponse;
    final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//    final JwtTokenUtil jwtTokenUtil;

    final Long ONE_WEEK = 604800L;
    final Long ONE_DAY = 86400L;

//    @PostMapping("/login")
//    public ResponseEntity<JwtResponse> login(@Validated @RequestBody LoginRequest login) {
//         User user = loginWithEmail(login.email(), login.password());
//         return ResponseEntity.ok(new JwtResponse(createToken(user, ONE_DAY, true), createToken(user, ONE_WEEK, false)));
//    }
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest login) {
//        try {
//            User user = loginWithEmail(login.email(), login.password());
//            JwtResponse jwtResponse = new JwtResponse(createToken(user, ONE_DAY, true), createToken(user, ONE_WEEK, false));
//                return ResponseEntity.ok(jwtResponse);
//            } catch (UsernameNotFoundException e) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Email not found");
//            } catch (BadCredentialsException e) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password not matched for user " + login.email());
//            } catch (AuthenticationException e) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
//        }
//}
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        AuthResponse authResponse = authService.authenticate(username, password);

        if (authResponse.isSuccess()) {
            return ResponseEntity.ok(authResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String bearerToken) {
        // Extract the token from the Authorization header
        String accessToken = bearerToken.replace("Bearer ", "");

        boolean logoutSuccessful = authService.logout(accessToken);

        if (logoutSuccessful) {
            return ResponseEntity.ok("Logout successful");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed");
        }
    }
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(@RequestBody LogoutRequest request) {
//        // Assuming logout logic is implemented in AuthService
//        boolean logoutSuccessful = authService.logout(request.getAccessToken());
//
//        if (logoutSuccessful) {
//            return ResponseEntity.ok("Logout successful");
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed");
//        }
//    }
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(@Validated @RequestBody JwtResponse token) throws Exception {
//        try {
//            jwtTokenUtil.revokeToken(token.token());
//            jwtTokenUtil.revokeToken(token.refreshToken());
//            return ResponseEntity.ok("token revoked,Logout Successfully");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("cannot revoke this token");
//        }
//    }


//    @PostMapping("/refresh")
//    public ResponseEntity<JwtResponse> refreshToken(@Validated @RequestBody JwtRequest token) throws Exception {
//        String username = jwtTokenUtil.getUsernameFromToken(token.token());
//        User user = userService.findUserByEmail(username);
//        if (jwtTokenUtil.isTokenValid(token.token(), user) && !jwtTokenUtil.isAccessToken(token.token())) {
//            return ResponseEntity.ok(new JwtResponse(createToken(user, ONE_DAY, true), token.token()));
//        }
//        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cannot refresh token");
//    }

//    private String createToken(User user, Long expiration, boolean isAccess) {
//        if (user == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username or password is incorrect");
//        }
//        if (!user.isEnabled()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "this account is locked");
//        }
//        return jwtTokenUtil.generateJWT(user, expiration, isAccess);
//    }

    private User loginWithEmail(String email, String password) {
        User user = userService.findUserByEmail(email);
        return loginProcess(email, password, user);
    }

    private User loginProcess(String email, String password, User user) {
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        if (email == null) {
            log.error("Email not found");
            throw new UsernameNotFoundException("Email not found");
        }

        if (!checkAuth(user, password)) {
            log.error("Password not matched for user {}", email);
            throw new BadCredentialsException("Bad credentials");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            log.info("User {} successfully logged in", email);
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user {}: {}", email, e.getMessage());
            throw new BadCredentialsException("Bad credentials");
        }

        return user;
    }

    private boolean checkAuth(User user, String password) {
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
    public class ResponseError {
        private String errorMessage;

        public ResponseError(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
