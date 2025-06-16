package com.cms.complaint_management_system.controller;


import com.cms.complaint_management_system.dto.CitizenDto;
import com.cms.complaint_management_system.dto.OfficerDto;
import com.cms.complaint_management_system.dto.api_request.CitizenRegisterRequest;
import com.cms.complaint_management_system.dto.api_request.OfficerRegisterRequest;
import com.cms.complaint_management_system.dto.api_request.UserLoginRequest;
import com.cms.complaint_management_system.dto.api_response.GeneralResponse;
import com.cms.complaint_management_system.entity.UserRecord;
import com.cms.complaint_management_system.security.CustomUserDetails;
import com.cms.complaint_management_system.security.JwtUtil;
import com.cms.complaint_management_system.service.CitizenService;
import com.cms.complaint_management_system.service.OfficerService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final CitizenService citizenService;
    private final OfficerService officerService;

    public UserAuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, ModelMapper modelMapper, CitizenService citizenService, OfficerService officerService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.citizenService = citizenService;
        this.officerService = officerService;
    }

    @PostMapping("/register/citizens")
    public ResponseEntity<GeneralResponse<CitizenDto>> registerCitizen(@Valid @RequestBody CitizenRegisterRequest registerRequest){
        var userEntity = modelMapper.map(registerRequest, UserRecord.class);

        var savedUserEntity = citizenService.saveCitizenDetails(userEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GeneralResponse<>(true, "Citizen details saved successfully", savedUserEntity));
    }

    @PostMapping("/register/officers")
    public ResponseEntity<GeneralResponse<OfficerDto>> registerOfficer(@Valid @RequestBody OfficerRegisterRequest request){
        var officerEntity = officerService.saveOfficerDetails(request);
        officerEntity.setPassword(request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GeneralResponse<>(true, "Officer details saved successfully", officerEntity));
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse<?>> userLogin(@Valid @RequestBody UserLoginRequest request){
        Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String token = jwtUtil.generateToken(userDetails.getUserId(), userDetails.getUsername(), userDetails.getRole().name());

        return ResponseEntity.ok(new GeneralResponse<>(true, "login successful",
                Collections.singletonMap("token", token)));
    }

}
