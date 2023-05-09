package com.incedo.smart_inventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.smart_inventory.entities.InwardsProduct;

public interface InwardsProductRepository extends JpaRepository<InwardsProduct, Integer> {
	
	public List<InwardsProduct> findAllByGodownId(int godownId);
	
}
