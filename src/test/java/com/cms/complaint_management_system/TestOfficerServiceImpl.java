package com.cms.complaint_management_system;

import com.cms.complaint_management_system.dto.OfficerDto;
import com.cms.complaint_management_system.dto.api_request.OfficerUpdateRequest;
import com.cms.complaint_management_system.entity.Departments;
import com.cms.complaint_management_system.entity.UserRecord;
import com.cms.complaint_management_system.enums.UserRoles;
import com.cms.complaint_management_system.exception.UserNotFoundException;
import com.cms.complaint_management_system.repository.DepartmentRepository;
import com.cms.complaint_management_system.repository.UserRepository;
import com.cms.complaint_management_system.service.impl.OfficerServiceImpl;
import org.junit.jupiter.api.Assertions;
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
public class TestOfficerServiceImpl {

    @Mock
    private UserRepository userRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private OfficerServiceImpl officerService;


    @Test
    @DisplayName("Should update officer and return valid updated officer")
    void updateOfficerDetails_ValidRequest_UpdatesAndReturnsUser() {
        UUID officerId = UUID.randomUUID();

        OfficerUpdateRequest request = new OfficerUpdateRequest();
        request.setUsername("nitish");
        request.setPassword("nitish@123");
        request.setEmail("example@gmail.com");
        request.setDepartmentId(1L);

        UserRecord user = new UserRecord();
        user.setRole(UserRoles.OFFICER);

        Departments department = new Departments();

        Mockito.when(userRepository.findById(officerId))
                .thenReturn(Optional.of(user));

        Mockito.when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserRecord result = officerService.updateOfficerDetails(officerId, request);

        Assertions.assertEquals("nitish", result.getUsername());
        Assertions.assertEquals("nitish@123", result.getPassword());
        Assertions.assertEquals("example@gmail.com", result.getEmail());

        Mockito.verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Should throw UserNotFound exception when unknown officerId provided")
    void updateOfficerDetails_ShouldThrowException_WhenUnknownOfficerId() {
        UUID unknownOfficerId = UUID.randomUUID();

        OfficerUpdateRequest request = new OfficerUpdateRequest();

        Mockito.when(userRepository.findById(unknownOfficerId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            officerService.updateOfficerDetails(unknownOfficerId, request);
        });
    }

    @Test
    @DisplayName("Should return Officer when valid officerId provided")
    void getOfficerDetails_ShouldReturnValidOfficer_WhenValidOfficerId() {
        UUID officerId = UUID.randomUUID();

        OfficerDto mockOfficer = Mockito.mock(OfficerDto.class);

        Mockito.when(mockOfficer.getRole()).thenReturn(UserRoles.OFFICER);
        Mockito.when(userRepository.getOfficerDetails(officerId)).thenReturn(Optional.of(mockOfficer));

        OfficerDto result = officerService.getOfficerDetails(officerId);

        Assertions.assertEquals(mockOfficer, result);
        Mockito.verify(userRepository).getOfficerDetails(officerId);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when officer not found")
    void getOfficerDetails_ShouldThrowException_WhenOfficerNotFound(){
        UUID officerId = UUID.randomUUID();
        Mockito.when(userRepository.getOfficerDetails(officerId)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, ()->{
            officerService.getOfficerDetails(officerId);
        });
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when role is not OFFICER")
    void getOfficerDetails_ShouldThrowException_WhenInvalidUserRole(){
        UUID officerId = UUID.randomUUID();
        OfficerDto mockOfficer = Mockito.mock(OfficerDto.class);

        Mockito.when(mockOfficer.getRole()).thenReturn(UserRoles.CITIZEN);
        Mockito.when(userRepository.getOfficerDetails(officerId)).thenReturn(Optional.of(mockOfficer));

        Assertions.assertThrows(UserNotFoundException.class, ()->{
            officerService.getOfficerDetails(officerId);
        });
    }
}
