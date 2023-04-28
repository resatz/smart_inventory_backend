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
import com.incedo.smart_inventory.entities.Invoice;
import com.incedo.smart_inventory.repositories.EmployeeRepository;
import com.incedo.smart_inventory.repositories.InvoiceRepository;

@RestController
@RequestMapping("/api")
public class InvoiceController {
	
	private static final String PATH = "/invoice";
	
	@Autowired
	InvoiceRepository invoiceRespository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@GetMapping(path=PATH)
	public ResponseEntity<List<Invoice>> invoice() {
		return ResponseEntity.ok(invoiceRespository.findAll());
	}
	
	@GetMapping(path=PATH + "/{id}")
	public ResponseEntity findById(@PathVariable int id) {
		Optional<Invoice> invoiceFound = invoiceRespository.findById(id);
		
		if(invoiceFound.isPresent()) {
			return new ResponseEntity<Invoice>(invoiceFound.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("The resource with given id doesn't exist", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(path=PATH)
	public ResponseEntity addInvoice(@RequestBody Invoice invoice) {
		
		if(invoice.getBillCheckedBy()!=null && invoice.getBillCheckedBy().getId()>0) {
			Optional<Employee> employeeFound = employeeRepository.findById(invoice.getBillCheckedBy().getId());
			
			if(employeeFound.isEmpty()) {
				return new ResponseEntity<String>("The employee with the given id is not found",HttpStatus.NOT_FOUND);
			}
			invoice.setBillCheckedBy(employeeFound.get());
		}
			
		Invoice saved = invoiceRespository.save(invoice);
		return new ResponseEntity<Invoice>(saved, HttpStatus.CREATED);
	}
	
	@DeleteMapping(path=PATH + "/{id}")
	public ResponseEntity<Void> deleteEntity(@PathVariable int id) {
		invoiceRespository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
