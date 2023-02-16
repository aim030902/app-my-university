package com.company.appmyalllearnsproject.mapper;

import com.company.appmyalllearnsproject.entity.Role;
import com.company.appmyalllearnsproject.entity.User;
import com.company.appmyalllearnsproject.payload.RegisterDto;
import com.company.appmyalllearnsproject.payload.RoleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User entityFromRegisterDto(RegisterDto dto);

}
