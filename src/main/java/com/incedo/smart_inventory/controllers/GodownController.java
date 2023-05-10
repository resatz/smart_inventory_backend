package com.incedo.smart_inventory.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;
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
import com.incedo.smart_inventory.entities.EmployeeRoles;
import com.incedo.smart_inventory.entities.Godown;
import com.incedo.smart_inventory.entities.ProductsStock;
import com.incedo.smart_inventory.repositories.EmployeeRepository;
import com.incedo.smart_inventory.repositories.EmployeeRolesRepository;
import com.incedo.smart_inventory.repositories.GodownRepository;
import com.incedo.smart_inventory.repositories.ProductsStockRepository;

@RestController
@RequestMapping("/api")
public class GodownController {
	
	private static final String PATH = "/godowns";
	
	@Autowired
	GodownRepository godownRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	EmployeeRolesRepository employeeRolesRepository;
	
	@Autowired
	ProductsStockRepository productsStockRepository;
	
	@GetMapping(path=PATH)
	public ResponseEntity<List<Godown>> godown() {
		return new ResponseEntity<List<Godown>>(godownRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(path=PATH + "/{id}")
	public ResponseEntity findById(@PathVariable int id) {
		Optional<Godown> godownFound = godownRepository.findById(id);
		
		if(godownFound.isEmpty()) {
			return new ResponseEntity<String>("The given id is not found", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Godown>(godownFound.get(), HttpStatus.OK);
	}
	
	@GetMapping(path=PATH + "/{id}/stock")
	public ResponseEntity<List<ProductsStock>> getProductsStock(@PathVariable int id) {
		return new ResponseEntity<List<ProductsStock>>(productsStockRepository.findProductsStockByGodownId(id)
				.stream()
				.filter(item -> item.getStock() != 0)
				.collect(Collectors.toList()), HttpStatus.OK);
	}
	
	@GetMapping(path=PATH + "/{id}/currentCapacity")
	public ResponseEntity<Double> getCapacity(@PathVariable int id) {
		return new ResponseEntity<Double>(productsStockRepository.findProductsStockByGodownId(id)
				.stream()
				.mapToDouble(item -> item.getStock() * item.getProduct().getWeight())
				.sum(), HttpStatus.OK);
	}

	@PostMapping(path=PATH)
	public ResponseEntity addGodown(@RequestBody Godown godown) {
		
		if (godown.getStartDate() == LocalDate.MIN) {
			return new ResponseEntity<String>("Date should be given in the format dd/MM/yyyy. For example, 30th December 2000 should be given as 30/12/2000.", HttpStatus.BAD_REQUEST);
		}
		
		if (godown.getManager() != null && godown.getManager().getId() > 0) {
			Optional<Employee> employeeFound= employeeRepository.findById(godown.getManager().getId());
			
			if (employeeFound.isEmpty()) {
				return new ResponseEntity<String>("Employee with the given id is not found",HttpStatus.NOT_FOUND);
			}
			
			godown.setManager(employeeFound.get());
		}
		else if (godown.getManager() != null) {
			if (godown.getManager().getRole() != null && godown.getManager().getRole().getId() > 0) {
				Optional<EmployeeRoles> employeeRoleFound = employeeRolesRepository.findById(godown.getManager().getRole().getId());
				
				if (employeeRoleFound.isEmpty()) {
					return new ResponseEntity<String>("Employee role with the given id is not found", HttpStatus.NOT_FOUND);
				}
				
				godown.getManager().setRole(employeeRoleFound.get());
				godown.getManager().setGodown(godown);
			}
			
			godown.getManager().setPassword(BCrypt.hashpw(godown.getManager().getPassword(), BCrypt.gensalt()));
		}

		godown.getManager().setGodown(null);
		Godown saved = godownRepository.save(godown);
		saved.getManager().setGodown(saved);
		saved = godownRepository.save(saved);
		return new ResponseEntity<Godown>(saved, HttpStatus.CREATED);
	}
	
	@PutMapping(path=PATH + "/{id}")
	public ResponseEntity updateEntity(@PathVariable int id,@RequestBody Godown godown) {
		Optional<Godown> godownFound = godownRepository.findById(id);
		
		if (godownFound.isEmpty()) {
			return new ResponseEntity<String>("The godown with the given id is not found",HttpStatus.NOT_FOUND);
		}
		
		if (godown.getStartDate() == LocalDate.MIN) {
			return new ResponseEntity<String>("Date should be given in the format dd/MM/yyyy. For example, 30th December 2000 should be given as 30/12/2000.", HttpStatus.BAD_REQUEST);
		}
		
		godown.setId(id);
		
		if (godown.getManager() != null && godown.getManager().getId() > 0) {
			Optional<Employee> employeeFound = employeeRepository.findById(godown.getManager().getId());
			
			if(employeeFound.isEmpty()) {
				return new ResponseEntity<String>("The employee with the given id is not found",HttpStatus.NOT_FOUND);
			}
			
			godown.setManager(employeeFound.get());
		}
		else if (godown.getManager() != null) {
			if (godown.getManager().getRole() != null && godown.getManager().getRole().getId() > 0) {
				Optional<EmployeeRoles> employeeRoleFound = employeeRolesRepository.findById(godown.getManager().getRole().getId());
				
				if (employeeRoleFound.isEmpty()) {
					return new ResponseEntity<String>("Employee role with the given id is not found", HttpStatus.NOT_FOUND);
				}
				
				godown.getManager().setRole(employeeRoleFound.get());
			}
			
			godown.getManager().setPassword(BCrypt.hashpw(godown.getManager().getPassword(), BCrypt.gensalt()));
		}
		
		godown.getManager().setGodown(null);
		Godown saved = godownRepository.save(godown);
		saved.getManager().setGodown(saved);
		saved = godownRepository.save(saved);
		return new ResponseEntity<Godown>(saved, HttpStatus.OK);
	}
	
	@DeleteMapping(path=PATH + "/{id}")
	public ResponseEntity<Void> deleteEntity(@PathVariable int id) {
		godownRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
