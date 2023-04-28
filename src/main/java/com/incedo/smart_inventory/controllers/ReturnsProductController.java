package com.incedo.smart_inventory.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incedo.smart_inventory.entities.Godown;
import com.incedo.smart_inventory.entities.Invoice;
import com.incedo.smart_inventory.entities.Product;
import com.incedo.smart_inventory.entities.ReturnsProduct;
import com.incedo.smart_inventory.repositories.GodownRepository;
import com.incedo.smart_inventory.repositories.InvoiceRepository;
import com.incedo.smart_inventory.repositories.ProductRepository;
import com.incedo.smart_inventory.repositories.ReturnsProductRepository;

@RestController
@RequestMapping("/api")
public class ReturnsProductController {
	
	private static final String PATH = "/returns";
	
	@Autowired
	ReturnsProductRepository returnsProductRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	GodownRepository godownRepository;
	
	@Autowired
	InvoiceRepository invoiceRepository;

	@GetMapping(path=PATH)
	public List<ReturnsProduct> fetchAllReservations(){
		return returnsProductRepository.findAll();
	}

	@GetMapping(path=PATH + "/{id}")
    public ResponseEntity<ReturnsProduct> getById(@PathVariable int id) {

        Optional<ReturnsProduct> returnsProduct = returnsProductRepository.findById(id);
        if (returnsProduct.isPresent()) {
            return new ResponseEntity<ReturnsProduct>(returnsProduct.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<ReturnsProduct>(HttpStatus.NOT_FOUND);
        }
    }
	
	@PutMapping(path=PATH + "/{id}")
	public ResponseEntity editReturns(@RequestBody ReturnsProduct returnsProduct, @PathVariable int id) {
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
		
		ReturnsProduct saved = returnsProductRepository.save(returnsProduct);
		return new ResponseEntity<ReturnsProduct>(saved, HttpStatus.OK);
	}
	
	@PostMapping(path=PATH)
	public ResponseEntity addReturn(@RequestBody ReturnsProduct returnsProduct) {
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
		
		ReturnsProduct saved = returnsProductRepository.save(returnsProduct);
		return new ResponseEntity<ReturnsProduct>(saved, HttpStatus.CREATED);
	}
	
	@DeleteMapping(path=PATH + "/{id}")
	public ResponseEntity<Void> deleteReturns(@PathVariable int id) {
		returnsProductRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
