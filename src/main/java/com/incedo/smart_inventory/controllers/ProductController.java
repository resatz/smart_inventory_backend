package com.incedo.smart_inventory.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incedo.smart_inventory.entities.Product;
import com.incedo.smart_inventory.entities.ProductsStock;
import com.incedo.smart_inventory.repositories.ProductRepository;
import com.incedo.smart_inventory.repositories.ProductsStockRepository;

@RestController
@RequestMapping("/api")
public class ProductController {

	private static final String PATH = "/products";
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductsStockRepository productsStockRepository;
	
	@GetMapping(path=PATH)
	public ResponseEntity<List<Product>> product(@RequestParam(name = "godownId", required = false) Integer godownId) {
		if (godownId == null) {
			return new ResponseEntity<List<Product>>(productRepository.findAll(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Product>>(productsStockRepository.findProductsStockByGodownId(godownId)
					.stream()
					.filter(item -> item.getStock() != 0)
					.map(item -> item.getProduct())
					.collect(Collectors.toList()), HttpStatus.OK);
		}
	}
	
	@GetMapping(path=PATH + "/{id}")
	public ResponseEntity findById(@PathVariable int id) {
		Optional<Product> productFound = productRepository.findById(id);
		
		if(productFound.isPresent()) {
			return new ResponseEntity<Product>(productFound.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("The resource with the given id doesn't exist", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(path=PATH)
	public ResponseEntity<Product> createEntity(@RequestBody Product product) {
		Product saved = productRepository.save(product);
		return new ResponseEntity<Product>(saved, HttpStatus.CREATED);
	}
	
	@PutMapping(path=PATH + "/{id}")
	public ResponseEntity updateEntity(@PathVariable int id,@RequestBody Product product) {
		Optional<Product> productFound = productRepository.findById(id);
		
		if(productFound.isPresent()) {
			product.setId(id);
			Product saved = productRepository.save(product);
			return new ResponseEntity<Product>(saved, HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Record with the given id not found.", HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping(path=PATH + "/{id}")
	public ResponseEntity<Void> deleteEntity(@PathVariable int id) {
		productRepository.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
