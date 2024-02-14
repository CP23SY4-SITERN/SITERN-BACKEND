package com.example.siternbackend.user.controllers;

import com.example.siternbackend.jobs.controllers.JobController;
import com.example.siternbackend.jobs.dtos.CreatingJobDTO;
import com.example.siternbackend.jobs.dtos.JobLocationDTO;
import com.example.siternbackend.jobs.entities.JobPost;
import com.example.siternbackend.jobs.repositories.JobLocationRepository;
import com.example.siternbackend.jobs.services.JobLocationService;
import com.example.siternbackend.user.DTOs.CreateUserDto;
import com.example.siternbackend.user.DTOs.UserDto;
import com.example.siternbackend.user.entities.User;
import com.example.siternbackend.user.repositories.UserRepository;
import com.example.siternbackend.user.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserRepository userRepository;

    public UserController(UserService userService,UserRepository userRepository) {
        this.userService = userService;
        this.userRepository =  userRepository;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        try {
            List<UserDto> userDto = userService.getAllUsers();
            log.info("Retrieved {} Users", userDto.size());
            return userDto;
        } catch (Exception e) {
            log.error("Error while retrieving Users", e);
            throw e; // Rethrow the exception or handle it appropriately
        }
    }
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUsers(@Valid @RequestBody CreateUserDto newUsers, HttpServletRequest request) throws MethodArgumentNotValidException, MessagingException, IOException {
        log.info("POST mapping for creating a users");
        System.out.println("postmapping");
        return userService.createUsers(newUsers, request);
    }
    @PatchMapping("/{id}")
    public User update(@RequestBody @javax.validation.Valid User updateUser, @PathVariable Integer id){
        User user = userRepository.findById(id).map(o->userService.mapUser(o,updateUser))
                .orElseGet(()->
                {
                    updateUser.setId(id);
                    return updateUser;
                });
        return userRepository.saveAndFlush(user);
    }
}
