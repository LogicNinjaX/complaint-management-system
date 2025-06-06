package com.cms.complaint_management_system.repository;

import com.cms.complaint_management_system.entity.ComplaintCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintCategoryRepository extends JpaRepository<ComplaintCategories, Long> {
}
