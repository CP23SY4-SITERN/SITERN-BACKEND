package com.example.siternbackend.user.services;

import com.example.siternbackend.Exception.DemoGraphqlException;
import com.example.siternbackend.user.DTOs.CreateUserDto;
import com.example.siternbackend.user.DTOs.UserDto;
import com.example.siternbackend.user.controllers.UserResponse;
import com.example.siternbackend.user.entities.Authorities;
import com.example.siternbackend.user.entities.Roles;
import com.example.siternbackend.user.entities.User;
import com.example.siternbackend.user.repositories.AuthoritiesRepository;
import com.example.siternbackend.user.repositories.UserRepository;
import com.example.siternbackend.util.ListMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    public List<UserDto> getAllUsers() {
        //ต้องเพิ่ม sortbycompanyID
        List<User> user = userRepository.findAll();
        return listMapper.mapList(user ,UserDto.class,modelMapper);
    }
    public Optional<User> findAllById(int id) {
        return userRepository.findById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("This user not found"));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("This user not found"));
    }

    // ... existing code ...
//    public ResponseEntity<User> createUsers(CreateUserDto newUsers, HttpServletRequest request) throws MessagingException, IOException{
//        // Check if the email is already registered
//        if (userRepository.existsByEmail(newUsers.getEmail())) {
//            throw new IllegalArgumentException("Email is already registered");
//        }
//        User newUser = modelMapper.map(newUsers, User.class);
//        // Encode the password
//        newUser.setPasswordHashed(passwordEncoder.encode(newUsers.getPassword()));
//        // Set other attributes
//        newUser.setCreated(LocalDateTime.now());
//        System.out.println("setCreateDatetime");
//        newUser.setUpdated(LocalDateTime.now());
//        System.out.println("setUpdateDatetime");
//        userRepository.saveAndFlush(newUser);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
//    }
//    public User mapUser(User existingUser, User updateUser) {
//        existingUser.setUsername(updateUser.getUsername());
//        existingUser.setUpdated(updateUser.getUpdated());
//        existingUser.setEmail(updateUser.getEmail());
//        existingUser.setRoles(updateUser.getRoles());
//        return existingUser;
//    }
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    public UserResponse addUser(String userName, String email, String password) {
        if (!userRepository.findByEmail(email).isEmpty()) {
            throw new DemoGraphqlException("This email have already registered");
        }
        Authorities authorities = getAuthorityByName(Roles.Student);
        User newUser = User.builder()
                .username(userName)
                .email(email)
                .password(encoder.encode(password))
                .authorities(List.of(authorities))
                .build();

        return mapUserToUserResponse(userRepository.save(newUser));
    }
    public Authorities getAuthorityByName(Roles name) {
        return authoritiesRepository.findByName(name);
    }
    public UserResponse mapUserToUserResponse(User user) {
        try {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(Long.valueOf(user.getId()));
            userResponse.setName(user.getUsername());
            userResponse.setEmail(user.getEmail());
            userResponse.setAuthorities(user.getSimpleAuthorities());
            return userResponse;
        } catch (Exception e) {
            log.error("Could not Map User to UserResponse: " + e.getMessage());
            return UserResponse.builder().build();
        }
    }
    }

