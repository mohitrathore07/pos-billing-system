package com.pos.system.payload.dto;

import com.pos.system.domain.StoreStatus;
import com.pos.system.Modal.StoreContact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreDto {
    private Long id;
    private String brand;
    private UserDto userDto;

    private UserDto storeAdmin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String description;
    private String storeType;
    private StoreStatus status;
    private StoreContact contact;
}
