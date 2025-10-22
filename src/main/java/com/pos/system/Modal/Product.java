package com.pos.system.Modal;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true ,nullable = false)
    private String sku;

    private String description;

    @Column(nullable = false)
    private Double mrp;

   @Column(nullable = false)
    private Double sellingPrice;

   private String brand;

   private String image;

   @ManyToOne
   private Store store;

   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
