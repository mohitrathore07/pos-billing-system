package com.pos.system.repository;

import com.pos.system.Modal.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category , Long> {
    List<Category> findByStoreId(Long storeId);

}
