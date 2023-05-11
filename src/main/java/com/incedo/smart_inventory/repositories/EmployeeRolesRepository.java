package com.incedo.smart_inventory.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.smart_inventory.entities.EmployeeRoles;

public interface EmployeeRolesRepository extends JpaRepository<EmployeeRoles, Integer> {
	
	public Optional<EmployeeRoles> findByRole(String role);
	
}
