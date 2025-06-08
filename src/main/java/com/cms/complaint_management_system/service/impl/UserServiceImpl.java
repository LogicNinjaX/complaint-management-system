package com.cms.complaint_management_system.service.impl;

import com.cms.complaint_management_system.entity.UserRecord;
import com.cms.complaint_management_system.repository.UserRepository;
import com.cms.complaint_management_system.service.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserRecord saveUserDetails(UserRecord userRecord) {
        return userRepository.save(userRecord);
    }

    @Override
    public void deleteUserDetails(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserRecord getUserDetails(UUID uuid) {
        return userRepository.findById(uuid).orElse(null);
    }
}
