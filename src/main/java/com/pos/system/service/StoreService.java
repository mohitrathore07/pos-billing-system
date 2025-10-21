package com.pos.system.service;

import com.pos.system.exceptions.UserException;
import com.pos.system.Modal.User;
import com.pos.system.domain.StoreStatus;
import com.pos.system.Modal.Store;
import com.pos.system.payload.dto.StoreDto;

import java.util.List;

public interface StoreService {
    StoreDto createStore(StoreDto storeDto, User user);
    StoreDto getStoreById(Long id) throws Exception;
    List<StoreDto> getAllStores();
    Store getStoreByAdmin() throws Exception;
    StoreDto updateStore(Long id, StoreDto storeDto) throws Exception;
    void deleteStore(Long id) throws Exception;
    StoreDto getStoreByEmployee() throws UserException;

    StoreDto moderateStore(Long id, StoreStatus status) throws Exception;
}
