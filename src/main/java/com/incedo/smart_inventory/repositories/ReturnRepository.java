package com.incedo.smart_inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.smart_inventory.entities.Returns;

public interface ReturnRepository extends JpaRepository<Returns, Integer> {
	

}
