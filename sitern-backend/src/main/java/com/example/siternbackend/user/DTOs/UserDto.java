package com.example.siternbackend.user.DTOs;

import com.example.siternbackend.user.entities.Roles;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.siternbackend.user.entities.User}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto  {
    private Integer id;
    private String email;
    private String passwordHashed;
    private Roles roles;
    private String username;
    private LocalDateTime created;
    private LocalDateTime updated;
}