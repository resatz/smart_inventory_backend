package com.incedo.smart_inventory.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
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

import com.incedo.smart_inventory.entities.Employee;
import com.incedo.smart_inventory.entities.Godown;
import com.incedo.smart_inventory.entities.InvoiceIssued;
import com.incedo.smart_inventory.entities.InvoiceReceived;
import com.incedo.smart_inventory.entities.OutwardsProduct;
import com.incedo.smart_inventory.entities.Product;
import com.incedo.smart_inventory.entities.ProductsStock;
import com.incedo.smart_inventory.entities.ProductsStockCompositeKey;
import com.incedo.smart_inventory.repositories.EmployeeRepository;
import com.incedo.smart_inventory.repositories.GodownRepository;
import com.incedo.smart_inventory.repositories.InvoiceIssuedRepository;
import com.incedo.smart_inventory.repositories.OutwardsProductRepository;
import com.incedo.smart_inventory.repositories.ProductRepository;
import com.incedo.smart_inventory.repositories.ProductsStockRepository;

@RestController
@RequestMapping("/api")
public class OutwardsProductController {
	
	private static final String PATH = "/outwards";
	
	@Autowired
	OutwardsProductRepository outwardsProductRepository;
	
	@Autowired
	GodownRepository godownRepository;
	
	@Autowired
	InvoiceIssuedRepository invoiceIssuedRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	ProductsStockRepository productsStockRepository;
	
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
		
		if (outwardsProduct.getInvoiceIssued() != null) {
			outwardsProduct.getInvoiceIssued().setInvoiceNo(RandomStringUtils.randomAlphanumeric(12));
			
			if(outwardsProduct.getInvoiceIssued().getBillCheckedBy() != null && outwardsProduct.getInvoiceIssued().getBillCheckedBy().getId() > 0) {
				Optional<Employee> employeeFound = employeeRepository.findById(outwardsProduct.getInvoiceIssued().getBillCheckedBy().getId());
				
				if(employeeFound.isEmpty()) {
					return new ResponseEntity<String>("The employee with the given id is not found",HttpStatus.NOT_FOUND);
				}
				outwardsProduct.getInvoiceIssued().setBillCheckedBy(employeeFound.get());
			}
			
			outwardsProduct.getInvoiceIssued().setQuantity(outwardsProduct.getQuantity());
		}
		
		if(outwardsProduct.getProduct() != null && outwardsProduct.getProduct().getId() > 0) {
			Optional<Product> productFound = productRepository.findById(outwardsProduct.getProduct().getId());
			
			if(productFound.isEmpty()) {
				return new ResponseEntity<String>("Product with the given id is not found.", HttpStatus.NOT_FOUND);
			}
			
			outwardsProduct.setProduct(productFound.get());

			if (outwardsProduct.getInvoiceIssued() != null) {
				outwardsProduct.getInvoiceIssued().setProduct(productFound.get());
				if (outwardsProduct.getQuantity() != null) {
					outwardsProduct.getInvoiceIssued().setBillValue(productFound.get().getPrice() * outwardsProduct.getQuantity());
				}
			}
		}
		
		OutwardsProduct saved = outwardsProductRepository.save(outwardsProduct);
		
//		ProductsStockCompositeKey productsStockCompositeKey = new ProductsStockCompositeKey(outwardsProduct.getProduct().getId(), outwardsProduct.getGodown().getId());
//		Optional<ProductsStock> productsStockFound = productsStockRepository.findById(productsStockCompositeKey);
//		if (productsStockFound.isPresent()) {
//			productsStockFound.get().setStock(productsStockFound.get().getStock() - outwardsProduct.getQuantity());
//			productsStockRepository.save(productsStockFound.get());
//		}
		
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
		
		if(outwardsProduct.getGodown() != null && outwardsProduct.getGodown().getId() > 0) {
			Optional<Godown> godownFound = godownRepository.findById(outwardsProduct.getGodown().getId());
			
			if(godownFound.isEmpty()) {
				return new ResponseEntity<String>("Godown with the id is not found...!", HttpStatus.NOT_FOUND);
			}
			
			outwardsProduct.setGodown(godownFound.get());
		}
		
