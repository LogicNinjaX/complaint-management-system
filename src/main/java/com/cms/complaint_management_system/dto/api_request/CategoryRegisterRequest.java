package com.cms.complaint_management_system.dto.api_request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public class CategoryRegisterRequest {

    @Positive(message = "enter positive value")
    private long categoryId;

    @NotEmpty(message = "required field")
    private String categoryName;

    @Positive(message = "enter positive value")
    private long departmentId;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
