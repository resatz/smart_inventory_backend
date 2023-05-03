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
import org.springframework.web.bind.annotation.RestController;

import com.incedo.smart_inventory.entities.Employee;
import com.incedo.smart_inventory.entities.InvoiceIssued;
import com.incedo.smart_inventory.entities.InvoiceReceived;
import com.incedo.smart_inventory.repositories.EmployeeRepository;
import com.incedo.smart_inventory.repositories.InvoiceIssuedRepository;
import com.incedo.smart_inventory.repositories.InvoiceReceivedRepository;

@RestController
@RequestMapping("/api")
public class InvoiceIssuedController {
	
	private static final String PATH = "/invoiceIssued";
	
	@Autowired
	InvoiceIssuedRepository invoiceIssuedRespository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@GetMapping(path=PATH)
	public ResponseEntity<List<InvoiceIssued>> invoiceIssued() {
		return ResponseEntity.ok(invoiceIssuedRespository.findAll());
	}
	
	@GetMapping(path=PATH + "/{id}")
	public ResponseEntity findById(@PathVariable int id) {
		Optional<InvoiceIssued> invoiceIssuedFound = invoiceIssuedRespository.findById(id);
		
		if(invoiceIssuedFound.isPresent()) {
			return new ResponseEntity<InvoiceIssued>(invoiceIssuedFound.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("The resource with given id doesn't exist", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(path=PATH)
	public ResponseEntity addInvoice(@RequestBody InvoiceIssued invoiceIssued) {
		
		if(invoiceIssued.getBillCheckedBy()!=null && invoiceIssued.getBillCheckedBy().getId()>0) {
			Optional<Employee> employeeFound = employeeRepository.findById(invoiceIssued.getBillCheckedBy().getId());
			
			if(employeeFound.isEmpty()) {
				return new ResponseEntity<String>("The employee with the given id is not found",HttpStatus.NOT_FOUND);
			}
			invoiceIssued.setBillCheckedBy(employeeFound.get());
		}
			
		InvoiceIssued saved = invoiceIssuedRespository.save(invoiceIssued);
		return new ResponseEntity<InvoiceIssued>(saved, HttpStatus.CREATED);
	}
	
	@DeleteMapping(path=PATH + "/{id}")
	public ResponseEntity<Void> deleteEntity(@PathVariable int id) {
		invoiceIssuedRespository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
