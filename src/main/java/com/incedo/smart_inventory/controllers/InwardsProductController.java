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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incedo.smart_inventory.entities.Employee;
import com.incedo.smart_inventory.entities.Godown;
import com.incedo.smart_inventory.entities.InvoiceReceived;
import com.incedo.smart_inventory.entities.InwardsProduct;
import com.incedo.smart_inventory.entities.Product;
import com.incedo.smart_inventory.entities.ProductsStock;
import com.incedo.smart_inventory.entities.ProductsStockCompositeKey;
import com.incedo.smart_inventory.entities.Supplier;
import com.incedo.smart_inventory.repositories.EmployeeRepository;
import com.incedo.smart_inventory.repositories.GodownRepository;
import com.incedo.smart_inventory.repositories.InvoiceReceivedRepository;
import com.incedo.smart_inventory.repositories.InwardsProductRepository;
import com.incedo.smart_inventory.repositories.ProductRepository;
import com.incedo.smart_inventory.repositories.ProductsStockRepository;
import com.incedo.smart_inventory.repositories.SupplierRepository;

@RestController
@RequestMapping("/api")
public class InwardsProductController {
	
	private static final String PATH = "/inwards";
	
	@Autowired
	InwardsProductRepository inwardsProductRepository;
	
	@Autowired
	SupplierRepository supplierRepository;
	
	@Autowired
	InvoiceReceivedRepository invoiceReceivedRepository;
	
	@Autowired
	GodownRepository godownRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	ProductsStockRepository productsStockRepository;
	
