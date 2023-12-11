package com.example.siternbackend.user.services;

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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
@Service
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
    public UserService() {
    }
    public List<UserDto> getAllUsers() {
        //ต้องเพิ่ม sortbycompanyID
        List<User> user = userRepository.findAll();
        return listMapper.mapList(user ,UserDto.class,modelMapper);
    }


    // ... existing code ...

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


}
