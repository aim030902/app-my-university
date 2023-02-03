package com.company.appmyalllearnsproject.controller;

import com.company.appmyalllearnsproject.aop.CheckPermission;
import com.company.appmyalllearnsproject.payload.ApiResponse;
import com.company.appmyalllearnsproject.payload.RoleDto;
import com.company.appmyalllearnsproject.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityListeners;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/role")
@EntityListeners(value = AuditingEntityListener.class)
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping
    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    public ResponseEntity<?> add(@Valid @RequestBody RoleDto userDto){
        ApiResponse apiResponse = roleService.add(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    @CheckPermission(permission = "EDIT_ROLE")
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody RoleDto userDto){
        ApiResponse apiResponse = roleService.edit(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
