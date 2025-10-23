package com.pos.system.repository;

import com.pos.system.Modal.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch , Long> {
    List<Branch> findByStoreId(Long storeId);
}
