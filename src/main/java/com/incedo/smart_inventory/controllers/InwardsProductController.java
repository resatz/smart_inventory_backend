package com.incedo.smart_inventory.controllers;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.incedo.smart_inventory.entities.Godown;
import com.incedo.smart_inventory.entities.Invoice;
import com.incedo.smart_inventory.entities.InwardsProduct;
import com.incedo.smart_inventory.entities.Product;
import com.incedo.smart_inventory.entities.Supplier;
import com.incedo.smart_inventory.repositories.GodownRepository;
import com.incedo.smart_inventory.repositories.InvoiceRepository;
import com.incedo.smart_inventory.repositories.InwardsProductRepository;
import com.incedo.smart_inventory.repositories.ProductRepository;
import com.incedo.smart_inventory.repositories.SupplierRepository;

@RestController
public class InwardsProductController {
	
	@Autowired
	InwardsProductRepository inwardsProductRepository;
	
	@Autowired
	SupplierRepository supplierRepository;
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	GodownRepository godownRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping(path="/inwards")
	public ResponseEntity<List<InwardsProduct>> getProducts() {
		return new ResponseEntity<List<InwardsProduct>>(inwardsProductRepository.findAll(), HttpStatus.OK);
	}
	
	@PostMapping(path="/inwards")
	public ResponseEntity<String> addProduct(@RequestBody InwardsProduct inwardsProduct) {
		if (inwardsProduct.getSupplyDate() == LocalDate.MIN) {
			return new ResponseEntity<String>("Date should be given in the format dd/MM/yyyy. For example, 30th December 2000 should be given as 30/12/2000.", HttpStatus.BAD_REQUEST);
		}
		
		if (inwardsProduct.getSupplier() != null && inwardsProduct.getSupplier().getId() > 0) {
			Optional<Supplier> supplierFound = supplierRepository.findById(inwardsProduct.getSupplier().getId());
			
			if (supplierFound.isEmpty()) {
				return new ResponseEntity<String>("Supplier with the given id not found.", HttpStatus.NOT_FOUND);
			}
			
			inwardsProduct.setSupplier(supplierFound.get());
		}
		
		if (inwardsProduct.getInvoice() != null && inwardsProduct.getInvoice().getId() > 0) {
			Optional<Invoice> invoiceFound = invoiceRepository.findById(inwardsProduct.getInvoice().getId());
			
			if (invoiceFound.isEmpty()) {
				return new ResponseEntity<String>("Invoice with the given id not found.", HttpStatus.NOT_FOUND);
			}
			
			inwardsProduct.setInvoice(invoiceFound.get());
		}
		
		if (inwardsProduct.getGodown() != null && inwardsProduct.getGodown().getId() > 0) {
			Optional<Godown> godownFound = godownRepository.findById(inwardsProduct.getGodown().getId());
			
			if (godownFound.isEmpty()) {
				return new ResponseEntity<String>("Godown with the given id not found.", HttpStatus.NOT_FOUND);
			}
			
			inwardsProduct.setGodown(godownFound.get());
		}
		
		if (inwardsProduct.getProduct() != null && inwardsProduct.getProduct().getId() > 0) {
			Optional<Product> productFound = productRepository.findById(inwardsProduct.getProduct().getId());
			
			if (productFound.isEmpty()) {
				return new ResponseEntity<String>("Product with the given id not found.", HttpStatus.NOT_FOUND);
			}
			
			inwardsProduct.setProduct(productFound.get());
		}
		
		inwardsProductRepository.save(inwardsProduct);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping(path="/inwards/{id}")
	public ResponseEntity<String> editProduct(@RequestBody InwardsProduct inwardsProduct, @PathVariable int id) {
		Optional<InwardsProduct> inwardsProductFound = inwardsProductRepository.findById(id);
		
		if (inwardsProductFound.isEmpty()) {
			return new ResponseEntity<String>("Record with the given id not found.", HttpStatus.NOT_FOUND);
		}
		
		if (inwardsProduct.getSupplyDate() == LocalDate.MIN) {
			return new ResponseEntity<String>("Date should be given in the format dd/MM/yyyy. For example, 30th December 2000 should be given as 30/12/2000.", HttpStatus.BAD_REQUEST);
		}
		
		inwardsProduct.setId(id);
		
		if (inwardsProduct.getSupplier() != null && inwardsProduct.getSupplier().getId() > 0) {
			Optional<Supplier> supplierFound = supplierRepository.findById(inwardsProduct.getSupplier().getId());
			
			if (supplierFound.isEmpty()) {
				return new ResponseEntity<String>("Supplier with the given id not found.", HttpStatus.NOT_FOUND);
			}
			
			inwardsProduct.setSupplier(supplierFound.get());
		}
		
		if (inwardsProduct.getInvoice() != null && inwardsProduct.getInvoice().getId() > 0) {
			Optional<Invoice> invoiceFound = invoiceRepository.findById(inwardsProduct.getInvoice().getId());
			
			if (invoiceFound.isEmpty()) {
				return new ResponseEntity<String>("Invoice with the given id not found.", HttpStatus.NOT_FOUND);
			}
			
			inwardsProduct.setInvoice(invoiceFound.get());
		}
		
		if (inwardsProduct.getGodown() != null && inwardsProduct.getGodown().getId() > 0) {
			Optional<Godown> godownFound = godownRepository.findById(inwardsProduct.getGodown().getId());
			
			if (godownFound.isEmpty()) {
				return new ResponseEntity<String>("Godown with the given id not found.", HttpStatus.NOT_FOUND);
			}
			
			inwardsProduct.setGodown(godownFound.get());
		}
		
		if (inwardsProduct.getProduct() != null && inwardsProduct.getProduct().getId() > 0) {
			Optional<Product> productFound = productRepository.findById(inwardsProduct.getProduct().getId());
			
			if (productFound.isEmpty()) {
				return new ResponseEntity<String>("Product with the given id not found.", HttpStatus.NOT_FOUND);
			}
			
			inwardsProduct.setProduct(productFound.get());
		}
		
		inwardsProductRepository.save(inwardsProduct);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(path="/inwards/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
		inwardsProductRepository.deleteById(id);
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
