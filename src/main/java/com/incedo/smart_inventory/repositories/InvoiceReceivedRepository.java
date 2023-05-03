package com.incedo.smart_inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.smart_inventory.entities.InvoiceReceived;

public interface InvoiceReceivedRepository extends JpaRepository<InvoiceReceived, Integer> {
	
}
