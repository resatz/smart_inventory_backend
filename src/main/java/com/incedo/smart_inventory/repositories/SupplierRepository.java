package com.incedo.smart_inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.smart_inventory.entities.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

}
