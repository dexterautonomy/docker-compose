package com.hingebridge.devops.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeDTO {
	private Long id;
	private String firstname;
	private String lastname;
	
	public static EmployeeDTOBuilder builder() {
		return new EmployeeDTOBuilder();
	}
}