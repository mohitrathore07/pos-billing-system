package com.pos.system.Modal;

import com.pos.system.domain.UserRole;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "fullName is mandatory")
    private String fullName;

    @Column(nullable = false)
    private String password;


    @Column(nullable = false , unique = true)
    @NotBlank(message = "email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    private String phone;

    @Column(nullable = false)
    private UserRole role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
}
