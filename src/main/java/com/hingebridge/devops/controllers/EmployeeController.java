package com.hingebridge.devops.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hingebridge.devops.dto.EmployeeDTO;
import com.hingebridge.devops.services.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("employee/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EmployeeController {
	private final EmployeeService employeeService;
	
	@GetMapping("all")
	public List<EmployeeDTO> fetchAll(){
		return employeeService.fetchAll();
	}
	
	@PostMapping("add")
	public List<EmployeeDTO> add(@RequestBody List<EmployeeDTO> employeeDTOs){
		return employeeService.add(employeeDTOs);
	}
}
