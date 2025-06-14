package com.cms.complaint_management_system.service.impl;

import com.cms.complaint_management_system.dto.CitizenDto;
import com.cms.complaint_management_system.dto.api_request.CitizenUpdateRequest;
import com.cms.complaint_management_system.entity.UserRecord;
import com.cms.complaint_management_system.enums.UserRoles;
import com.cms.complaint_management_system.exception.UserNotFoundException;
import com.cms.complaint_management_system.repository.UserRepository;
import com.cms.complaint_management_system.service.CitizenService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CitizenServiceImpl implements CitizenService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public CitizenServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CitizenDto saveCitizenDetails(UserRecord userRecord) {
        userRecord.setPassword(passwordEncoder.encode(userRecord.getPassword()));
        userRecord.setRole(UserRoles.CITIZEN);
        var user = userRepository.save(userRecord);
        return modelMapper.map(user, CitizenDto.class);
    }

    @Override
    public CitizenDto getCitizenDetails(UUID citizenId) throws UserNotFoundException {
        CitizenDto user =  userRepository.getCitizenDetails(citizenId)
                .orElseThrow(() -> new UserNotFoundException("citizen not found with id: "+citizenId));

        if (user.getRole() != UserRoles.CITIZEN){
            throw new UserNotFoundException("citizen not found with id: "+citizenId);
        }

        return user;
    }

    @Override
    public void deleteCitizenDetails(UUID citizenId) {
        userRepository.deleteCitizenDetails(citizenId);
    }

    @Transactional
    @Override
    public CitizenDto updateCitizenDetails(UUID citizenId, CitizenUpdateRequest request) {
        UserRecord user =  userRepository.findById(citizenId)
                .orElseThrow(() -> new UserNotFoundException("citizen not found with id: "+citizenId));

        if (user.getRole() != UserRoles.CITIZEN){
            throw new UserNotFoundException("citizen not found with id: "+citizenId);
        }

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());

        return saveCitizenDetails(user);
    }
}
