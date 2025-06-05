package com.cms.complaint_management_system.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "departments")
public class Departments {

    @Id
    private long departmentId;

    @Column(nullable = false)
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
