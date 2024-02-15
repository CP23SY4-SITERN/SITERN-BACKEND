package com.example.siternbackend.user.controllers;

import com.example.siternbackend.Exception.DemoGraphqlException;
import com.example.siternbackend.user.DTOs.CreateUserDto;
import com.example.siternbackend.user.DTOs.UserDto;
import com.example.siternbackend.user.entities.Authorities;
import com.example.siternbackend.user.entities.Roles;
import com.example.siternbackend.user.entities.User;
import com.example.siternbackend.user.repositories.AuthoritiesRepository;
import com.example.siternbackend.user.repositories.UserRepository;
import com.example.siternbackend.user.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private final AuthoritiesRepository authoritiesRepository;

    public UserController(UserService userService, UserRepository userRepository, AuthoritiesRepository authoritiesRepository) {
        this.userService = userService;
        this.userRepository =  userRepository;
        this.authoritiesRepository = authoritiesRepository;

    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        try {
            List<UserResponse> userResponses = userService.getAllUsers();
            log.info("Retrieved {} Users", userResponses.size());
            return userResponses;
        } catch (Exception e) {
            log.error("Error while retrieving Users", e);
            throw e; // Rethrow the exception or handle it appropriately
        }
    }
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<User> createUsers(@Valid @RequestBody CreateUserDto newUsers, HttpServletRequest request) throws MethodArgumentNotValidException, MessagingException, IOException {
//        log.info("POST mapping for creating a users");
//        System.out.println("postmapping");
//        return userService.addUser(newUsers, request);
//    }


    @PostMapping
    public ResponseEntity<UserResponse> addUser(@RequestBody CreateUserDto createUserDto, HttpServletRequest request)
            throws MessagingException, IOException {
        // Extract user details from the request
        String username = createUserDto.getUsername();
        String email = createUserDto.getEmail();
        String password = createUserDto.getPassword();

        // Call the service method to add the user
        UserResponse user = userService.addUser(createUserDto, request).getBody();


        // Return the response with HTTP status 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

//    @PatchMapping("/{id}")
//    public User update(@RequestBody @javax.validation.Valid User updateUser, @PathVariable Integer id){
//        User user = userRepository.findById(id).map(o->userService.updateUser(o,updateUser))
//                .orElseGet(()->
//                {
//                    updateUser.setId(id);
//                    return updateUser;
//                });
//        return userRepository.saveAndFlush(user);
//    }
    //String emailFromToken, int id, String authorities, String userName, String email
    @PatchMapping("/users/{id}")
    public UserResponse updateUser(
        @PathVariable Integer id,
        @RequestBody UpdateUserRequest updateUserRequest
    ) {
    // Extract details from the request
    String emailFromToken = updateUserRequest.getEmailFromToken();
    String authorities = updateUserRequest.getAuthorities();
    String userName = updateUserRequest.getUserName();
    String email = updateUserRequest.getEmail();

    // Call the service method to perform the update
    UserResponse updatedUserResponse = userService.updateUser(emailFromToken, id, authorities, userName, email);

    // Return the response
    return updatedUserResponse;
    }
    @Data
    public class UpdateUserRequest {
        private String emailFromToken;
        private String authorities;
        private String userName;
        private String email;
    }
}
