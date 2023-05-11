package com.incedo.smart_inventory.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.smart_inventory.entities.Employee;
import com.incedo.smart_inventory.entities.InwardsProduct;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	public Optional<Employee> findByUsername(String username);

	public List<Employee> findAllByGodownId(int godownId);
	
	public List<Employee> findAllByIsLocked(boolean isLocked);
	
	public List<Employee> findAllByGodownIdAndIsLocked(int godownId, boolean isLocked);
	
}
