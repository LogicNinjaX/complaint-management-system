package com.cms.complaint_management_system.dto.api_request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserLoginRequest {

    @NotEmpty(message = "required field")
    @Size(min = 5, max = 30, message = "username must be between 5-30 characters")
    private String username;

    @NotEmpty(message = "required field")
    @Size(min = 8, message = "password must be 8 characters long or more")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
