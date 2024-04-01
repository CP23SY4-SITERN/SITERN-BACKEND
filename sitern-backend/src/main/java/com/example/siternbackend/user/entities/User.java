package com.example.siternbackend.user.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails, Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    private String email;

    @Size(max = 255)
    private String password;

    @Size(max = 45)
    @NotNull
    private String username;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime created;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updated;

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

    @Size(max = 255 , message = "Your Skills must lower than 255 characters")
    private String skills;

    private byte[] resumeCv;

    @Pattern(regexp = "^\\+?[0-9.-]*$", message = "Invalid phone number format")
    private String phoneNumber;
    @Size(max = 255 , message = "Your Address must lower than 255 characters")
    private String address;

    private String linkedInProfile;

    private String statusTR01 = "incomplete";
    private String statusTR02 = "incomplete";
//
//    @Builder.Default
//    String TR01Status = "Incomplete";
//    @Builder.Default
//    String TR02Status = "Incomplete";

    @ManyToMany(fetch = FetchType.EAGER)
    List<Authorities> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities
                .stream()
                .map(authorities -> new SimpleGrantedAuthority(authorities.getRoles().name()))
                .toList();
    }

    public List<String> getSimpleAuthorities() {
        return this.authorities.stream().map(authorities -> authorities.getRoles().name()).toList();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Builder.Default
    Date lastPasswordReset = Date.from(LocalDate.of(2023, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());

    @Builder.Default
    Boolean enables = true;
    @Override
    public boolean isAccountNonLocked() {
        return enables;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enables;
    }


    public void setFromDecodedToken(String name, String preferredUsername, String role, String email) {
        this.setUsername(preferredUsername);// กำหนดชื่อผู้ใช้
        this.setEmail(email); // กำหนดอีเมล
        this.setPassword("");
        this.setFirstName(name); // กำหนดชื่อจริง
        this.setLastName(""); // ไม่มีข้อมูลนามสกุลจาก decoded token
        this.setMajor(""); // ไม่มีข้อมูลสาขา
        this.setDepartment(""); // ไม่มีข้อมูลแผนก
        this.setGpax(0.0f); // กำหนด GPAX เริ่มต้น
        this.setStudentInterest(""); // ไม่มีข้อมูลความสนใจของนักศึกษา
        this.setSkills(""); // ไม่มีข้อมูลทักษะ
        this.setPhoneNumber(""); // ไม่มีข้อมูลหมายเลขโทรศัพท์
        this.setAddress(""); // ไม่มีข้อมูลที่อยู่
        this.setLinkedInProfile(""); // ไม่มีข้อมูลโปรไฟล์ LinkedIn
//        this.setEnabled(true); // ตั้งค่าสถานะการใช้งานเป็น true
        this.setCreated(LocalDateTime.now()); // ตั้งค่าวันที่สร้างเป็นปัจจุบัน
        this.setUpdated(LocalDateTime.now()); // ตั้งค่าวันที่อัปเดตเป็นปัจจุบัน
    }

}