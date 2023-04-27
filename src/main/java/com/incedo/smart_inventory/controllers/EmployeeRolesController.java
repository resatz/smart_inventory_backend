package com.incedo.smart_inventory.controllers;

import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.incedo.smart_inventory.entities.EmployeeRoles;
import com.incedo.smart_inventory.entities.Product;
import com.incedo.smart_inventory.repositories.EmployeeRolesRepository;

@RestController
public class EmployeeRolesController
{
	@Autowired
	EmployeeRolesRepository employeeRolesRepository;
	
	
	@PostMapping("/employeeRoles")
	public ResponseEntity<Void> addemployeeroles(@RequestBody EmployeeRoles e) {
	   employeeRolesRepository.save(e);
	   ResponseEntity<Void> re = new ResponseEntity<>(HttpStatus.CREATED);
	   return re;
	}
	
	
	@GetMapping(path="/employeeRoles")
	public ResponseEntity<List<EmployeeRoles>> getEmployeeRoles() {
		return new ResponseEntity<List<EmployeeRoles>>(employeeRolesRepository.findAll(), HttpStatus.OK);
	}
    
	@GetMapping(path="/employeeRoles/{id}")
	public ResponseEntity findById(@PathVariable int id) {
		Optional<EmployeeRoles> roleFound = employeeRolesRepository.findById(id);
		
		if(roleFound.isPresent()) {
			return new ResponseEntity<EmployeeRoles>(roleFound.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("The Employee role with the given id doesn't exist", HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping(path="/employeeRoles/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
		employeeRolesRepository.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	@PutMapping(path="/employeeRoles/{id}")
	public ResponseEntity<String> editEmployeeRoles(@RequestBody EmployeeRoles employeeRoles, @PathVariable int id) {
		Optional<EmployeeRoles> roles = employeeRolesRepository.findById(id);
		
		if (roles.isPresent()) {
			employeeRoles.setId(id);
			employeeRolesRepository.save(employeeRoles);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("The given id is not found", HttpStatus.NOT_FOUND);
		}
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

		
   
   

	



	
	