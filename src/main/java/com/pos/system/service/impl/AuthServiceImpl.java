package com.pos.system.service.impl;

import com.pos.exceptions.UserException;
import com.pos.system.Modal.User;
import com.pos.system.configuration.JwtProvider;
import com.pos.system.domain.UserRole;
import com.pos.system.mapper.UserMapper;
import com.pos.system.payload.dto.UserDto;
import com.pos.system.payload.response.AuthResponse;
import com.pos.system.repository.UserRepository;
import com.pos.system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplamantation customUserImplamantation;

    @Override
    public AuthResponse signup(UserDto userDto) throws UserException {
        User user = userRepository.findByEmail(userDto.getEmail());
        if(user != null) {
            throw new UserException("email is already registered");
        }
        if(userDto.getRole().equals(UserRole.ROLE_ADMIN)) {
            throw new UserException("Role admin is not allowed");
        }

        User createdUser = new User();
        createdUser.setEmail(userDto.getEmail());
        createdUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        createdUser.setCreatedAt(LocalDateTime.now());
        createdUser.setUpdatedAt(LocalDateTime.now());
        createdUser.setPhone(userDto.getPhone());
        createdUser.setLastLogin(LocalDateTime.now());
        createdUser.setFullName(userDto.getFullName());
        createdUser.setRole(userDto.getRole());

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt =jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setTitle("Welcome "+savedUser.getEmail());
        authResponse.setMessage("User registered successfully");
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDto(savedUser));
        System.out.println("user registered: "+authResponse);
        return authResponse;
    }

    @Override
    public AuthResponse login(UserDto userDto) throws UserException {
        Authentication authentication = authenticate(userDto.getEmail(), userDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.iterator().next().getAuthority();
        String token = jwtProvider.generateToken(authentication);

        User user = userRepository.findByEmail(userDto.getEmail());
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setTitle("Login Success");
        authResponse.setMessage("Welcome back: "+userDto.getEmail());
        authResponse.setJwt(token);
        authResponse.setUser(UserMapper.toDto(user));
        return authResponse;
    }

    private Authentication authenticate(String email, String password) throws UserException {
        UserDetails userDetails = customUserImplamantation.loadUserByUsername(email);
        if(userDetails == null) {
            throw new UserException("email id doesn't exist "+email);
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UserException("Wrong Password ");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
