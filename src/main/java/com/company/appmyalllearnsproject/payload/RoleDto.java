package com.company.appmyalllearnsproject.payload;

import com.company.appmyalllearnsproject.entity.enums.Permission;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class RoleDto {
        @NotNull(message = "Name can not be null") String name;
        String description;
        @NotEmpty
        List<Permission> permissionList;
}
