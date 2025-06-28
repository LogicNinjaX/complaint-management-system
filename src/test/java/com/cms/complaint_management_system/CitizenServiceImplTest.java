package com.cms.complaint_management_system;

import com.cms.complaint_management_system.dto.CitizenDto;
import com.cms.complaint_management_system.entity.UserRecord;
import com.cms.complaint_management_system.enums.UserRoles;
import com.cms.complaint_management_system.repository.UserRepository;
import com.cms.complaint_management_system.service.impl.CitizenServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CitizenServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CitizenServiceImpl citizenService;

    private UUID citizenId;

    private CitizenDto mockCitizenDto;

    @BeforeEach
    void setup() {

        citizenId = UUID.randomUUID();
        mockCitizenDto = new CitizenDto();
        mockCitizenDto.setUserId(citizenId);
        mockCitizenDto.setRole(UserRoles.CITIZEN);

    }

    @Test
    @DisplayName("when valid id provided")
    void getCitizenDetails_shouldReturnCitizen_whenValidIdAndRole() {

        Mockito.when(userRepository.getCitizenDetails(citizenId)).thenReturn(Optional.of(mockCitizenDto));

        CitizenDto result = citizenService.getCitizenDetails(citizenId);

        Assertions.assertEquals(citizenId, result.getUserId());

        Assertions.assertEquals(UserRoles.CITIZEN, result.getRole());

        Mockito.verify(userRepository).getCitizenDetails(citizenId);
    }
}
