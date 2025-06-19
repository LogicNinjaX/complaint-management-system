package com.cms.complaint_management_system.service.impl;

import com.cms.complaint_management_system.dto.ComplaintDto;
import com.cms.complaint_management_system.dto.ComplaintWithoutUserDto;
import com.cms.complaint_management_system.dto.api_request.ComplaintRegisterRequest;
import com.cms.complaint_management_system.entity.ComplaintRecord;
import com.cms.complaint_management_system.entity.UserRecord;
import com.cms.complaint_management_system.enums.ComplaintStatus;
import com.cms.complaint_management_system.exception.CategoryNotFoundException;
import com.cms.complaint_management_system.exception.ComplaintNotFoundException;
import com.cms.complaint_management_system.exception.UserNotFoundException;
import com.cms.complaint_management_system.repository.ComplaintCategoryRepository;
import com.cms.complaint_management_system.repository.ComplaintRepository;
import com.cms.complaint_management_system.repository.UserRepository;
import com.cms.complaint_management_system.service.ComplaintService;
import com.cms.complaint_management_system.service.DepartmentService;
import com.cms.complaint_management_system.service.EmailService;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintCategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final DepartmentService departmentService;

    public ComplaintServiceImpl(ComplaintRepository complaintRepository, ComplaintCategoryRepository categoryRepository, UserRepository userRepository, EntityManager entityManager, ModelMapper modelMapper, EmailService emailService, DepartmentService departmentService) {
        this.complaintRepository = complaintRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
        this.departmentService = departmentService;
    }

    @Override
    public ComplaintDto registerComplaint(UUID userId, ComplaintRegisterRequest request) throws UserNotFoundException, CategoryNotFoundException {
        var category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        ComplaintRecord complaint = new ComplaintRecord(request.getTitle(), request.getDescription(), request.getAddress(), user);
        complaint.setCategory(category);
        var savedComplaint = complaintRepository.save(complaint);

        var department = departmentService.getDepartmentById(category.getDepartmentId());

        emailService.sendEmail(user.getEmail(), user.getUsername(), savedComplaint.getComplaintId().toString(), savedComplaint.getCategory().getCategoryName(), department.getDepartmentName(), savedComplaint.getCreatedAt().toString());

        return modelMapper.map(savedComplaint, ComplaintDto.class);
    }

    @Override
    public List<ComplaintWithoutUserDto> getComplainsByStatus(ComplaintStatus status, int pageNumber, int pageSize, String sortBy, String sortDir) {
        pageNumber = Math.max(1,pageNumber);
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by(sortBy).ascending());

        if (sortDir.equalsIgnoreCase("desc")){
            PageRequest.of(pageNumber-1, pageSize, Sort.by(sortBy).descending());
        }
        return complaintRepository.getComplainsByStatus(status, pageable).toList();
    }

    @Override
    public ComplaintWithoutUserDto getComplainByComplaintId(UUID complaintId) throws ComplaintNotFoundException {
        return complaintRepository.getComplainByComplaintId(complaintId)
                .orElseThrow(() -> new ComplaintNotFoundException("Complaint not found with id: "+complaintId));
    }

    @Override
    public List<ComplaintWithoutUserDto> getComplainsByUserId(UUID userId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        pageNumber = Math.max(1,pageNumber);
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by(sortBy).ascending());

        if (sortDir.equalsIgnoreCase("desc")){
            PageRequest.of(pageNumber-1, pageSize, Sort.by(sortBy).descending());
        }

        return complaintRepository.getComplainsByUserId(userId, pageable).toList();
    }

    @Override
    public void updateComplaintStatus(UUID complaintId, ComplaintStatus status) throws ComplaintNotFoundException {
        int result = complaintRepository.updateComplaintStatus(complaintId, status);
        if (result == 0){
            throw new ComplaintNotFoundException("Complaint not found with id: "+complaintId);
        }
    }

    @Override
    public void deleteComplaintByComplaintId(UUID complaintId) {
        complaintRepository.deleteByComplaintId(complaintId);
    }


}
