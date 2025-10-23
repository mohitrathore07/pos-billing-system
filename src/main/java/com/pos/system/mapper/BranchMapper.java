package com.pos.system.mapper;

import com.pos.system.Modal.Branch;
import com.pos.system.Modal.Store;
import com.pos.system.payload.dto.BranchDto;

public class BranchMapper {
    public static BranchDto toDto(Branch branch) {
        return BranchDto.builder()
                .id(branch.getId())
                .name(branch.getName())
                .address(branch.getAddress())
                .phone(branch.getPhone())
                .email(branch.getEmail())
                .closeTime(branch.getCloseTime())
                .openTime(branch.getOpenTime())
                .workingDays(branch.getWorkingDays())
                .storeId(branch.getStore() != null ? branch.getStore().getId() : null)
                .createdAt(branch.getCreatedAt())
                .updatedAt(branch.getUpdatedAt())
                .build();
    }

    public static Branch toEntity(BranchDto dto, Store store) {
        return Branch.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .store(store)
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .closeTime(dto.getCloseTime())
                .openTime(dto.getOpenTime())
                .workingDays(dto.getWorkingDays())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())

                .build();
    }
}
