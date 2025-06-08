package com.cms.complaint_management_system.service;

import com.cms.complaint_management_system.entity.UserRecord;

import java.util.UUID;

public interface UserService {

    UserRecord saveUserDetails(UserRecord userRecord);

    void deleteUserDetails(UUID userId);

    UserRecord getUserDetails(UUID uuid);
}
