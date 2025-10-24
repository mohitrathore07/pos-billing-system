package com.pos.system.mapper;

import com.pos.system.Modal.Branch;
import com.pos.system.Modal.Inventory;
import com.pos.system.Modal.Product;
import com.pos.system.payload.dto.InventoryDto;
import org.springframework.security.core.parameters.P;

public class InventoryMapper {

    public static InventoryDto toDto(Inventory inventory) {
        return InventoryDto.builder()
                .id(inventory.getId())
                .branchId(inventory.getBranch().getId())
                .productId(inventory.getProduct().getId())
                .product(ProductMapper.toDto(inventory.getProduct()))
                .quantity(inventory.getQuantity())
                .build();
    }

    public static Inventory toEntity(InventoryDto inventoryDto, Branch branch, Product product) {
        return Inventory.builder()
                .branch(branch)
                .product(product)
                .quantity(inventoryDto.getQuantity())
                .build();
    }
}
