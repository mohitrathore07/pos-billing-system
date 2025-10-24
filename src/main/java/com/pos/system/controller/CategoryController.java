package com.pos.system.controller;


import com.pos.system.exceptions.UserException;
import com.pos.system.payload.dto.CategoryDto;
import com.pos.system.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_STORE_MANAGER', 'ROLE_STORE_ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto dto) throws UserException {
        return ResponseEntity.ok(categoryService.createCategory(dto));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<CategoryDto>> getCategories(@PathVariable Long storeId) {
        return ResponseEntity.ok(categoryService.getCategoriesByStore(storeId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_STORE_MANAGER', 'ROLE_STORE_ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id,
                                                      @RequestBody CategoryDto dto) throws UserException {
        return ResponseEntity.ok(categoryService.updateCategory(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_STORE_MANAGER', 'ROLE_STORE_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws UserException {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}

