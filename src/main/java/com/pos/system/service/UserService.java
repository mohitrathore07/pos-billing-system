package com.pos.system.service;

import com.pos.exceptions.UserException;
import com.pos.system.Modal.User;

import java.util.List;

public interface UserService{
    User  getUserByEmail(String email) throws UserException;
    User getUserFromJwtToken(String jwt) throws UserException;
    User getUserById(Long id) throws UserException;
    List<User> getUsers() throws UserException;
    User getCurrentUser() throws UserException;
}