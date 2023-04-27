package com.incedo.smart_inventory.controllers;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.incedo.smart_inventory.entities.Godown;
import com.incedo.smart_inventory.entities.Invoice;
import com.incedo.smart_inventory.entities.InwardsProduct;
import com.incedo.smart_inventory.entities.OutwardsProduct;
import com.incedo.smart_inventory.entities.Product;
import com.incedo.smart_inventory.entities.ReturnsProduct;
import com.incedo.smart_inventory.entities.Supplier;
import com.incedo.smart_inventory.repositories.GodownRepository;
import com.incedo.smart_inventory.repositories.InvoiceRepository;
import com.incedo.smart_inventory.repositories.ProductRepository;
import com.incedo.smart_inventory.repositories.ReturnsProductRepository;


@RestController
public class ReturnsProductController {
	
	@Autowired
	ReturnsProductRepository returnsProductRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	GodownRepository godownRepository;
	
	@Autowired
	InvoiceRepository invoiceRepository;

	@GetMapping("/returns")
	public List<ReturnsProduct> fetchAllReservations(){
		return returnsProductRepository.findAll();
	}

	@GetMapping("/returns/{id}")
    public ResponseEntity<ReturnsProduct> getById(@PathVariable int id) {

        Optional<ReturnsProduct> returnsProduct = returnsProductRepository.findById(id);
        if (returnsProduct.isPresent()) {
            return new ResponseEntity<ReturnsProduct>(returnsProduct.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<ReturnsProduct>(HttpStatus.NOT_FOUND);
        }
    }
	
	@PutMapping(path="/returns/{id}")
	public ResponseEntity<String> editReturns(@RequestBody ReturnsProduct returnsProduct, @PathVariable int id) {
		Optional<ReturnsProduct> returnFound = returnsProductRepository.findById(id);
		
		if (returnFound.isEmpty()) {
			return new ResponseEntity<String>("Return with the given id not found.", HttpStatus.NOT_FOUND);
		}
		
		if (returnsProduct.getDeliveryDate() == LocalDate.MIN|| returnsProduct.getReturnDate()==LocalDate.MIN) {
			return new ResponseEntity<String>("Date should be given in the format dd/MM/yyyy. For example, 30th December 2000 should be given as 30/12/2000.", HttpStatus.BAD_REQUEST);
		}
		
		returnsProduct.setId(id);
		
		if (returnsProduct.getInvoice() != null && returnsProduct.getInvoice().getId() > 0) {
			Optional<Invoice> invoiceFound = invoiceRepository.findById(returnsProduct.getInvoice().getId());
			
			if (invoiceFound.isEmpty()) {
				return new ResponseEntity<String>("Invoice with the given id not found.", HttpStatus.NOT_FOUND);
			}
			
			returnsProduct.setInvoice(invoiceFound.get());
		}
		
		if (returnsProduct.getProduct() != null && returnsProduct.getProduct().getId() > 0) {
			Optional<Product> productFound = productRepository.findById(returnsProduct.getProduct().getId());
			
			if (productFound.isEmpty()) {
				return new ResponseEntity<String>("Product with the given id not found.", HttpStatus.NOT_FOUND);
			}
			returnsProduct.setProduct(productFound.get());
		}
		if (returnsProduct.getGodown() != null && returnsProduct.getGodown().getId() > 0) {
			Optional<Godown> godownFound = godownRepository.findById(returnsProduct.getGodown().getId());
			
			if (godownFound.isEmpty()) {
				return new ResponseEntity<String>("Godown with the given id not found.", HttpStatus.NOT_FOUND);
			}
			returnsProduct.setGodown(godownFound.get());
		}
		
		returnsProductRepository.save(returnsProduct);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(path="/returns")
	public ResponseEntity<String> addReturn(@RequestBody ReturnsProduct returnsProduct) {
		if(returnsProduct.getDeliveryDate() == LocalDate.MIN || returnsProduct.getReturnDate() == LocalDate.MIN) {
			return new ResponseEntity<String> ("Date should be given in the format dd/MM/yyyy. For example, 30th December 2000 should be given as 30/12/2000.",HttpStatus.BAD_REQUEST);
		}
		if (returnsProduct.getInvoice() != null && returnsProduct.getInvoice().getId() > 0) {
			Optional<Invoice> invoiceFound = invoiceRepository.findById(returnsProduct.getInvoice().getId());
			
			if (invoiceFound.isEmpty()) {
				return new ResponseEntity<String>("Invoice with the given id not found.", HttpStatus.NOT_FOUND);
			}
			returnsProduct.setInvoice(invoiceFound.get());
		}
		if (returnsProduct.getProduct() != null && returnsProduct.getProduct().getId() > 0) {
			Optional<Product> productFound = productRepository.findById(returnsProduct.getProduct().getId());
			
			if (productFound.isEmpty()) {
				return new ResponseEntity<String>("Product with the given id not found.", HttpStatus.NOT_FOUND);
			}
			returnsProduct.setProduct(productFound.get());
		}
		if (returnsProduct.getGodown() != null && returnsProduct.getGodown().getId() > 0) {
			Optional<Godown> godownFound = godownRepository.findById(returnsProduct.getGodown().getId());
			
			if (godownFound.isEmpty()) {
				return new ResponseEntity<String>("Godown with the given id not found.", HttpStatus.NOT_FOUND);
			}
			returnsProduct.setGodown(godownFound.get());
		}
		returnsProductRepository.save(returnsProduct);
		return new ResponseEntity<>(HttpStatus.CREATED);
	    }
	
	@DeleteMapping(path="/returns/{id}")
	public ResponseEntity<Void> deleteReturns(@PathVariable int id) {
		returnsProductRepository.deleteById(id);
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
	
	}
	

