package com.cms.complaint_management_system.service;

import com.cms.complaint_management_system.entity.Departments;
import com.cms.complaint_management_system.exception.DepartmentNotFoundException;

import java.util.List;

public interface DepartmentService {

    List<Departments> getDepartmentList(int pageNumber, int pageSize, String sortBy, String sortDir);

    Departments getDepartmentById(long departmentId) throws DepartmentNotFoundException;

    Departments saveDepartment(Departments departments);

    void deleteDepartmentById(long departmentId);
}
