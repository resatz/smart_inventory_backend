package com.incedo.smart_inventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.smart_inventory.entities.Product;
import com.incedo.smart_inventory.entities.ProductsStock;
import com.incedo.smart_inventory.entities.ProductsStockCompositeKey;

public interface ProductsStockRepository extends JpaRepository<ProductsStock, ProductsStockCompositeKey> {

	public List<ProductsStock> findProductsStockByGodownId(int godownId);
	
}
