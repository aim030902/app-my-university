package com.company.appmyalllearnsproject.payload;

import com.company.appmyalllearnsproject.entity.enums.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDto {
    private String name;
    private String description;
    @NotEmpty
    private List<Permission> permissionList;
}
