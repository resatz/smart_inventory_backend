package com.incedo.smart_inventory.controllers;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.incedo.smart_inventory.entities.Supplier;
import com.incedo.smart_inventory.repositories.SupplierRepository;

@RestController
@RequestMapping("/api")
public class SupplierController {
	
	private static final String PATH = "/suppliers";
	
	@Autowired
	SupplierRepository supplierRepository;
	
	@GetMapping(path=PATH)
	public ResponseEntity<List<Supplier>> getSuppliers() {
		return new ResponseEntity<List<Supplier>>(supplierRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(path=PATH + "/{id}")
	public ResponseEntity getSupplierById(@PathVariable int id) {
		Optional<Supplier> supplierFound = supplierRepository.findById(id);
		
		if (supplierFound.isEmpty()) {
			return new ResponseEntity<String>("Supplier with the given id not found", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Supplier>(supplierFound.get(), HttpStatus.OK);
	}
	
	@PostMapping(path=PATH)
	public ResponseEntity<Supplier> addSupplier(@RequestBody Supplier supplier) {
		Supplier saved = supplierRepository.save(supplier);
		return new ResponseEntity<Supplier>(saved, HttpStatus.CREATED);
	}
	
	@PutMapping(path=PATH + "/{id}")
	public ResponseEntity editSupplier(@RequestBody Supplier supplier, @PathVariable int id) {
		Optional<Supplier> supplierFound = supplierRepository.findById(id);
		
		if (supplierFound.isPresent()) {
			supplier.setId(id);
			Supplier saved = supplierRepository.save(supplier);
			return new ResponseEntity<Supplier>(saved, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Supplier with the given id not found.", HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(path=PATH + "/{id}")
	public ResponseEntity<Void> deleteSupplier(@PathVariable int id) {
		supplierRepository.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
