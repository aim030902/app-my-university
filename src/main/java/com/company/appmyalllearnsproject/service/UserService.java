package com.company.appmyalllearnsproject.service;

import com.company.appmyalllearnsproject.entity.User;
import com.company.appmyalllearnsproject.exception.ResourceNotFoundException;
import com.company.appmyalllearnsproject.payload.ApiResponse;
import com.company.appmyalllearnsproject.payload.UserDto;
import com.company.appmyalllearnsproject.repository.RoleRepository;
import com.company.appmyalllearnsproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    public ApiResponse add(UserDto dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            return new ApiResponse("User already exists", false);
        }
        userRepository.save(new User(
                dto.getFullName(),
                dto.getUsername(),
                passwordEncoder.encode( dto.getPassword()),
                roleRepository.findById(dto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("roles", "roleId", "role")),
                UUID.randomUUID().toString(),
                true
        ));
        return new ApiResponse("User saved successfully", true);
    }
}
