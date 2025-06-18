package com.cms.complaint_management_system.entity;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "departments")
public class Departments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private long departmentId;

    @Column(nullable = false, unique = true)
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
