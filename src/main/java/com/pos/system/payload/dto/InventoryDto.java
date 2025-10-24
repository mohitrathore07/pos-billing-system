package com.pos.system.payload.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InventoryDto {
    public Long id;
    public BranchDto branch;

    private Long branchId;
    private Long productId;

    private ProductDto product;

    private Integer quantity;

    private LocalDateTime lastUpdate;
}
