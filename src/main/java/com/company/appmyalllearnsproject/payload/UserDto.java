package com.company.appmyalllearnsproject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    @NotNull(message = "FullName can not be null")
    private String fullName;
    @NotNull(message = "Username can not be null")
    private String username;
    @NotNull(message = "Password can not be null")
    private String password;
    @NotNull(message = "Role can not be null")
    private Long roleId;
}
