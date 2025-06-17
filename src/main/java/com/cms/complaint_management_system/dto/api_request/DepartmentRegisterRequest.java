package com.cms.complaint_management_system.dto.api_request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public class DepartmentRegisterRequest {

    @Positive(message = "enter positive value")
    private long departmentId;

    @NotEmpty(message = "required field")
    private String departmentName;

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
