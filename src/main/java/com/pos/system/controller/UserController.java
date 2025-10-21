package com.pos.system.controller;

import com.pos.system.exceptions.UserException;
import com.pos.system.Modal.User;
import com.pos.system.mapper.UserMapper;
import com.pos.system.payload.dto.UserDto;
import com.pos.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(
            @RequestHeader("Authorization") String jwt
    ) throws UserException {
        User user =  userService.getUserFromJwtToken(jwt);
        return  ResponseEntity.ok(UserMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws UserException {
        User user =  userService.getUserById(id);
        if(user == null) {
            throw new UserException("user not found");
        }
        return  ResponseEntity.ok(UserMapper.toDto(user));
    }


}
