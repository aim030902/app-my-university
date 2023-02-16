package com.company.appmyalllearnsproject.mapper;

import com.company.appmyalllearnsproject.entity.Role;
import com.company.appmyalllearnsproject.payload.RoleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role entityFromRoleDto(RoleDto dto);

}
