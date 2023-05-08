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

import com.incedo.smart_inventory.entities.Employee;
import com.incedo.smart_inventory.entities.Godown;
import com.incedo.smart_inventory.entities.InvoiceIssued;
import com.incedo.smart_inventory.entities.Product;
import com.incedo.smart_inventory.entities.ProductsStock;
import com.incedo.smart_inventory.entities.ProductsStockCompositeKey;
import com.incedo.smart_inventory.entities.ReturnsProduct;
import com.incedo.smart_inventory.repositories.EmployeeRepository;
import com.incedo.smart_inventory.repositories.GodownRepository;
import com.incedo.smart_inventory.repositories.InvoiceIssuedRepository;
import com.incedo.smart_inventory.repositories.ProductRepository;
import com.incedo.smart_inventory.repositories.ProductsStockRepository;
import com.incedo.smart_inventory.repositories.ReturnsProductRepository;

@RestController
@RequestMapping("/api")
public class ReturnsProductController {
	
	private static final String PATH = "/returns";
	
	@Autowired
	ReturnsProductRepository returnsProductRepository;
	
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
		Optional<ReturnsProduct> returnsProductFound = returnsProductRepository.findById(id);
		
		if (returnsProductFound.isEmpty()) {
			return new ResponseEntity<String>("Return with the given id not found.", HttpStatus.NOT_FOUND);
		}
		
		if (returnsProduct.getDeliveryDate() == LocalDate.MIN|| returnsProduct.getReturnDate()==LocalDate.MIN) {
			return new ResponseEntity<String>("Date should be given in the format dd/MM/yyyy. For example, 30th December 2000 should be given as 30/12/2000.", HttpStatus.BAD_REQUEST);
		}
		
		returnsProduct.setId(id);
		
		if (returnsProduct.getGodown() != null && returnsProduct.getGodown().getId() > 0) {
			Optional<Godown> godownFound = godownRepository.findById(returnsProduct.getGodown().getId());
			
			if (godownFound.isEmpty()) {
				return new ResponseEntity<String>("Godown with the given id not found.", HttpStatus.NOT_FOUND);
			}
			returnsProduct.setGodown(godownFound.get());
		}
		
		if (returnsProduct.getInvoiceIssued() != null && returnsProduct.getInvoiceIssued().getId() > 0) {
			Optional<InvoiceIssued> invoiceFound = invoiceIssuedRepository.findById(returnsProduct.getInvoiceIssued().getId());
			
			if (invoiceFound.isEmpty()) {
				return new ResponseEntity<String>("Invoice with the given id not found.", HttpStatus.NOT_FOUND);
			}
			
			returnsProduct.setInvoiceIssued(invoiceFound.get());
		}
		else if (returnsProduct.getInvoiceIssued() != null) {
			if (returnsProduct.getInvoiceIssued().getBillCheckedBy() != null && returnsProduct.getInvoiceIssued().getBillCheckedBy().getId() > 0) {
				Optional<Employee> employeeFound = employeeRepository.findById(returnsProduct.getInvoiceIssued().getBillCheckedBy().getId());
				
				if (employeeFound.isEmpty()) {
					return new ResponseEntity<String>("Employee with the given id not found.", HttpStatus.NOT_FOUND);
				}
				
				returnsProduct.getInvoiceIssued().setBillCheckedBy(employeeFound.get());
			}
			
			returnsProduct.getInvoiceIssued().setQuantity(returnsProduct.getQuantity());
		}
		
		if (returnsProduct.getProduct() != null && returnsProduct.getProduct().getId() > 0) {
			Optional<Product> productFound = productRepository.findById(returnsProduct.getProduct().getId());
			
			if (productFound.isEmpty()) {
				return new ResponseEntity<String>("Product with the given id not found.", HttpStatus.NOT_FOUND);
			}
			
			returnsProduct.setProduct(productFound.get());
			
			if (returnsProduct.getInvoiceIssued() != null) {
				returnsProduct.getInvoiceIssued().setProduct(productFound.get());
				if (returnsProduct.getQuantity() != null) {
					returnsProduct.getInvoiceIssued().setBillValue(productFound.get().getPrice() * returnsProduct.getQuantity());
				}
			}
		}
		
		Integer previousQuantity = returnsProductFound.get().getQuantity();
		Product previousProduct = returnsProductFound.get().getProduct();
		Godown previousGodown = returnsProductFound.get().getGodown();
		
