package com.pos.system.Modal;

import com.pos.system.domain.StoreStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "blank name is required")
    private String brand;

    @OneToOne
    private User storeAdmin;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String storeType;

    private  String description;

    private StoreStatus status;
    @Embedded
    private StoreContact storeContact = new StoreContact();

    @PrePersist
    protected void OnCreate() {
        createdAt = updatedAt = LocalDateTime.now();
        status = StoreStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
