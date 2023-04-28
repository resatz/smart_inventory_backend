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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incedo.smart_inventory.entities.Godown;
import com.incedo.smart_inventory.entities.Invoice;
import com.incedo.smart_inventory.entities.OutwardsProduct;
import com.incedo.smart_inventory.entities.Product;
import com.incedo.smart_inventory.repositories.GodownRepository;
import com.incedo.smart_inventory.repositories.InvoiceRepository;
import com.incedo.smart_inventory.repositories.OutwardsProductRepository;
import com.incedo.smart_inventory.repositories.ProductRepository;

@RestController
@RequestMapping("/api")
public class OutwardsProductController {
	
	private static final String PATH = "/outwards";
	
	@Autowired
	OutwardsProductRepository outwardsProductRepository;
	
	@Autowired
	GodownRepository godownRepository;
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping(path=PATH)
	public ResponseEntity<List<OutwardsProduct>> outwardsProduct() {
		return new ResponseEntity<List<OutwardsProduct>>(outwardsProductRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(path=PATH + "/{id}")
	public ResponseEntity findById(@PathVariable int id) {
		Optional<OutwardsProduct> deliveriesFound = outwardsProductRepository.findById(id);
		
		if(deliveriesFound.isPresent()) {
			return new ResponseEntity<OutwardsProduct>(deliveriesFound.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("The outwards with the given id is not found",HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(path=PATH)
	public ResponseEntity addProduct(@RequestBody OutwardsProduct outwardsProduct) {
		if(outwardsProduct.getSupplyDate() == LocalDate.MIN || outwardsProduct.getDeliveryDate() == LocalDate.MIN) {
			return new ResponseEntity<String> ("Date should be given in the format dd/MM/yyyy. For example, 30th December 2000 should be given as 30/12/2000.",HttpStatus.BAD_REQUEST);
		}
		
		if(outwardsProduct.getGodown() != null && outwardsProduct.getGodown().getId() > 0) {
			Optional<Godown> godownFound = godownRepository.findById(outwardsProduct.getGodown().getId());
			
			if(godownFound.isEmpty()) {
				return new ResponseEntity<String>("Godown with the given id is not found.", HttpStatus.NOT_FOUND);
			}
			
			outwardsProduct.setGodown(godownFound.get());
		}
		
		if(outwardsProduct.getInvoice()!= null && outwardsProduct.getInvoice().getId() > 0) {
			Optional <Invoice> invoiceFound = invoiceRepository.findById(outwardsProduct.getInvoice().getId());
			
			if(invoiceFound.isEmpty()) {
				return new ResponseEntity<String>("invoice with the given id is not found.", HttpStatus.NOT_FOUND);
			}
			
			outwardsProduct.setInvoice(invoiceFound.get());
		}
		
		if(outwardsProduct.getProduct() != null && outwardsProduct.getProduct().getId() > 0) {
			Optional<Product> godownFound = productRepository.findById(outwardsProduct.getProduct().getId());
			
			if(godownFound.isEmpty()) {
				return new ResponseEntity<String>("Product with the given id is not found.", HttpStatus.NOT_FOUND);
			}
			
			outwardsProduct.setProduct(godownFound.get());
		}
		
		OutwardsProduct saved = outwardsProductRepository.save(outwardsProduct);
		return new ResponseEntity<OutwardsProduct>(saved, HttpStatus.CREATED);
	}
	
	@PutMapping(path=PATH + "/{id}")
	public ResponseEntity editProduct(@PathVariable int id,@RequestBody OutwardsProduct outwardsProduct) {
		Optional<OutwardsProduct> outwardsProductFound = outwardsProductRepository.findById(id);
		
		if(outwardsProductFound.isEmpty()) {
			return new ResponseEntity<String>("The entry with the given id is not found..!", HttpStatus.NOT_FOUND);
		}
		
		if(outwardsProduct.getSupplyDate() == LocalDate.MIN || outwardsProduct.getDeliveryDate() == LocalDate.MIN) {
			return new ResponseEntity<String>("Date should be given in the format dd/MM/yyyy. For example, 30th December 2000 should be given as 30/12/2000.", HttpStatus.BAD_REQUEST);
		}
		
		outwardsProduct.setId(id);
		
		if(outwardsProduct.getGodown().getId() != null && outwardsProduct.getGodown().getId() > 0) {
			Optional<Godown> godownFound = godownRepository.findById(outwardsProduct.getGodown().getId());
			
			if(godownFound.isEmpty()) {
				return new ResponseEntity<String>("Godown with the id is not found...!", HttpStatus.NOT_FOUND);
			}
			
			outwardsProduct.setGodown(godownFound.get());
		}
		
		if(outwardsProduct.getInvoice()!= null && outwardsProduct.getInvoice().getId() > 0) {
			Optional <Invoice> invoiceFound = invoiceRepository.findById(outwardsProduct.getInvoice().getId());
			
			if(invoiceFound.isEmpty()) {
				return new ResponseEntity<String>("invoice with the given id is not found.", HttpStatus.NOT_FOUND);
			}
			
			outwardsProduct.setInvoice(invoiceFound.get());
		}
		
		if(outwardsProduct.getProduct() != null && outwardsProduct.getProduct().getId() > 0) {
			Optional<Product> productFound = productRepository.findById(outwardsProduct.getProduct().getId());
			
			if(productFound.isEmpty()) {
				return new ResponseEntity<String>("Product with the given id is not found.", HttpStatus.NOT_FOUND);
			}
			
			outwardsProduct.setProduct(productFound.get());
		}
		
		OutwardsProduct saved = outwardsProductRepository.save(outwardsProduct);
		return new ResponseEntity<OutwardsProduct>(saved, HttpStatus.OK);
	}
	
	@DeleteMapping(path=PATH + "/{id}")
	public ResponseEntity<Void> deleteEntity(@PathVariable int id) {
		outwardsProductRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}