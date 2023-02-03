package com.company.appmyalllearnsproject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse {
    private String message;
    private Object object;
    private boolean success;

    public ApiResponse(String message, boolean success){
        this.message = message;
        this.success = success;
    }
}
