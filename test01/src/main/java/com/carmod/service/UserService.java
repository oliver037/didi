package com.carmod.service;

import com.carmod.dto.UserDto;
import com.carmod.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User registerUser(UserDto userDto);
    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    String generateToken(String username);
    User getCurrentUser();
} 