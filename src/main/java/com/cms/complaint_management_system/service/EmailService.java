package com.cms.complaint_management_system.service;

public interface EmailService {

    void sendEmail(String toEmail, String name, String complaintId, String categoryName, String departmentName, String date);
}
