package com.example.siternbackend.user.services;

import com.example.siternbackend.Exception.DemoGraphqlException;
import com.example.siternbackend.files.entities.File;
import com.example.siternbackend.jobs.dtos.JobLocationDTO;
import com.example.siternbackend.jobs.entities.JobLocation;
import com.example.siternbackend.user.DTOs.CreateUserDto;
import com.example.siternbackend.user.DTOs.UserDto;
import com.example.siternbackend.user.DTOs.UserUpdateRequest;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public abstract class UserService {
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

    public List<UserResponse> getAllUsers() {
        //ต้องเพิ่ม sortbycompanyID
        List<User> user = userRepository.findAll();
        return mapListUserToListUserResponse(user);
    }

    public List<UserUpdateRequest> getUserWithDetails() {
        //ต้องเพิ่ม sortbycompanyID
        List<User> user = userRepository.findAll();
        return mapUserwithDetailsToListUserwithDetails(user);
    }

    public List<UserDto> getAllDataFromUsers() {
        //ต้องเพิ่ม sortbycompanyID
        List<User> user = userRepository.findAll();
        return mapListUserToListUserDtos(user);
    }

    public List<UserDto> mapListUserToListUserDtos(List<User> users) {
        return users.stream().map(this::mapListUserToListUserDtos).collect(Collectors.toList());
    }

    public List<UserResponse> mapListUserToListUserResponse(List<User> users) {
        return users.stream().map(this::mapUserToUserResponse).collect(Collectors.toList());
    }

    public List<UserUpdateRequest> mapUserwithDetailsToListUserwithDetails(List<User> users) {
        return users.stream().map(this::mapUserwithDetailsToListUserwithDetails).collect(Collectors.toList());
    }

    public Optional<User> findAllById(int id) {
        return userRepository.findById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("This user not found"));
    }

    public User findUserByEmail(String email) {
//        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("This Email not found"));
//    }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    // ... existing code ...
    public ResponseEntity<UserResponse> addUser(CreateUserDto newUsers, HttpServletRequest request) throws MessagingException, IOException {
        // Check if the email is already registered
        if (userRepository.existsByEmail(newUsers.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }
        if (userRepository.existsByUsername(newUsers.getUsername())) {
            throw new IllegalArgumentException("Username is already exists");
        }
        User newUser = modelMapper.map(newUsers, User.class);
        Authorities authorities = getAuthorityByName(Roles.STUDENT);
        newUser.setAuthorities(List.of(authorities));
        // Encode the password
//        newUser.setPassword(passwordEncoder.encode(newUsers.getPassword()));
        // Set other attributes
        newUser.setCreated(LocalDateTime.now());
        System.out.println("setCreateDatetime");
        newUser.setUpdated(LocalDateTime.now());
        System.out.println("setUpdateDatetime");
        return ResponseEntity.status(HttpStatus.CREATED).body(mapUserToUserResponse(userRepository.save(newUser)));

    }

    public User saveUser(User user) {

        return userRepository.save(user);
    }

    //    public User mapUser(User existingUser, User updateUser) {
//        existingUse
//        r.setUsername(updateUser.getUsername());
//        existingUser.setUpdated(updateUser.getUpdated());
//        existingUser.setEmail(updateUser.getEmail());
//
//        return existingUser;
//    }
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    //    public UserResponse addUser(String userName, String email, String password) {
//        if (!userRepository.findByEmail(email).isEmpty()) {
//            throw new DemoGraphqlException("This email have already registered");
//        }
//        Authorities authorities = getAuthorityByName(Roles.Student);
//        User newUser = User.builder()
//                .username(userName)
//                .email(email)
//                .password(encoder.encode(password))
//                .authorities(List.of(authorities))
//                .build();
//
//        return mapUserToUserResponse(userRepository.save(newUser));
//    }
    public UserResponse updateUser(String emailFromToken, int id, String authorities, String userName, String email) {
        User userFromToken = userRepository.findByEmail(emailFromToken).orElseThrow(() -> new DemoGraphqlException("This user not found"));
        User userFromId = userRepository.findById(id).orElseThrow(() -> new DemoGraphqlException("This user not found"));

//        boolean isStaff = userFromToken.getAuthorities()
//                .stream()
//                .anyMatch(authority -> Roles.STAFF.toString().equalsIgnoreCase(authority.getAuthority()));
//        if (!isStaff) {
//            if (!userFromId.getUsername().equals(userFromToken.getUsername()) || authorities.contains(Roles.STAFF.toString())) {
//                log.info("Unauthorized: Cannot Update this User");
//                return UserResponse.builder().build();
//            }
//        }

        List<Authorities> authoritiesList = authoritiesRepository.findAll();
        List<String> roleList = Arrays.stream(authorities.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        boolean allRolesCorrect = roleList.stream()
                .allMatch(role -> authoritiesList.stream()
                        .anyMatch(authority -> authority.getUser().toString().equals(role)));

        if (allRolesCorrect) {
            List<Authorities> userAuthorities = roleList.stream()
                    .map(role -> authoritiesList.stream()
                            .filter(authority -> authority.getUser().toString().equals(role))
                            .findFirst()
                            .orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            userFromId.setAuthorities(userAuthorities);
        } else {
            log.error("Incorrect update role");
            return UserResponse.builder().build();
        }
        userFromId.setUsername(userName);
        userFromId.setEmail(email);
        return mapUserToUserResponse(userRepository.save(userFromId));
    }

    public Authorities getAuthorityByName(Roles roles) {
        return authoritiesRepository.findAuthoritiesByRoles(roles);
    }

    public UserResponse mapUserToUserResponse(User user) {
        try {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setEmail(user.getEmail());
            userResponse.setAuthorities(user.getSimpleAuthorities());
            userResponse.setFiles(mapToFileList(user.getFiles()));
            return userResponse;
        } catch (Exception e) {
            log.error("Could not Map User to UserResponse: " + e.getMessage());
            return UserResponse.builder().build();
        }
    }
    private List<File> mapToFileList(List<File> files) {
        return files.stream()
                .map(this::mapToFile)
                .collect(Collectors.toList());
    }
    private File mapToFile(File file) {
        File file1 = new File();
        file1.setId(file.getId());
        file1.setFilePath(file.getFilePath());
        file1.setUser(file.getUser());
        return file1;
    }

    //mapUserwithDetailsToListUserwithDetails
    public UserUpdateRequest mapUserwithDetailsToListUserwithDetails(User user) {
        try {
            UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
            userUpdateRequest.setFirstName(user.getFirstName());
            userUpdateRequest.setLastName(user.getLastName());
            userUpdateRequest.setMajor(user.getMajor());
            userUpdateRequest.setDepartment(user.getDepartment());
            userUpdateRequest.setGpax(user.getGpax());
            userUpdateRequest.setStudentInterest(user.getStudentInterest());
            userUpdateRequest.setResumeCv(user.getResumeCv());
            userUpdateRequest.setPhoneNumber(user.getPhoneNumber());
            userUpdateRequest.setAddress(user.getAddress());
            userUpdateRequest.setLinkedInProfile(user.getLinkedInProfile());
            return userUpdateRequest;
        } catch (Exception e) {
            log.error("Could not Map User to UserUpdateRequest: " + e.getMessage());
            return UserUpdateRequest.builder().build();
        }
    }

    public UserDto mapListUserToListUserDtos(User user) {
        try {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setEmail(user.getEmail());
            userDto.setAuthorities(user.getSimpleAuthorities());
            userDto.setCreated(user.getCreated());
            userDto.setUpdated(user.getUpdated());
            return userDto;
        } catch (Exception e) {
            log.error("Could not Map User to UserResponse: " + e.getMessage());
            return UserDto.builder().build();
        }
    }

    public User updateUserDetails(Integer userId, UserUpdateRequest userUpdateRequest) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        if (userUpdateRequest.getFirstName() != null) {
            existingUser.setFirstName(userUpdateRequest.getFirstName());
        }
        if (userUpdateRequest.getLastName() != null) {
            existingUser.setLastName(userUpdateRequest.getLastName());
        }
        if (userUpdateRequest.getMajor() != null) {
            existingUser.setMajor(userUpdateRequest.getMajor());
        }
        if (userUpdateRequest.getDepartment() != null) {
            existingUser.setDepartment(userUpdateRequest.getDepartment());
        }
        // Similarly, check and update other fields...
        // Update user details
        if (userUpdateRequest.getGpax() != null){
            existingUser.setGpax(userUpdateRequest.getGpax());
        }
        if (userUpdateRequest.getStudentInterest() != null){
            existingUser.setStudentInterest(userUpdateRequest.getStudentInterest());
        }
        if (userUpdateRequest.getSkills() != null){
            existingUser.setSkills(String.join(",", userUpdateRequest.getSkills()));
        } if (userUpdateRequest.getResumeCv() != null){
            existingUser.setResumeCv(userUpdateRequest.getResumeCv());
        } if (userUpdateRequest.getPhoneNumber() != null){
            existingUser.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        }if (userUpdateRequest.getAddress() != null){
            existingUser.setAddress(userUpdateRequest.getAddress());
        }if (userUpdateRequest.getLinkedInProfile() != null){
            existingUser.setLinkedInProfile(userUpdateRequest.getLinkedInProfile());
        }
        // Update updated time
        existingUser.setUpdated(LocalDateTime.now());

        return userRepository.save(existingUser);
    }
}

