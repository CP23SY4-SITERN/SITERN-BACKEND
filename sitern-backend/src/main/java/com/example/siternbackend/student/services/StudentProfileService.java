//package com.example.siternbackend.student.services;
//
////
////import com.example.siternbackend.JWT.JwtTokenUtil;
//import com.example.siternbackend.student.entities.StudentProfile;
//import com.example.siternbackend.student.exception.StudentProfileNotFoundException;
//import com.example.siternbackend.student.repositories.StudentProfileRepository;
//import com.example.siternbackend.user.entities.User;
//import com.example.siternbackend.user.repositories.UserRepository;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.BeanWrapper;
//import org.springframework.beans.BeanWrapperImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.server.ResponseStatusException;
//
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
////import com.example.siternbackend.JWT.JwtTokenUtil;
//@Service
//public class StudentProfileService {
//    @Autowired
//    private StudentProfileRepository studentProfileRepository;
//
////    public ResponseEntity<StudentProfile> createOrUpdateStudentProfile(@Valid StudentProfile studentProfile, HttpServletRequest request) throws MethodArgumentNotValidException {
////        StudentProfile existingProfile = studentProfileRepository.findById(studentProfile.getId()).orElse(null);
////
////        if (existingProfile != null) {
////            // Update existing profile
////            BeanUtils.copyProperties(studentProfile, existingProfile, getNullPropertyNames(studentProfile));
////        } else {
////            // Create new profile
////            existingProfile = studentProfile;
////        }
////
////        studentProfileRepository.saveAndFlush(existingProfile);
////        return ResponseEntity.status(HttpStatus.CREATED).body(existingProfile);
////    }
//
//    @Autowired
//    private UserRepository userRepository;
////    @Autowired
////    private JwtTokenUtil jwtTokenUtil;
//
////    public ResponseEntity<StudentProfile> createOrUpdateStudentProfile(@Valid StudentProfile studentProfile, HttpServletRequest request) throws MethodArgumentNotValidException {
////        Long userId = extractUserIdFromRequest(request); // Method to extract userId from request, implement as per your authentication mechanism
////
////        // Print the userId for debugging
////        System.out.println("User ID extracted from request: " + userId);
////
////        // Fetch the user from the repository using userId
////        User user = userRepository.findById(userId.intValue())
////                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
////
////        // Set the user to the studentProfile
////        studentProfile.setUser(user);
////
////        // Save or update the studentProfile
////        StudentProfile savedProfile = studentProfileRepository.save(studentProfile);
////
////        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
////    }
////
//
//    public ResponseEntity<StudentProfile> createOrUpdateStudentProfile(@Valid StudentProfile studentProfile, Integer userId) {
//        // Fetch the user from the repository using userId
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
//
//        // Set the user to the studentProfile
//        studentProfile.setUser(user);
//
//        // Save or update the studentProfile
//        StudentProfile savedProfile = studentProfileRepository.save(studentProfile);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
//    }
//
//    private static final String CLAIM_KEY_ID = "id";
//    //
////    // Method to extract userId from HttpServletRequest, implement as per your authentication mechanism
////    public Integer extractUserIdFromRequest(HttpServletRequest request) {
////        String token = request.getHeader("Authorization");
////        if (token != null && token.startsWith("Bearer ")) {
////            token = token.substring(7);
////            try {
////                Claims claims = jwtTokenUtil.getClaimsFromToken(token);
////                String userIdStr = claims.get(CLAIM_KEY_ID, String.class);
////                System.out.println(userIdStr);
////                if (userIdStr != null) {
////
////                    return Integer.parseInt(userIdStr);
////                }
////            } catch (Exception e) {
////                throw new RuntimeException("Error extracting user ID from JWT token", e);
////            }
////        }
////        throw new RuntimeException("User ID not found in request");
////    }
//    public List<StudentProfile> getAllStudentProfiles() {
//        List<StudentProfile> studentProfiles = studentProfileRepository.findAll();
//        if (studentProfiles.isEmpty()) {
//            throw new StudentProfileNotFoundException("No student profiles found");
//        }
//        return studentProfiles;
//    }
//
//
//    public StudentProfile getStudentProfileById(Integer id) {
//        return studentProfileRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, "Student profile with id " + id + " does not exist"
//                ));
//    }
//
//    public void deleteStudentProfileById(Integer id, HttpServletRequest request) {
//        studentProfileRepository.findById(id).orElseThrow(() ->
//                new ResponseStatusException(HttpStatus.NOT_FOUND, "Student profile with id " + id + " not found"));
//        studentProfileRepository.deleteById(id);
//    }
//
//    private String[] getNullPropertyNames(Object source) {
//        final BeanWrapper src = new BeanWrapperImpl(source);
//        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
//        Set<String> nullProperties = new HashSet<>();
//
//        for (java.beans.PropertyDescriptor pd : pds) {
//            if (src.getPropertyValue(pd.getName()) == null) {
//                nullProperties.add(pd.getName());
//            }
//        }
//
//        return nullProperties.toArray(new String[0]);
//    }
//}
//