	@GetMapping(path=PATH)
	public ResponseEntity<List<InwardsProduct>> getProducts(@RequestParam(name = "godownId", required = false) Integer godownId) {
		if (godownId == null) {
			return new ResponseEntity<List<InwardsProduct>>(inwardsProductRepository.findAll(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<InwardsProduct>>(inwardsProductRepository.findAllByGodownId(godownId), HttpStatus.OK);
		}
	}
	
	@GetMapping(path=PATH + "/{id}")
	public ResponseEntity getProductById(@PathVariable int id) {
		Optional<InwardsProduct> inwardsProductFound = inwardsProductRepository.findById(id);
		
		if (inwardsProductFound.isEmpty()) {
			return new ResponseEntity<String>("Record with the given id not found", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<InwardsProduct>(inwardsProductFound.get(), HttpStatus.OK);
	}
	
	@PostMapping(path=PATH)
	public ResponseEntity addProduct(@RequestBody InwardsProduct inwardsProduct) {
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
		
		if (inwardsProduct.getInvoiceReceived() != null) {
			if (inwardsProduct.getInvoiceReceived().getBillCheckedBy() != null && inwardsProduct.getInvoiceReceived().getBillCheckedBy().getId() > 0) {
				Optional<Employee> employeeFound = employeeRepository.findById(inwardsProduct.getInvoiceReceived().getBillCheckedBy().getId());
				
				if (employeeFound.isEmpty()) {
					return new ResponseEntity<String>("Employee with the given id not found.", HttpStatus.NOT_FOUND);
				}
				
				inwardsProduct.getInvoiceReceived().setBillCheckedBy(employeeFound.get());
			}
			
			inwardsProduct.getInvoiceReceived().setQuantity(inwardsProduct.getQuantity());
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
			
			if (inwardsProduct.getInvoiceReceived() != null) {
				inwardsProduct.getInvoiceReceived().setProduct(productFound.get());
				if (inwardsProduct.getQuantity() != null) {
					inwardsProduct.getInvoiceReceived().setBillValue(productFound.get().getPrice() * inwardsProduct.getQuantity());
				}
			}
		}
		
		InwardsProduct saved = inwardsProductRepository.save(inwardsProduct);
		
		ProductsStockCompositeKey productsStockCompositeKey = new ProductsStockCompositeKey(inwardsProduct.getProduct().getId(), inwardsProduct.getGodown().getId());
		Optional<ProductsStock> productsStockFound = productsStockRepository.findById(productsStockCompositeKey);
		if (productsStockFound.isPresent()) {
			productsStockFound.get().setStock(productsStockFound.get().getStock() + inwardsProduct.getQuantity());
			productsStockRepository.save(productsStockFound.get());
		}
		else {
			ProductsStock productsStock = new ProductsStock();
			productsStock.setCompositeKey(productsStockCompositeKey);
			productsStock.setProduct(inwardsProduct.getProduct());
			productsStock.setGodown(inwardsProduct.getGodown());
			productsStock.setStock(inwardsProduct.getQuantity());
			productsStockRepository.save(productsStock);
		}
		
		return new ResponseEntity<InwardsProduct>(saved, HttpStatus.CREATED);
	}
	
	@PutMapping(path=PATH + "/{id}")
	public ResponseEntity editProduct(@RequestBody InwardsProduct inwardsProduct, @PathVariable int id) {
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
		
		if (inwardsProduct.getInvoiceReceived() != null && inwardsProduct.getInvoiceReceived().getId() > 0) {
			Optional<InvoiceReceived> invoiceFound = invoiceReceivedRepository.findById(inwardsProduct.getInvoiceReceived().getId());
			
			if (invoiceFound.isEmpty()) {
				return new ResponseEntity<String>("Invoice with the given id not found.", HttpStatus.NOT_FOUND);
			}
			
			inwardsProduct.setInvoiceReceived(invoiceFound.get());
		}
		else if (inwardsProduct.getInvoiceReceived() != null) {
			if(inwardsProduct.getInvoiceReceived().getBillCheckedBy() != null && inwardsProduct.getInvoiceReceived().getBillCheckedBy().getId() > 0) {
				Optional<Employee> employeeFound = employeeRepository.findById(inwardsProduct.getInvoiceReceived().getBillCheckedBy().getId());
				
				if (employeeFound.isEmpty()) {
					return new ResponseEntity<String>("Employee with the given id not found.", HttpStatus.NOT_FOUND);
				}
				
				inwardsProduct.getInvoiceReceived().setBillCheckedBy(employeeFound.get());
			}
			
			inwardsProduct.getInvoiceReceived().setQuantity(inwardsProduct.getQuantity());
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
			
			if (inwardsProduct.getInvoiceReceived() != null) {
				inwardsProduct.getInvoiceReceived().setProduct(productFound.get());
				if (inwardsProduct.getQuantity() != null) {
					inwardsProduct.getInvoiceReceived().setBillValue(productFound.get().getPrice() * inwardsProduct.getQuantity());
				}
			}
		}
		
		Integer previousQuantity = inwardsProductFound.get().getQuantity();
		Product previousProduct = inwardsProductFound.get().getProduct();
		Godown previousGodown = inwardsProductFound.get().getGodown();
		
		InwardsProduct saved = inwardsProductRepository.save(inwardsProduct);
		
		if (inwardsProduct.getQuantity() != previousQuantity || inwardsProduct.getProduct().getId() != previousProduct.getId() || inwardsProduct.getGodown().getId() != previousGodown.getId()) {
			ProductsStockCompositeKey productsStockCompositeKey = new ProductsStockCompositeKey(previousProduct.getId(), previousGodown.getId());
			Optional<ProductsStock> productsStockFound = productsStockRepository.findById(productsStockCompositeKey);
			if (productsStockFound.isPresent()) {
				productsStockFound.get().setStock(productsStockFound.get().getStock() - previousQuantity);
				productsStockRepository.save(productsStockFound.get());
			}
			
			productsStockCompositeKey = new ProductsStockCompositeKey(inwardsProduct.getProduct().getId(), inwardsProduct.getGodown().getId());
			productsStockFound = productsStockRepository.findById(productsStockCompositeKey);
			if (productsStockFound.isPresent()) {
				productsStockFound.get().setStock(productsStockFound.get().getStock() + inwardsProduct.getQuantity());
				productsStockRepository.save(productsStockFound.get());
			}
			else {
				ProductsStock productsStock = new ProductsStock();
				productsStock.setCompositeKey(productsStockCompositeKey);
				productsStock.setProduct(inwardsProduct.getProduct());
				productsStock.setGodown(inwardsProduct.getGodown());
				productsStock.setStock(inwardsProduct.getQuantity());
				productsStockRepository.save(productsStock);
			}
		}
		
		return new ResponseEntity<InwardsProduct>(saved, HttpStatus.OK);
	}
	
	@DeleteMapping(path=PATH + "/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id) {
		Optional<InwardsProduct> inwardsProductFound = inwardsProductRepository.findById(id);
		
		if (inwardsProductFound.isEmpty()) {
			return new ResponseEntity<String>("Record with the given id not found.", HttpStatus.NOT_FOUND);
		}
		
		inwardsProductRepository.deleteById(id);
		
		ProductsStockCompositeKey productsStockCompositeKey = new ProductsStockCompositeKey(inwardsProductFound.get().getProduct().getId(), inwardsProductFound.get().getGodown().getId());
		Optional<ProductsStock> productsStockFound = productsStockRepository.findById(productsStockCompositeKey);
		if (productsStockFound.isPresent()) {
			productsStockFound.get().setStock(productsStockFound.get().getStock() - inwardsProductFound.get().getQuantity());
			productsStockRepository.save(productsStockFound.get());
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
