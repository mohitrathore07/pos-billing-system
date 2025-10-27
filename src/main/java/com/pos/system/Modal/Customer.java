package com.pos.system.Modal;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String fullName;

    private String email;

    private String phone;

    private LocalDateTime createdAt;
    private LocalDateTime  updatedAt;
}
