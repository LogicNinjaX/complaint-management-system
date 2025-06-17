package com.cms.complaint_management_system.dto.api_request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ComplaintRegisterRequest {

    @Positive(message = "enter positive value")
    private long categoryId;

    @NotEmpty(message = "required field")
    @Size(min = 5, message = "title must be between 5 characters long")
    private String title;

    @NotEmpty(message = "required field")
    private String description;

    @NotEmpty(message = "required field")
    private String address;

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
