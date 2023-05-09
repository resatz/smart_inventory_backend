package com.incedo.smart_inventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.smart_inventory.entities.ReturnsProduct;

public interface ReturnsProductRepository extends JpaRepository<ReturnsProduct, Integer> {
	
	public List<ReturnsProduct> findAllByGodownId(int godownId);

}
