package com.cms.complaint_management_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "complaint_categories")
public class ComplaintCategories {

    @Id
    private long categoryId;

    @Column(nullable = false)
    private String categoryName;

    private long departmentId;

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

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
}
