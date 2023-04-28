package com.incedo.smart_inventory.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.smart_inventory.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	public Optional<Employee> findByUsername(String username);
	
}
