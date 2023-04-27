package com.incedo.smart_inventory.controllers;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.incedo.smart_inventory.entities.EmployeeRoles;
import com.incedo.smart_inventory.entities.Employee;
import com.incedo.smart_inventory.entities.Godown;
import com.incedo.smart_inventory.entities.OutwardsProduct;
import com.incedo.smart_inventory.entities.Product;
import com.incedo.smart_inventory.repositories.EmployeeRepository;
import com.incedo.smart_inventory.repositories.EmployeeRolesRepository;

@RestController
public class EmployeeController {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	EmployeeRolesRepository employeerolesrepository;
	
	
	@PostMapping(path="/employees")
	public ResponseEntity<String> addEmployees(@RequestBody Employee employee) {
		
		
		if(employee.getRole()!=null && employee.getRole().getId()>0) {
			Optional<EmployeeRoles> employeeRoleFound = employeerolesrepository.findById(employee.getRole().getId());
			
			if(employeeRoleFound.isEmpty()) {
				return new ResponseEntity<String>("The employee role with the given id is not found",HttpStatus.NOT_FOUND);
			}
			employee.setRole(employeeRoleFound.get());
		}
		
		employeeRepository.save(employee);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	
	
	@GetMapping("/employees")
	public List<Employee> fetchAllEmployees()
	{
		return employeeRepository.findAll();
	}
	

	@GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getById(@PathVariable int id) {

        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return new ResponseEntity<Employee>(employee.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
    }
	
	@DeleteMapping(path="/employees/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
		employeeRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	
		
	@PutMapping(path="/employees/{id}")
	public ResponseEntity<String> updateEntity(@PathVariable int id, @RequestBody Employee employee) {
		Optional<Employee> employeeFound = employeeRepository.findById(id);
		
		if(employeeFound.isEmpty()) {
			return new ResponseEntity<String>("Employee with the given id not found.", HttpStatus.NOT_FOUND);
		}
		
		employee.setId(id);
		
		if(employee.getRole()!=null && employee.getRole().getId()>0) {
			Optional<EmployeeRoles> employeeRoleFound = employeerolesrepository.findById(employee.getRole().getId());
			
			if(employeeRoleFound.isEmpty()) {
				return new ResponseEntity<String>("The employee role with the given id is not found",HttpStatus.NOT_FOUND);
			}
			employee.setRole(employeeRoleFound.get());
		}
		employeeRepository.save(employee);
		return new  ResponseEntity<>(HttpStatus.OK);
	}
	
		
	@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
		StringBuffer error = new StringBuffer();
		ex.getConstraintViolations().forEach(violation -> {
			error.append(violation.getPropertyPath() + " " + violation.getMessage() + "\n");
		});
        return new ResponseEntity<String>(error.toString(), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> handleInvalidFormatException(InvalidFormatException ex) {
		return new ResponseEntity<String>(String.format("`%s` should be of type %s, but the given value \"%s\" is of type %s.", ex.getPath().get(0).getFieldName(), ex.getTargetType().getSimpleName(), ex.getValue(), ex.getValue().getClass().getSimpleName()), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<String> handleMismatchedInputException(MismatchedInputException ex) {
		return new ResponseEntity<String>(String.format("`%s` should be of type %s.", ex.getPath().get(0).getFieldName(), ex.getTargetType().getSimpleName()), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		return new ResponseEntity<String>("The resource with given id does not exist.", HttpStatus.BAD_REQUEST);
    }
}
