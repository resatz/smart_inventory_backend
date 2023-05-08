package com.incedo.smart_inventory.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductsStockCompositeKey implements Serializable {
	
	private static final long serialVersionUID = 2221676589514725747L;

	@Column(name = "product_id")
	private int productId;
	
	@Column(name = "product_id")
	private int godownId;

	public ProductsStockCompositeKey() {
		super();
	}
	
	public ProductsStockCompositeKey(int productId, int godownId) {
		super();
		this.productId = productId;
		this.godownId = godownId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getGodownId() {
		return godownId;
	}

	public void setGodownId(int godownId) {
		this.godownId = godownId;
	}
	
}
