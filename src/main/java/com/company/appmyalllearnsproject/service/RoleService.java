package com.company.appmyalllearnsproject.service;

import com.company.appmyalllearnsproject.entity.Role;
import com.company.appmyalllearnsproject.payload.ApiResponse;
import com.company.appmyalllearnsproject.payload.RoleDto;
import com.company.appmyalllearnsproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public ApiResponse add(RoleDto dto){
        if (roleRepository.existsByName(dto.getName())) {
            return new ApiResponse("Role already exits", false);
        }
        Role role = new Role(
                dto.getName(),
                dto.getPermissionList(),
                dto.getDescription()
        );
        roleRepository.save(role);
        return new ApiResponse("Role successfully exits", true);
    }

    public ApiResponse edit(RoleDto userDto) {
        return new ApiResponse("", true);
    }
}
