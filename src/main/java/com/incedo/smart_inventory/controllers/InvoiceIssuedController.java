package com.incedo.smart_inventory.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incedo.smart_inventory.entities.InvoiceIssued;
import com.incedo.smart_inventory.repositories.InvoiceIssuedRepository;

@RestController
@RequestMapping("/api")
public class InvoiceIssuedController {
	
	private static final String PATH = "/invoiceIssued";
	
	@Autowired
	InvoiceIssuedRepository invoiceIssuedRespository;
	
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
		
		return new ResponseEntity<String>("The resource with given id does not exist", HttpStatus.NOT_FOUND);
	}

}
