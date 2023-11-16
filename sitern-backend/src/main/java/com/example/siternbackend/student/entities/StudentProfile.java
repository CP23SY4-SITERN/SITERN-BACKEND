package com.example.siternbackend.student.entities;

import com.example.siternbackend.internshipstatus.entities.Internshipapplicationstatus;
import com.example.siternbackend.jobs.entities.JobAppliedByStudent;
import com.example.siternbackend.user.entities.User;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "student_profile", schema = "sitern")
public class StudentProfile {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_ID", nullable = false)
    private User user;

    @MapsId("internshipapplicationstatusId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "InternshipApplicationStatus_ID", nullable = false, referencedColumnName = "ID")
    private Internshipapplicationstatus internshipApplicationStatus;

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

    @OneToMany(mappedBy = "studentProfile")
    private Set<JobAppliedByStudent> jobAppliedByStudents = new LinkedHashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Internshipapplicationstatus getInternshipApplicationStatus() {
        return internshipApplicationStatus;
    }

    public void setInternshipApplicationStatus(Internshipapplicationstatus internshipApplicationStatus) {
        this.internshipApplicationStatus = internshipApplicationStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Float getGpax() {
        return gpax;
    }

    public void setGpax(Float gpax) {
        this.gpax = gpax;
    }

    public String getStudentInterest() {
        return studentInterest;
    }

    public void setStudentInterest(String studentInterest) {
        this.studentInterest = studentInterest;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public byte[] getResumeCv() {
        return resumeCv;
    }

    public void setResumeCv(byte[] resumeCv) {
        this.resumeCv = resumeCv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkedInProfile() {
        return linkedInProfile;
    }

    public void setLinkedInProfile(String linkedInProfile) {
        this.linkedInProfile = linkedInProfile;
    }

    public Set<JobAppliedByStudent> getJobAppliedByStudents() {
        return jobAppliedByStudents;
    }

    public void setJobAppliedByStudents(Set<JobAppliedByStudent> jobAppliedByStudents) {
        this.jobAppliedByStudents = jobAppliedByStudents;
    }

}