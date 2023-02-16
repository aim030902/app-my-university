package com.company.appmyalllearnsproject.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegisterDto {

    @NotNull(message = "FullName can not be null")
    private String fullName;
    @NotNull(message = "Username can not be null")
    private String username;
    @NotNull(message = "Password can not be null")
    private String password;
    @NotNull(message = "PrePassword can not be null")
    private String prePassword;
}