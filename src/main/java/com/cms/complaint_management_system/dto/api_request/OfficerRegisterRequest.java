package com.cms.complaint_management_system.dto.api_request;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class OfficerRegisterRequest {

    @NotEmpty(message = "required field")
    @Size(min = 5, max = 30, message = "username must be between 5-30 characters")
    private String username;

    @NotEmpty(message = "required field")
    @Size(min = 8, message = "password must be 8 characters long or more")
    private String password;

    @NotEmpty(message = "required field")
    private String email;

    @Positive(message = "departmentId must be a positive number")
    private long departmentId;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
