package com.incedo.smart_inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.smart_inventory.entities.ReturnsProduct;

public interface ReturnsProductRepository extends JpaRepository<ReturnsProduct, Integer> {
	

}