		if (outwardsProduct.getInvoiceIssued() != null && outwardsProduct.getInvoiceIssued().getId() > 0) {
			Optional<InvoiceIssued> invoiceFound = invoiceIssuedRepository.findById(outwardsProduct.getInvoiceIssued().getId());
			
			if (invoiceFound.isEmpty()) {
				return new ResponseEntity<String>("Invoice with the given id not found.", HttpStatus.NOT_FOUND);
			}
			
			outwardsProduct.setInvoiceIssued(invoiceFound.get());
		}
		else if (outwardsProduct.getInvoiceIssued() != null) {
			outwardsProduct.getInvoiceIssued().setInvoiceNo(RandomStringUtils.randomAlphanumeric(12));
			
			if(outwardsProduct.getInvoiceIssued().getBillCheckedBy() != null && outwardsProduct.getInvoiceIssued().getBillCheckedBy().getId() > 0) {
				Optional<Employee> employeeFound = employeeRepository.findById(outwardsProduct.getInvoiceIssued().getBillCheckedBy().getId());
				
				if(employeeFound.isEmpty()) {
					return new ResponseEntity<String>("The employee with the given id is not found",HttpStatus.NOT_FOUND);
				}
				outwardsProduct.getInvoiceIssued().setBillCheckedBy(employeeFound.get());
			}
			
			outwardsProduct.getInvoiceIssued().setQuantity(outwardsProduct.getQuantity());
		}
		
		if(outwardsProduct.getProduct() != null && outwardsProduct.getProduct().getId() > 0) {
			Optional<Product> productFound = productRepository.findById(outwardsProduct.getProduct().getId());
			
			if(productFound.isEmpty()) {
				return new ResponseEntity<String>("Product with the given id is not found.", HttpStatus.NOT_FOUND);
			}
			
			outwardsProduct.setProduct(productFound.get());
			
			if (outwardsProduct.getInvoiceIssued() != null) {
				outwardsProduct.getInvoiceIssued().setProduct(productFound.get());
				if (outwardsProduct.getQuantity() != null) {
					outwardsProduct.getInvoiceIssued().setBillValue(productFound.get().getPrice() * outwardsProduct.getQuantity());
				}
			}
		}
		
		Integer previousQuantity = outwardsProductFound.get().getQuantity();
		Product previousProduct = outwardsProductFound.get().getProduct();
		Godown previousGodown = outwardsProductFound.get().getGodown();
		
		OutwardsProduct saved = outwardsProductRepository.save(outwardsProduct);
		
//		System.out.println(outwardsProduct.getQuantity() + " " + previousQuantity);
//		System.out.println(outwardsProduct.getProduct().getId() + " " + previousProduct.getId());
//		System.out.println(outwardsProduct.getGodown().getId() + " " + previousGodown.getId());
//		if (outwardsProduct.getQuantity() != previousQuantity || outwardsProduct.getProduct().getId() != previousProduct.getId() || outwardsProduct.getGodown().getId() != previousGodown.getId()) {
//			ProductsStockCompositeKey productsStockCompositeKey = new ProductsStockCompositeKey(previousProduct.getId(), previousGodown.getId());
//			Optional<ProductsStock> productsStockFound = productsStockRepository.findById(productsStockCompositeKey);
//			if (productsStockFound.isPresent()) {
//				productsStockFound.get().setStock(productsStockFound.get().getStock() + previousQuantity);
//				productsStockRepository.save(productsStockFound.get());
//			}
//			
//			productsStockCompositeKey = new ProductsStockCompositeKey(outwardsProduct.getProduct().getId(), outwardsProduct.getGodown().getId());
//			productsStockFound = productsStockRepository.findById(productsStockCompositeKey);
//			if (productsStockFound.isPresent()) {
//				productsStockFound.get().setStock(productsStockFound.get().getStock() - outwardsProduct.getQuantity());
//				productsStockRepository.save(productsStockFound.get());
//			}
//		}
		
		return new ResponseEntity<OutwardsProduct>(saved, HttpStatus.OK);
	}
	
	@DeleteMapping(path=PATH + "/{id}")
	public ResponseEntity<Void> deleteEntity(@PathVariable int id) {
		outwardsProductRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}