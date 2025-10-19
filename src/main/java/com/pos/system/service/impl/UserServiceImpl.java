package com.pos.system.service.impl;

import com.pos.exceptions.UserException;
import com.pos.system.Modal.User;
import com.pos.system.configuration.JwtProvider;
import com.pos.system.repository.UserRepository;
import com.pos.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User getUserByEmail(String email) throws UserException {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UserException("User not found with email: "+email);
        }
        return user;
    }

    @Override
    public User getUserFromJwtToken(String jwt) throws UserException {
        String email =  jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);
        if(user == null) throw new UserException("user not exists with email: "+email);
        return user;
    }

    @Override
    public User getUserById(Long id) throws UserException {
        return userRepository.findById(id).orElseThrow(() -> new UserException("user not found with id: "+id));
    }

    @Override
    public List<User> getUsers() throws UserException {

        return userRepository.findAll();
    }

    @Override
    public User getCurrentUser() throws UserException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UserException("User not found");
        }
        return user;
    }
}
