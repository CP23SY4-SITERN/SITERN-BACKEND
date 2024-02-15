package com.example.siternbackend.student.entities;

import com.example.siternbackend.user.entities.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfile {
    @Id
    private Integer id;

    @MapsId("userId")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    private String firstName;

    private String lastName;

    private String major;


    private String department;


    private Float gpax;


    private String studentInterest;

    @Lob
    private String skills;

    private byte[] resumeCv;

    private String email;


    private String phoneNumber;

    private String address;


    private String linkedInProfile;

}