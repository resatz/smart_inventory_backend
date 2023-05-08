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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incedo.smart_inventory.entities.Employee;
import com.incedo.smart_inventory.entities.InvoiceReceived;
import com.incedo.smart_inventory.repositories.EmployeeRepository;
import com.incedo.smart_inventory.repositories.InvoiceReceivedRepository;

@RestController
@RequestMapping("/api")
public class InvoiceReceivedController {
	
	private static final String PATH = "/invoiceReceived";
	
	@Autowired
	InvoiceReceivedRepository invoiceReceivedRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@GetMapping(path=PATH)
	public ResponseEntity<List<InvoiceReceived>> invoiceReceived() {
		return ResponseEntity.ok(invoiceReceivedRepository.findAll());
	}
	
	@GetMapping(path=PATH + "/{id}")
	public ResponseEntity findById(@PathVariable int id) {
		Optional<InvoiceReceived> invoiceFound = invoiceReceivedRepository.findById(id);
		
		if(invoiceFound.isPresent()) {
			return new ResponseEntity<InvoiceReceived>(invoiceFound.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("The resource with given id does not exist", HttpStatus.NOT_FOUND);
	}
	
}
