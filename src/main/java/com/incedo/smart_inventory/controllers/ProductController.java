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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.incedo.smart_inventory.entities.Product;
import com.incedo.smart_inventory.repositories.ProductRepository;

@RestController
@ResponseBody
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping(path="/products")
	public ResponseEntity<List<Product>> product() {
		return new ResponseEntity<List<Product>>(productRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(path="/products/{id}")
	public ResponseEntity findById(@PathVariable int id) {
		Optional<Product> productFound = productRepository.findById(id);
		
		if(productFound.isPresent()) {
			return new ResponseEntity<Product>(productFound.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("The resource with the given id doesn't exist", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(path="/products")
	public ResponseEntity<Void> createEntity(@RequestBody Product product) {
		productRepository.save(product);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PutMapping(path="/products/{id}")
	public ResponseEntity<String> updateEntity(@PathVariable int id,@RequestBody Product product) {
		Optional<Product> productFound = productRepository.findById(id);
		
		if(productFound.isPresent()) {
			product.setId(id);
			productRepository.save(product);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Record with the given id not found.", HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping(path="/products/{id}")
	public ResponseEntity<Void> deleteEntity(@PathVariable int id) {
		productRepository.deleteById(id);
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
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		return new ResponseEntity<String>("The resource with given id does not exist.", HttpStatus.BAD_REQUEST);
    }
}
