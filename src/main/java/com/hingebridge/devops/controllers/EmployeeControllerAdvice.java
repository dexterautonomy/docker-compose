package com.hingebridge.devops.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hingebridge.devops.exception.EmployeeException;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmployeeControllerAdvice {
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EmployeeException.class)
	public String handleEmployeeException(EmployeeException ex) {
		return ex.getMessage();
	}
}