package com.cms.complaint_management_system.dto.api_request;


import jakarta.validation.constraints.NotEmpty;

public class DepartmentUpdateRequest {

    @NotEmpty(message = "required field")
    private String departmentName;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
