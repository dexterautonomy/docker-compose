package com.hingebridge.devops.transformers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hingebridge.devops.dto.EmployeeDTO;
import com.hingebridge.devops.exception.EmployeeException;
import com.hingebridge.devops.models.Employee;

@Component
public class ModelTransformers {
	
	public EmployeeDTO employee_EmployeDTO(Employee employee) {
		EmployeeDTO employeeDTO = EmployeeDTO.builder()
				.id(employee.getId())
				.firstname(employee.getFirstname())
				.lastname(employee.getLastname())
				.build();
		
		return employeeDTO;
	}
	
	public List<EmployeeDTO> employees_EmployeeDTOs(List<Employee> employees){
		if(employees.isEmpty()) {
			throw new EmployeeException("No records found");
		}
		return employees.stream().map(this::employee_EmployeDTO)
				.collect(Collectors.toList());
	}
	
	public Employee employeeDTO_Employee(EmployeeDTO employeeDTO) {
		Employee employee = new Employee();
		employee.setFirstname(employeeDTO.getFirstname());
		employee.setLastname(employeeDTO.getLastname());
		
		return employee;
	}
	
	public List<Employee> employeeDTOs_Employees(List<EmployeeDTO> employeeDTOs){
		if(employeeDTOs.isEmpty()) {
			throw new EmployeeException("No records found");
		}
		return employeeDTOs.stream().map(this::employeeDTO_Employee)
				.collect(Collectors.toList());
	}
}
