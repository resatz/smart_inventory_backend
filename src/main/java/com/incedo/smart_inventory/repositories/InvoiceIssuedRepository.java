package com.incedo.smart_inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.smart_inventory.entities.InvoiceIssued;

public interface InvoiceIssuedRepository extends JpaRepository<InvoiceIssued, Integer> {

}
