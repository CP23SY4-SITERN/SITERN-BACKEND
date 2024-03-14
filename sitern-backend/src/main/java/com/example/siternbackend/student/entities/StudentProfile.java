package com.example.siternbackend.student.entities;

import com.example.siternbackend.user.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfile {
    @Id
    private Integer userId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Size(max = 255 , message = "Your Firstname must be between 1 to 255 characters")
    private String firstName;
    @Size(max = 255 , message = "Your Lastname must be between 1 to 255 characters")
    private String lastName;
    @Size(max = 255 , message = "Your Major must lower than 255 characters")
    private String major;

    @Size(max = 255 , message = "Your Department must lower than 255 characters")
    private String department;


    private Float gpax;

    @Size(max = 255 , message = "Your Interests must lower than 255 characters")
    private String studentInterest;

    @Lob
    private String skills;

    private byte[] resumeCv;
//ควรใส่เพิ่มหรือว่าให้
    @Email
    @Size(max = 45, message = "must below 45 Characters")
    private String email;

    @Pattern(regexp = "^\\+?[0-9.-]*$", message = "Invalid phone number format")
    private String phoneNumber;
    @Size(max = 255 , message = "Your Address must lower than 255 characters")
    private String address;

    private String linkedInProfile;

}