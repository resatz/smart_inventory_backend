package com.incedo.smart_inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.smart_inventory.entities.InwardsProduct;

public interface InwardsProductRepository extends JpaRepository<InwardsProduct, Integer> {

}
