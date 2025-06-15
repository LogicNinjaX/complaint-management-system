package com.cms.complaint_management_system.exception;

public class ComplaintNotFoundException extends RuntimeException{

    public ComplaintNotFoundException(String message) {
        super(message);
    }
}
