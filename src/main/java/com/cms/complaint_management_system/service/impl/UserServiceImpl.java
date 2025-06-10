package com.cms.complaint_management_system.service.impl;

import com.cms.complaint_management_system.dto.api_request.UserUpdateRequest;
import com.cms.complaint_management_system.entity.UserRecord;
import com.cms.complaint_management_system.exception.UserException;
import com.cms.complaint_management_system.repository.UserRepository;
import com.cms.complaint_management_system.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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
    public UserRecord getUserDetails(UUID userId) throws UserException{
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException("user not found with: "+ userId));
    }

    @Override
    public UserRecord updateUserDetails(UUID userId, UserUpdateRequest request) {
        var oldUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("user not found with: "+ userId));

        oldUser.setUsername(request.getUsername());
        oldUser.setEmail(request.getEmail());
        oldUser.setPassword(request.getPassword());
        return userRepository.save(oldUser);
    }
}
