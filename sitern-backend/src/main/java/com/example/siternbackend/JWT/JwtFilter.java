package com.example.siternbackend.JWT;

import com.example.siternbackend.token.services.TokenService;
import com.example.siternbackend.user.entities.User;
import com.example.siternbackend.user.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;
    private final TokenService tokenService;
//    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtFilter(JwtTokenUtil jwtTokenUtil, UserService userService, TokenService tokenService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.tokenService = tokenService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && !token.isEmpty()) {
            System.out.println("token: " + token);
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            try {
                String username = jwtTokenUtil.getUsernameFromToken(token);
                User user = userService.findUserByEmail(username);
                if (user != null && jwtTokenUtil.isTokenValid(token, user) && jwtTokenUtil.isAccessToken(token)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null, user.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("user {} perform some action", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Token is invalid");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        filterChain.doFilter(request, response);
    }
}
