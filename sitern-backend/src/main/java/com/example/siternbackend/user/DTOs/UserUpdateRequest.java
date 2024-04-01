package com.example.siternbackend.user.DTOs;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    @Size(max = 255 , message = "Your Firstname must be between 1 to 255 characters")
    private String firstName;
    @Size(max = 255 , message = "Your lastName must be between 1 to 255 characters")
    private String lastName;
    @Size(max = 255 , message = "Your Major must lower than 255 characters")
    private String major;
    @Size(max = 255 , message = "Your Department must lower than 255 characters")
    private String department;
    private Float gpax;
    @Size(max = 255 , message = "Your Interests must lower than 255 characters")
    private String studentInterest;
    @Size(max = 255 , message = "Your Skills must lower than 255 characters")
    private String skills;

    private byte[] resumeCv;
    @Pattern(regexp = "^\\+?[0-9.-]*$", message = "Invalid phone number format")
    private String phoneNumber;
    @Size(max = 255 , message = "Your Address must lower than 255 characters")
    private String address;
    private String linkedInProfile;


}

