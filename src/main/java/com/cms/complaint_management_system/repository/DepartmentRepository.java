package com.cms.complaint_management_system.repository;

import com.cms.complaint_management_system.entity.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Departments, Long> {
}
