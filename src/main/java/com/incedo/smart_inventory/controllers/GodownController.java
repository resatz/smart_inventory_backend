package com.incedo.smart_inventory.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.incedo.smart_inventory.entities.Employee;
import com.incedo.smart_inventory.entities.Godown;
import com.incedo.smart_inventory.repositories.EmployeeRepository;
import com.incedo.smart_inventory.repositories.GodownRepository;

@ResponseBody
@RestController
public class GodownController {
	
	@Autowired
	GodownRepository godownRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@GetMapping(path="/godowns")
	public ResponseEntity<List<Godown>> godown() {
		return new ResponseEntity<List<Godown>>(godownRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(path="/godowns/{id}")
	public ResponseEntity findById(@PathVariable int id) {
		Optional<Godown> godownFound = godownRepository.findById(id);
		
		if(godownFound.isEmpty()) {
			return new ResponseEntity<String>("The given id is not found", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Godown>(godownFound.get(), HttpStatus.OK);
	}

	@PostMapping(path="/godowns")
	public ResponseEntity<String> addGodown(@RequestBody Godown godown) {
		
		if(godown.getStartDate() == LocalDate.MIN) {
			return new ResponseEntity<String>("Date should be given in the format dd/MM/yyyy. For example, 30th December 2000 should be given as 30/12/2000.", HttpStatus.BAD_REQUEST);
		}
		
		if(godown.getManager()!=null && godown.getManager().getId()>0) {
			Optional<Employee> employeeFound= employeeRepository.findById(godown.getManager().getId());
			
			if(employeeFound.isEmpty()) {
				return new ResponseEntity<String>("Employee with the given id is not found",HttpStatus.NOT_FOUND);
			}
			
			godown.setManager(employeeFound.get());
			
		}
		
		godownRepository.save(godown);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping(path="/godowns/{id}")
	public ResponseEntity<String> updateEntity(@PathVariable int id,@RequestBody Godown godown) {
		Optional<Godown> godownFound = godownRepository.findById(id);
		
		if(godownFound.isEmpty()) {
			return new ResponseEntity<String>("The godown with the given id is not found",HttpStatus.NOT_FOUND);
		}
		
		if(godown.getStartDate()== LocalDate.MIN) {
			return new ResponseEntity<String>("Date should be given in the format dd/MM/yyyy. For example, 30th December 2000 should be given as 30/12/2000.", HttpStatus.BAD_REQUEST);
		}
		
		godown.setId(id);
		
		if(godown.getManager()!=null && godown.getManager().getId()>0) {
			Optional<Employee> employeeFound = employeeRepository.findById(godown.getManager().getId());
			
			if(employeeFound.isEmpty()) {
				return new ResponseEntity<String>("The employee with the given id is not found",HttpStatus.NOT_FOUND);
			}
			
			godown.setManager(employeeFound.get());
		
		}
		
		godownRepository.save(godown);
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping(path="/godowns/{id}")
	public ResponseEntity<Void> deleteEntity(@PathVariable int id) {
		godownRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
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
