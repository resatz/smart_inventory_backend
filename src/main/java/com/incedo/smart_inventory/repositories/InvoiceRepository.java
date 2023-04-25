package com.incedo.smart_inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.smart_inventory.entities.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	
}
