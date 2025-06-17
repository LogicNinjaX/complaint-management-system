package com.cms.complaint_management_system.service;

import com.cms.complaint_management_system.entity.ComplaintCategories;
import com.cms.complaint_management_system.exception.CategoryException;
import com.cms.complaint_management_system.exception.DepartmentNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface CategoryService {

    List<ComplaintCategories> getCategoryList(int pageNumber, int pageSize, String sortBy, String dir);

    List<ComplaintCategories> getCategoriesByDepartmentId(long departmentId, int pageNumber, int pageSize, String sortBy, String sortDir);

    ComplaintCategories saveCategory(ComplaintCategories category) throws DepartmentNotFoundException, CategoryException, DataIntegrityViolationException;

    ComplaintCategories updateCategory(long categoryId, ComplaintCategories category) throws CategoryException;

    void deleteCategory(long categoryId);
}
