package com.pos.system.service;

import com.pos.system.exceptions.UserException;
import com.pos.system.payload.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto dto) throws UserException;
    List<CategoryDto> getCategoriesByStore(Long storeId);
    CategoryDto updateCategory(Long id, CategoryDto dto) throws UserException;
    void deleteCategory(Long id) throws UserException;
}
