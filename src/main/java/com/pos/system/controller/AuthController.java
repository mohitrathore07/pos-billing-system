package com.pos.system.controller;

import com.pos.system.exceptions.UserException;
import com.pos.system.configuration.JwtProvider;
import com.pos.system.payload.dto.UserDto;
import com.pos.system.payload.response.AuthResponse;
import com.pos.system.repository.UserRepository;
import com.pos.system.service.AuthService;
import com.pos.system.service.impl.CustomUserImplamantation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplamantation customUserImplamantation;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signupHandler(
            @RequestBody UserDto userDto
    ) throws UserException {

        AuthResponse response = authService.signup(userDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(
            @RequestBody UserDto userDto
    ) throws UserException {
        return ResponseEntity.ok(
                authService.login(userDto)
        );
    }
 }
