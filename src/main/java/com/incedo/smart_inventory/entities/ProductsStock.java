package com.incedo.smart_inventory.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProductsStock {

	@EmbeddedId
	@JsonIgnore
	private ProductsStockCompositeKey compositeKey;
	
	@NotNull
	@MapsId("productId")
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private Product product = null;
	
	@NotNull
	@MapsId("godownId")
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private Godown godown = null;
	
	@NotNull
	@Min(0)
	@Column(name="stock")
	private Integer stock = null;

	public ProductsStockCompositeKey getCompositeKey() {
		return compositeKey;
	}
	public void setCompositeKey(ProductsStockCompositeKey compositeKey) {
		this.compositeKey = compositeKey;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Godown getGodown() {
		return godown;
	}
	public void setGodown(Godown godown) {
		this.godown = godown;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	@Override
	public String toString() {
		return "ProductsStock [compositeKey=" + compositeKey + ", product=" + product + ", godown=" + godown
				+ ", stock=" + stock + "]";
	}
	
}
