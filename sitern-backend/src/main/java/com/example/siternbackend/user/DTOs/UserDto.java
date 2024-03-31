package com.example.siternbackend.user.DTOs;

import com.example.siternbackend.user.entities.Roles;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.example.siternbackend.user.entities.User}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto  {
    private Integer id;
    private String email;
    private String passwordHashed;
    private List<String> authorities;
    private String username;
    private LocalDateTime created;
    private LocalDateTime updated;
}