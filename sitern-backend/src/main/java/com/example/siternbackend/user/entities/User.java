package com.example.siternbackend.user.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
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
}