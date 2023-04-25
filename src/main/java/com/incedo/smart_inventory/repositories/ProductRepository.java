package com.incedo.smart_inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.smart_inventory.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
