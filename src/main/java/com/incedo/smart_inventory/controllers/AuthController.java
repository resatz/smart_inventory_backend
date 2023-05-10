package com.incedo.smart_inventory.controllers;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incedo.smart_inventory.controllers.models.ErrorData;
import com.incedo.smart_inventory.controllers.models.LoginData;
import com.incedo.smart_inventory.entities.Employee;
import com.incedo.smart_inventory.repositories.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class AuthController {
	
	private static final String PATH = "/auth";
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@PostMapping(path=PATH + "/login")
	public ResponseEntity addSupplier(@RequestBody LoginData loginData) {
		Optional<Employee> employeeFound = employeeRepository.findByUsername(loginData.getUsername());
		
		if (employeeFound.isEmpty()) {
			return new ResponseEntity<ErrorData>(new ErrorData("INVALID_USERNAME", "The username provided does not exist"), HttpStatus.NOT_FOUND);
		}
		
		if (!BCrypt.checkpw(loginData.getPassword(), employeeFound.get().getPassword())) {
			return new ResponseEntity<ErrorData>(new ErrorData("WRONG_PASSWORD", "The password provided is incorrect"), HttpStatus.UNAUTHORIZED);			
		}
		
		return new ResponseEntity<Employee>(employeeFound.get(), HttpStatus.OK);
	}
	
}
