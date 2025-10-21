package com.pos.system.service.impl;

import com.pos.system.Modal.StoreContact;
import com.pos.system.exceptions.ResourceNotFoundException;
import com.pos.system.exceptions.UserException;
import com.pos.system.Modal.User;
import com.pos.system.domain.StoreStatus;
import com.pos.system.Modal.Store;
import com.pos.system.mapper.StoreMapper;
import com.pos.system.payload.dto.StoreDto;
import com.pos.system.repository.StoreRepository;
import com.pos.system.service.StoreService;
import com.pos.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StoreServiceIml implements StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;


    @Override
    public StoreDto createStore(StoreDto storeDto, User user) {
        Store store = StoreMapper.toEntity(storeDto, user);
        return StoreMapper.toDto(storeRepository.save(store));
    }

    @Override
    public StoreDto getStoreById(Long id) throws Exception {

        Store store = storeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        return StoreMapper.toDto(store);
    }

    @Override
    public List<StoreDto> getAllStores() {
        List<Store> dtos = storeRepository.findAll();
        return dtos.stream().map(StoreMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Store getStoreByAdmin() throws Exception {
        User admin = userService.getCurrentUser();
        return storeRepository.findByStoreAdminId(admin.getId());
    }

    @Override
    public StoreDto updateStore(Long id, StoreDto storeDto) throws Exception {
        User currentUser = userService.getCurrentUser();
        Store existing = storeRepository.findByStoreAdminId(currentUser.getId());

        if(existing == null){
            throw new ResourceNotFoundException("store not found");
        }

        existing.setBrand(storeDto.getBrand());
        existing.setDescription(storeDto.getDescription());

        if (storeDto.getStoreType() != null) {
            existing.setStoreType(storeDto.getStoreType());
        }

        if (storeDto.getContact() != null) {
            StoreContact contact = StoreContact.builder()
                    .address(storeDto.getContact().getAddress())
                    .phone(storeDto.getContact().getPhone())
                    .email(storeDto.getContact().getEmail())
                    .build();
            existing.setStoreContact(contact);
        }
        Store updateStore = storeRepository.save(existing);
        return StoreMapper.toDto(updateStore);
    }

    @Override
    public void deleteStore(Long id) throws Exception {
        Store store= getStoreByAdmin();
        storeRepository.delete(store);
    }

    @Override
    public StoreDto getStoreByEmployee() throws UserException {
        User currentUser= userService.getCurrentUser();

        if(currentUser.getStore() == null) {
            throw new UserException("user does not have enough permissions to access this store");
        }
        return StoreMapper.toDto(currentUser.getStore());
    }

    @Override
    public StoreDto moderateStore(Long id, StoreStatus status) throws ResourceNotFoundException {
        Store store = storeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("store not found with id: "+id));

        store.setStatus(status);
        Store updatedStore =storeRepository.save(store);

        return StoreMapper.toDto(updatedStore);
    }
}
