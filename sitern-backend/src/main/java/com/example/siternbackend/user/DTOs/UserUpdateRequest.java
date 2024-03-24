package com.example.siternbackend.user.DTOs;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String major;
    private String department;
    private Float gpax;
    private String studentInterest;
    private String skills;
    private byte[] resumeCv;
    private String phoneNumber;
    private String address;
    private String linkedInProfile;


}

