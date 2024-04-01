package com.example.siternbackend.user.services;

import com.example.siternbackend.user.DTOs.UserUpdateRequest;
import com.example.siternbackend.user.entities.User;
import com.example.siternbackend.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class UserServiceImpl extends UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User updateUserDetails(Integer userId, UserUpdateRequest userUpdateRequest) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        // Update user details
        existingUser.setFirstName(userUpdateRequest.getFirstName());
        existingUser.setLastName(userUpdateRequest.getLastName());
        existingUser.setMajor(userUpdateRequest.getMajor());
        existingUser.setDepartment(userUpdateRequest.getDepartment());
        existingUser.setGpax(userUpdateRequest.getGpax());
        existingUser.setStudentInterest(userUpdateRequest.getStudentInterest());
        existingUser.setSkills(userUpdateRequest.getSkills());
        existingUser.setResumeCv(userUpdateRequest.getResumeCv());
        existingUser.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        existingUser.setAddress(userUpdateRequest.getAddress());
        existingUser.setLinkedInProfile(userUpdateRequest.getLinkedInProfile());

        // Update updated time
        existingUser.setUpdated(LocalDateTime.now());

        return userRepository.save(existingUser);
    }
}

