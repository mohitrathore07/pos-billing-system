package com.pos.system.service;

import com.pos.exceptions.UserException;
import com.pos.system.payload.dto.UserDto;
import com.pos.system.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(UserDto userDto) throws UserException;
    AuthResponse login(UserDto userDto) throws UserException;
}
