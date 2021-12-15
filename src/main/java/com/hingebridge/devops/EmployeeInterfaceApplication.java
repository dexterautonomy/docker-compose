package com.hingebridge.devops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Employee Interface Java", version = "1.0", description = "Testing Docker Networking"))
public class EmployeeInterfaceApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmployeeInterfaceApplication.class, args);
	}
}