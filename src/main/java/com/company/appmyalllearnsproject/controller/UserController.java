package com.company.appmyalllearnsproject.controller;

import com.company.appmyalllearnsproject.payload.ApiResponse;
import com.company.appmyalllearnsproject.payload.UserDto;
import com.company.appmyalllearnsproject.service.UserService;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import javax.validation.Valid;
import java.sql.Timestamp;

@RestController
@RequestMapping("/api/user")
@EntityListeners(value = AuditingEntityListener.class)
public class UserController {
    @Autowired
    UserService userService;
    @PreAuthorize(value = "hasAuthority('ADD_USER')")
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.add(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