		ReturnsProduct saved = returnsProductRepository.save(returnsProduct);
		
//		System.out.println(returnsProduct.getQuantity() + " " + previousQuantity);
//		System.out.println(returnsProduct.getProduct().getId() + " " + previousProduct.getId());
//		System.out.println(returnsProduct.getGodown().getId() + " " + previousGodown.getId());
//		if (returnsProduct.getQuantity() != previousQuantity || returnsProduct.getProduct().getId() != previousProduct.getId() || returnsProduct.getGodown().getId() != previousGodown.getId()) {
//			ProductsStockCompositeKey productsStockCompositeKey = new ProductsStockCompositeKey(previousProduct.getId(), previousGodown.getId());
//			Optional<ProductsStock> productsStockFound = productsStockRepository.findById(productsStockCompositeKey);
//			if (productsStockFound.isPresent()) {
//				productsStockFound.get().setStock(productsStockFound.get().getStock() - previousQuantity);
//				productsStockRepository.save(productsStockFound.get());
//			}
//			
//			productsStockCompositeKey = new ProductsStockCompositeKey(returnsProduct.getProduct().getId(), returnsProduct.getGodown().getId());
//			productsStockFound = productsStockRepository.findById(productsStockCompositeKey);
//			if (productsStockFound.isPresent()) {
//				productsStockFound.get().setStock(productsStockFound.get().getStock() + returnsProduct.getQuantity());
//				productsStockRepository.save(productsStockFound.get());
//			}
//			else {
//				ProductsStock productsStock = new ProductsStock();
//				productsStock.setCompositeKey(productsStockCompositeKey);
//				productsStock.setProduct(returnsProduct.getProduct());
//				productsStock.setGodown(returnsProduct.getGodown());
//				productsStock.setStock(returnsProduct.getQuantity());
//				productsStockRepository.save(productsStock);
//			}
//		}
		
		return new ResponseEntity<ReturnsProduct>(saved, HttpStatus.OK);
	}
	
	@PostMapping(path=PATH)
	public ResponseEntity addReturn(@RequestBody ReturnsProduct returnsProduct) {
		if(returnsProduct.getDeliveryDate() == LocalDate.MIN || returnsProduct.getReturnDate() == LocalDate.MIN) {
			return new ResponseEntity<String> ("Date should be given in the format dd/MM/yyyy. For example, 30th December 2000 should be given as 30/12/2000.",HttpStatus.BAD_REQUEST);
		}
		
		if (returnsProduct.getGodown() != null && returnsProduct.getGodown().getId() > 0) {
			Optional<Godown> godownFound = godownRepository.findById(returnsProduct.getGodown().getId());
			
			if (godownFound.isEmpty()) {
				return new ResponseEntity<String>("Godown with the given id not found.", HttpStatus.NOT_FOUND);
			}
			
			returnsProduct.setGodown(godownFound.get());
		}

		if (returnsProduct.getInvoiceIssued() != null) {
			if (returnsProduct.getInvoiceIssued().getBillCheckedBy() != null && returnsProduct.getInvoiceIssued().getBillCheckedBy().getId() > 0) {
				Optional<Employee> employeeFound = employeeRepository.findById(returnsProduct.getInvoiceIssued().getBillCheckedBy().getId());
				
				if (employeeFound.isEmpty()) {
					return new ResponseEntity<String>("Employee with the given id not found.", HttpStatus.NOT_FOUND);
				}
				
				returnsProduct.getInvoiceIssued().setBillCheckedBy(employeeFound.get());
			}
			
			returnsProduct.getInvoiceIssued().setQuantity(returnsProduct.getQuantity());
		}
		
		if (returnsProduct.getProduct() != null && returnsProduct.getProduct().getId() > 0) {
			Optional<Product> productFound = productRepository.findById(returnsProduct.getProduct().getId());
			
			if (productFound.isEmpty()) {
				return new ResponseEntity<String>("Product with the given id not found.", HttpStatus.NOT_FOUND);
			}
			
			returnsProduct.setProduct(productFound.get());
			
			if (returnsProduct.getInvoiceIssued() != null) {
				returnsProduct.getInvoiceIssued().setProduct(productFound.get());
				if (returnsProduct.getQuantity() != null) {
					returnsProduct.getInvoiceIssued().setBillValue(productFound.get().getPrice() * returnsProduct.getQuantity());
				}
			}
		}
		
		ReturnsProduct saved = returnsProductRepository.save(returnsProduct);
		
//		ProductsStockCompositeKey productsStockCompositeKey = new ProductsStockCompositeKey(returnsProduct.getProduct().getId(), returnsProduct.getGodown().getId());
//		Optional<ProductsStock> productsStockFound = productsStockRepository.findById(productsStockCompositeKey);
//		if (productsStockFound.isPresent()) {
//			productsStockFound.get().setStock(productsStockFound.get().getStock() + returnsProduct.getQuantity());
//			productsStockRepository.save(productsStockFound.get());
//		}
//		else {
//			ProductsStock productsStock = new ProductsStock();
//			productsStock.setCompositeKey(productsStockCompositeKey);
//			productsStock.setProduct(returnsProduct.getProduct());
//			productsStock.setGodown(returnsProduct.getGodown());
//			productsStock.setStock(returnsProduct.getQuantity());
//			productsStockRepository.save(productsStock);
//		}
		
		return new ResponseEntity<ReturnsProduct>(saved, HttpStatus.CREATED);
	}
	
	@DeleteMapping(path=PATH + "/{id}")
	public ResponseEntity<Void> deleteReturns(@PathVariable int id) {
		returnsProductRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
