package com.pos.system.mapper;

import com.pos.system.Modal.Category;
import com.pos.system.payload.dto.CategoryDto;

public class CategoryMapper {

    public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .storeId(category.getStore().getId())
                .build();
    }
}
