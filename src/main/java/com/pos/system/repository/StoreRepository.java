package com.pos.system.repository;

import com.pos.system.domain.StoreStatus;
import com.pos.system.Modal.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store , Long> {
    Store findByStoreAdminId(Long adminId);
    List<Store> findByStatus(StoreStatus status);
}
