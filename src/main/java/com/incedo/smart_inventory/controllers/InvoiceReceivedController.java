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
	
	private static final String PATH = "/invoice";
	
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
		
		return new ResponseEntity<String>("The resource with given id doesn't exist", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(path=PATH)
	public ResponseEntity addInvoice(@RequestBody InvoiceReceived invoiceReceived) {
		
		if(invoiceReceived.getBillCheckedBy()!=null && invoiceReceived.getBillCheckedBy().getId()>0) {
			Optional<Employee> employeeFound = employeeRepository.findById(invoiceReceived.getBillCheckedBy().getId());
			
			if(employeeFound.isEmpty()) {
				return new ResponseEntity<String>("The employee with the given id is not found",HttpStatus.NOT_FOUND);
			}
			invoiceReceived.setBillCheckedBy(employeeFound.get());
		}
			
		InvoiceReceived saved = invoiceReceivedRepository.save(invoiceReceived);
		return new ResponseEntity<InvoiceReceived>(saved, HttpStatus.CREATED);
	}
	
	@DeleteMapping(path=PATH + "/{id}")
	public ResponseEntity<Void> deleteEntity(@PathVariable int id) {
		invoiceReceivedRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
