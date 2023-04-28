package com.incedo.smart_inventory.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incedo.smart_inventory.entities.Employee;
import com.incedo.smart_inventory.entities.Godown;
import com.incedo.smart_inventory.repositories.EmployeeRepository;
import com.incedo.smart_inventory.repositories.GodownRepository;

@RestController
@RequestMapping("/api")
public class GodownController {
	
	private static final String PATH = "/godowns";
	
	@Autowired
	GodownRepository godownRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@GetMapping(path=PATH)
	public ResponseEntity<List<Godown>> godown() {
		return new ResponseEntity<List<Godown>>(godownRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(path=PATH + "/{id}")
	public ResponseEntity findById(@PathVariable int id) {
		Optional<Godown> godownFound = godownRepository.findById(id);
		
		if(godownFound.isEmpty()) {
			return new ResponseEntity<String>("The given id is not found", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Godown>(godownFound.get(), HttpStatus.OK);
	}

	@PostMapping(path=PATH)
	public ResponseEntity addGodown(@RequestBody Godown godown) {
		
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
		
		Godown saved = godownRepository.save(godown);
		return new ResponseEntity<Godown>(saved, HttpStatus.CREATED);
	}
	
	@PutMapping(path=PATH + "/{id}")
	public ResponseEntity updateEntity(@PathVariable int id,@RequestBody Godown godown) {
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
		
		Godown saved = godownRepository.save(godown);
		
		return new ResponseEntity<Godown>(saved, HttpStatus.OK);
	}
	
	@DeleteMapping(path=PATH + "/{id}")
	public ResponseEntity<Void> deleteEntity(@PathVariable int id) {
		godownRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
