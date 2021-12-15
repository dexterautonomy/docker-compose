package com.hingebridge.devops.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hingebridge.devops.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
