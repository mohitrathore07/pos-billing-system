package com.pos.system.controller;

import com.pos.system.Modal.User;
import com.pos.system.domain.StoreStatus;
import com.pos.system.exceptions.ResourceNotFoundException;
import com.pos.system.exceptions.UserException;
import com.pos.system.payload.dto.StoreDto;
import com.pos.system.payload.response.ApiResponse;
import com.pos.system.service.StoreService;
import com.pos.system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<StoreDto>createStore(@Valid @RequestBody StoreDto storeDto,@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(storeService.createStore(storeDto,user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> getStoreById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<ApiResponse> deleteStore(@PathVariable Long id) throws Exception {
            storeService.deleteStore(id);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("store deletedSuccessfully");
            return  ResponseEntity.ok(apiResponse);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_STORE_ADMIN')")
    public ResponseEntity<StoreDto> updateStore(
            @PathVariable Long id,
            @RequestBody StoreDto storeDto
    ) throws Exception {
        return ResponseEntity.ok(storeService.updateStore(id,storeDto));
    }

    @PutMapping("/{storeId}/moderate")
    public StoreDto moderateStore(
            @PathVariable Long storeId,
            @RequestParam StoreStatus action
            ) throws Exception {
        return storeService.moderateStore(storeId, action);
    }

 }
