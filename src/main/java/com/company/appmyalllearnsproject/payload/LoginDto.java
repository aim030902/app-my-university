package com.company.appmyalllearnsproject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto {
    @NotNull(message = "Username can not be null")
    private String username;
    @NotNull(message = "Password can not be null")
    private String password;
}
