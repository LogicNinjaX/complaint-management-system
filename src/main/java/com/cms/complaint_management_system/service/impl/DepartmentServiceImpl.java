package com.cms.complaint_management_system.service.impl;


import com.cms.complaint_management_system.entity.Departments;
import com.cms.complaint_management_system.exception.DepartmentException;
import com.cms.complaint_management_system.exception.DepartmentNotFoundException;
import com.cms.complaint_management_system.repository.DepartmentRepository;
import com.cms.complaint_management_system.service.DepartmentService;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    @Override
    public List<Departments> getDepartmentList(int pageNumber, int pageSize, String sortBy, String sortDir){

        pageNumber = Math.max(1, pageNumber);

        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by(Sort.Direction.ASC,sortBy));

        if (sortDir.toUpperCase().equals(Sort.Direction.DESC.name())){
            pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by(Sort.Direction.DESC,sortBy));
        }

        return departmentRepository.findAll(pageable).toList();
    }

    @Override
    public Departments getDepartmentById(long departmentId) throws DepartmentNotFoundException {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: "+departmentId));
    }

    @Override
    public Departments saveDepartment(Departments departments) {

        Departments savedDepartment;

        if (departmentRepository.existsById(departments.getDepartmentId())){
            throw new DepartmentException("Department id already exist with id: "+departments.getDepartmentId());
        }

        try{
            savedDepartment = departmentRepository.save(departments);
        }catch (DataIntegrityViolationException e){
            throw  new DepartmentException("Department already exist");
        }

        return savedDepartment;
    }

    @Override
    public void deleteDepartmentById(long departmentId) {
        departmentRepository.deleteDepartmentById(departmentId);
    }

}
