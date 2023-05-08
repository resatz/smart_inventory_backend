package com.incedo.smart_inventory.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incedo.smart_inventory.entities.ProductsStock;
import com.incedo.smart_inventory.repositories.ProductsStockRepository;

@RestController
@RequestMapping("/api")
public class ProductsStockController {

	private static final String PATH = "/productsStock";
	
	@Autowired
	ProductsStockRepository productsStockRepository;
	
	@GetMapping(path=PATH + "/stock")
	public ResponseEntity<List<ProductsStock>> getProductsByGodownId(@RequestParam("godownId") int godownId) {
		return new ResponseEntity<List<ProductsStock>>(productsStockRepository.findProductsByGodownId(godownId)
				.stream()
				.filter(item -> item.getStock() != 0)
				.toList(), HttpStatus.OK);
	}
	
	@GetMapping(path=PATH + "/capacity")
	public ResponseEntity<Double> getCapacityByGodownId(@RequestParam("godownId") int godownId) {
		return new ResponseEntity<Double>(productsStockRepository.findProductsByGodownId(godownId)
				.stream()
				.mapToDouble(item -> item.getStock() * item.getProduct().getWeight())
				.sum(), HttpStatus.OK);
	}
	
}
