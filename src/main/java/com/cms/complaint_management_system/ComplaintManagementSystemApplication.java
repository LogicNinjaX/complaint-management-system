package com.cms.complaint_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
@EnableCaching
@EnableAsync
public class ComplaintManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComplaintManagementSystemApplication.class, args);
	}

}
