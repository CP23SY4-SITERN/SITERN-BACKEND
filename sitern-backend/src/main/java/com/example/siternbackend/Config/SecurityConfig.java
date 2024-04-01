package com.example.siternbackend.Config;

//import com.example.siternbackend.JWT.JwtEntryPoint;
//import com.example.siternbackend.JWT.JwtFilter;
import com.example.siternbackend.JWT.JwtEntryPoint;
import com.example.siternbackend.JWT.JwtFilter;
import com.example.siternbackend.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import com.example.siternbackend.user.entities.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    final JwtEntryPoint jwtEntryPoint;
    final JwtFilter jwtFilter;
    final UserService userService;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*")); // Allow all headers
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        authProvider.setAuthoritiesMapper(authoritiesMapper());
//        return authProvider;
//    }


    private static final String[] FREE_AREA = {
            "/api/graphql",
            "/api/auth/**",
            "/api/auth",
            "/api/auth/login",
            "/api/details",
            "/api/users",
            "/api/users/**",
            "/users/{id}",
            "/api/files",
            "/api/files/**",
            "/api/files/upload",
            "/api/jobs/**",
            "/api/jobs",
//            "/api/companies",
//            "/api/companies/jobLocation",
//            "/api/companies/withJobLocations",
//            "/api/companies/**",
    };
    private static final String[] FREE_AREA_FOR_LOGIN = {
            "/playground",
            "/api/graphql",
            "/api/auth",
            "/api/auth/login",
            "/api/users",
            "/api/auth/logout",
    };
    private static final String[] STUDENT_WHITELIST= {
            "/api/auth/details",
            "/api/auth/credentials",
            "/api/auth/password",
            "/api/auth/email",
            "/api/auth/username",
            "/api/auth/forget-password",
            "/api/auth/refresh",
            "/api/jobLocation",
            "/api/auth/logout",
            "/api/companies",
            "/api/companies/**",
            "/api/auth/**",
            "/api/student-profiles",
            "/api/student-profiles/**",
            "/api/users",
            "/api/users/**",
            "/api/files",
            "/api/files/**",
            "/api/files/upload",
    };

    private static final String[] STAFF_WHITELIST = {
            "/api/admin/**",
            "/api/companies",
            "/api/companies/**",
            "/api/jobLocation",
            "/api/jobLocation/**",
            "/api/jobs",
            "/api/jobs/**",
            "/api/users/**",
            "/api/auth/logout",
            "/api/auth/logout/**",
    };

    private static final String[] USER_WHITELIST = {
            "/api/user/**"
    };


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(FREE_AREA_FOR_LOGIN).permitAll()
                        .requestMatchers(HttpMethod.GET,FREE_AREA).permitAll()
                        .requestMatchers(STUDENT_WHITELIST).hasAnyAuthority(
                                Roles.STUDENT.name(),
                                Roles.STAFF.name())
                        .requestMatchers(STAFF_WHITELIST).hasAuthority(Roles.STAFF.name())
                                .requestMatchers(HttpMethod.POST,"/api/jobs/**").hasAuthority(Roles.STAFF.name())
                                .requestMatchers(HttpMethod.POST,"/api/jobs").hasAuthority(Roles.STAFF.name())
                                .requestMatchers(HttpMethod.POST,"/api/companies/**").hasAuthority(Roles.STAFF.name())
                                .requestMatchers(HttpMethod.POST,"/api/companies").hasAuthority(Roles.STAFF.name())
//                        .requestMatchers(USER_WHITELIST).hasAnyAuthority(Roles.OTHER.name())
                        .anyRequest().authenticated()
                );
//        http.authenticationProvider(authenticationProvider());
//        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    List<String> allowedOrigins = Arrays.asList(
//            "*"
//    );


    @SneakyThrows
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return userService::findUserByEmail;
//    }

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }
}