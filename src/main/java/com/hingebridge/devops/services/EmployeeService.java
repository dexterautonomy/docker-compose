package com.hingebridge.devops.services;

import java.util.List;

import com.hingebridge.devops.dto.EmployeeDTO;

public interface EmployeeService {
	List<EmployeeDTO> fetchAll();
	List<EmployeeDTO> add(List<EmployeeDTO> employeeDTOs);
}
