package com.hingebridge.devops.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hingebridge.devops.dto.EmployeeDTO;
import com.hingebridge.devops.models.Employee;
import com.hingebridge.devops.repositories.EmployeeRepository;
import com.hingebridge.devops.services.EmployeeService;
import com.hingebridge.devops.transformers.ModelTransformers;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
	private final ModelTransformers transformers;
	private final EmployeeRepository employeeRepo;
	
	@Override
	public List<EmployeeDTO> fetchAll() {
		return transformers.employees_EmployeeDTOs(employeeRepo.findAll());
	}

	@Override
	public List<EmployeeDTO> add(List<EmployeeDTO> employeeDTOs) {
		List<Employee> employees = transformers.employeeDTOs_Employees(employeeDTOs);
		employees = employeeRepo.saveAll(employees);
		
		return transformers.employees_EmployeeDTOs(employees);
	}
}
