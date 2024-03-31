package com.example.siternbackend.user.DTOs;


import com.example.siternbackend.user.entities.Roles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
    @NotNull(message = "shouldn't be null")
    @Size(max = 45, message = "must below 45 Characters")
    @Email
    private String email;

    @NotNull
    @Size(min = 6, max = 255,message = "must between 6 to 255 Characters")
    private String password;


    @NotNull
    @Size(max = 45)
    private String username;


}

