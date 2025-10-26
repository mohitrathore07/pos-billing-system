package com.pos.system.mapper;

import com.pos.system.Modal.User;
import com.pos.system.payload.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public static UserDto toDto(User savedUser) {
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setFullName(savedUser.getFullName());
        userDto.setEmail(savedUser.getEmail());
        userDto.setRole(savedUser.getRole());
        userDto.setCreatedAt(savedUser.getCreatedAt());
        userDto.setUpdatedAt(savedUser.getUpdatedAt());
        userDto.setLastLogin(savedUser.getLastLogin());
        userDto.setBranchId(savedUser.getBranch()!=null?savedUser.getBranch().getId():null);
        userDto.setStoreId(savedUser.getStore()!=null?savedUser.getStore().getId():null);
        userDto.setPhone(savedUser.getPhone());
        return userDto;
    }

    public  static  User toEntity(UserDto userDto) {
        User createdUser = new User();
        createdUser.setEmail(userDto.getEmail());
        createdUser.setFullName(userDto.getFullName());
        createdUser.setRole(userDto.getRole());
        createdUser.setCreatedAt(userDto.getCreatedAt());
        createdUser.setUpdatedAt(userDto.getUpdatedAt());
        createdUser.setLastLogin(userDto.getLastLogin());
        createdUser.setPhone(userDto.getPhone());
        createdUser.setPassword(userDto.getPassword());
        return createdUser;
    }
}
