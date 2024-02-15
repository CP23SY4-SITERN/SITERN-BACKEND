package com.example.siternbackend.student.entities;

import com.example.siternbackend.user.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "student_profile", schema = "SITern")
public class StudentProfile {
    @Id
    private Integer id;

    @MapsId("userId")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_ID", nullable = false)
    private User user;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "major", nullable = false, length = 100)
    private String major;

    @Column(name = "department", nullable = false, length = 100)
    private String department;

    @Column(name = "gpax")
    private Float gpax;

    @Column(name = "student_interest", length = 10000)
    private String studentInterest;

    @Lob
    @Column(name = "skills", nullable = false)
    private String skills;

    @Column(name = "resume_cv")
    private byte[] resumeCv;

    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @Column(name = "phoneNumber", nullable = false, length = 10)
    private String phoneNumber;

    @Column(name = "address", length = 45)
    private String address;

    @Column(name = "linkedInProfile", length = 45)
    private String linkedInProfile;

}