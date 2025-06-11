package com.cms.complaint_management_system.controller;


import com.cms.complaint_management_system.dto.api_request.UserLoginRequest;
import com.cms.complaint_management_system.dto.api_response.GeneralResponse;
import com.cms.complaint_management_system.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    public UserAuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse<?>> userLogin(@Valid @RequestBody UserLoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String token = jwtUtil.generateToken(request.getUsername());

        return ResponseEntity.ok(new GeneralResponse<>(true, "login successful",
                Collections.singletonMap("token", token)));
    }

}
