package com.incedo.smart_inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.smart_inventory.entities.ProductsMap;

public interface ProductsMapReposirory extends JpaRepository<ProductsMap, Integer> {

}
