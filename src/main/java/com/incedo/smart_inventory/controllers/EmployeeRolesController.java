package com.incedo.smart_inventory.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.incedo.smart_inventory.entities.EmployeeRoles;
import com.incedo.smart_inventory.repositories.EmployeeRolesRepository;

@RestController
@RequestMapping("/api")
public class EmployeeRolesController {
	
	private static final String PATH = "/employeeRoles";
	
	@Autowired
	EmployeeRolesRepository employeeRolesRepository;
	
	
	@PostMapping(path=PATH)
	public ResponseEntity<EmployeeRoles> addEmployeeRoles(@RequestBody EmployeeRoles e) {
	   EmployeeRoles saved = employeeRolesRepository.save(e);
	   return new ResponseEntity<EmployeeRoles>(saved, HttpStatus.CREATED);
	}
	
	
	@GetMapping(path=PATH)
	public ResponseEntity<List<EmployeeRoles>> getEmployeeRoles() {
		return new ResponseEntity<List<EmployeeRoles>>(employeeRolesRepository.findAll(), HttpStatus.OK);
	}
    
	@GetMapping(path=PATH + "/{id}")
	public ResponseEntity findById(@PathVariable int id) {
		Optional<EmployeeRoles> roleFound = employeeRolesRepository.findById(id);
		
		if(roleFound.isPresent()) {
			return new ResponseEntity<EmployeeRoles>(roleFound.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("The Employee role with the given id doesn't exist", HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping(path=PATH + "/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
		employeeRolesRepository.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	@PutMapping(path=PATH + "/{id}")
	public ResponseEntity editEmployeeRoles(@RequestBody EmployeeRoles employeeRoles, @PathVariable int id) {
		Optional<EmployeeRoles> roles = employeeRolesRepository.findById(id);
		
		if (roles.isPresent()) {
			employeeRoles.setId(id);
			EmployeeRoles saved = employeeRolesRepository.save(employeeRoles);
			return new ResponseEntity<EmployeeRoles>(saved, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("The given id is not found", HttpStatus.NOT_FOUND);
		}
	}
}

		
   
   

	



	
	