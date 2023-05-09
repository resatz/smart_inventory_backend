package com.incedo.smart_inventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.smart_inventory.entities.OutwardsProduct;

public interface OutwardsProductRepository extends JpaRepository<OutwardsProduct, Integer>{
	
	public List<OutwardsProduct> findAllByGodownId(int godownId);
	
}