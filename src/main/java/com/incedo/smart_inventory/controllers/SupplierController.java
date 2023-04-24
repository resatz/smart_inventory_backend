package com.incedo.smart_inventory.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.incedo.smart_inventory.entities.Supplier;
import com.incedo.smart_inventory.repositories.SupplierRepository;

@RestController
public class SupplierController {
	
	@Autowired
	SupplierRepository supplierRepository;
	
	@GetMapping(path="/suppliers")
	public ResponseEntity<List<Supplier>> getSuppliers() {
		return new ResponseEntity<List<Supplier>>(supplierRepository.findAll(), HttpStatus.OK);
	}
	
	@PostMapping(path="/suppliers")
	public ResponseEntity<Void> addSupplier(@RequestBody Supplier supplier) {
		supplierRepository.save(supplier);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PutMapping(path="/suppliers/{id}")
	public ResponseEntity<String> editSupplier(@RequestBody Supplier supplier, @PathVariable int id) {
		Optional<Supplier> supplierFound = supplierRepository.findById(id);
		
		if (supplierFound.isPresent()) {
			supplier.setId(id);
			supplierRepository.save(supplier);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Supplier with the given id not found.", HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(path="/suppliers/{id}")
	public ResponseEntity<Void> deleteSupplier(@PathVariable int id) {
		supplierRepository.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
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
	
}
