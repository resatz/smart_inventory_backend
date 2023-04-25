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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.incedo.smart_inventory.entities.Invoice;
import com.incedo.smart_inventory.repositories.InvoiceRepository;

@RestController
@ResponseBody
public class InvoiceController {
	
	@Autowired
	InvoiceRepository invoiceRespository;
	
	@GetMapping(path="/invoice")
	public ResponseEntity<List<Invoice>> invoice() {
		return ResponseEntity.ok(invoiceRespository.findAll());
	}
	
	@GetMapping(path="/invoice/{id}")
	public ResponseEntity findById(@PathVariable int id) {
		Optional<Invoice> invoiceFound = invoiceRespository.findById(id);
		
		if(invoiceFound.isPresent()) {
			return new ResponseEntity<Invoice>(invoiceFound.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("The resource with given id doesn't exist", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(path="/invoice")
	public ResponseEntity<String> addInvoice(@RequestBody Invoice invoice) {
		invoiceRespository.save(invoice);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping(path="/invoice/{id}")
	public ResponseEntity<Void> deleteEntity(@PathVariable int id) {
		invoiceRespository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
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
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		return new ResponseEntity<String>("The resource with given id does not exist.", HttpStatus.BAD_REQUEST);
    }
	
}
