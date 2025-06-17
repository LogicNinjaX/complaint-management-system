package com.cms.complaint_management_system.dto.api_request;

import jakarta.validation.constraints.NotEmpty;

public class CategoryUpdateRequest {

    @NotEmpty(message = "required field")
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
