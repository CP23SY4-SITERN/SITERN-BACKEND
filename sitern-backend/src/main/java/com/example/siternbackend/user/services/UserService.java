package com.example.siternbackend.user.services;

import com.example.siternbackend.JWT.JwtTokenUtil;
import com.example.siternbackend.company.entities.Company;
import com.example.siternbackend.company.repositories.CompanyRepository;
import com.example.siternbackend.jobs.dtos.CreatingJobDTO;
import com.example.siternbackend.jobs.dtos.JobLocationDTO;
import com.example.siternbackend.jobs.entities.JobLocation;
import com.example.siternbackend.jobs.entities.JobPost;
import com.example.siternbackend.jobs.repositories.JobPostRepository;
import com.example.siternbackend.user.DTOs.CreateUserDto;
import com.example.siternbackend.user.DTOs.UserDto;
import com.example.siternbackend.user.entities.User;
import com.example.siternbackend.user.repositories.UserRepository;
import com.example.siternbackend.util.ListMapper;
import jakarta.persistence.EntityNotFoundException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.Instant;
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
    @PostMapping
    public ResponseEntity createUsers(CreateUserDto newUsers, HttpServletRequest request) throws MessagingException, IOException{
        // Check if the email is already registered
        if (userRepository.existsByEmail(newUsers.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }
        User newUser = modelMapper.map(newUsers, User.class);
        // Encode the password
        newUser.setPasswordHashed(passwordEncoder.encode(newUsers.getPassword()));
        // Set other attributes
        newUser.setCreated(LocalDateTime.now());
        System.out.println("setCreateDatetime");
        newUser.setUpdated(LocalDateTime.now());
        System.out.println("setUpdateDatetime");
        userRepository.saveAndFlush(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
    public User mapUser(User existingUser, User updateUser) {
        existingUser.setUsername(updateUser.getUsername());
        existingUser.setUpdated(updateUser.getUpdated());
        existingUser.setEmail(updateUser.getEmail());
        existingUser.setRole(updateUser.getRole());
        return existingUser;
    }
    }

