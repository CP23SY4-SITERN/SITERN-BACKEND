package com.example.siternbackend.user.entities;

import com.example.siternbackend.student.entities.StudentProfile;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "user", schema = "sitern")
public class User {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @Column(name = "password_hashed", nullable = false)
    private String passwordHashed;

    @Lob
    @Column(name = "role")
    private String role;

    @Column(name = "username", nullable = false, length = 45)
    private String username;

    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated", nullable = false)
    private Instant updated;

    @OneToMany(mappedBy = "user")
    private Set<StudentProfile> studentProfiles = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHashed() {
        return passwordHashed;
    }

    public void setPasswordHashed(String passwordHashed) {
        this.passwordHashed = passwordHashed;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Set<StudentProfile> getStudentProfiles() {
        return studentProfiles;
    }

    public void setStudentProfiles(Set<StudentProfile> studentProfiles) {
        this.studentProfiles = studentProfiles;
    }

}