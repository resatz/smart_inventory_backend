package com.incedo.smart_inventory.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id = null;
	
	@NotNull
	@Column(name="name")
	private String name;
	
	@NotNull
	@Column(name="stock")
	private Integer stock = null;
	
	@NotNull
	@Column(name="price")
	private Double price = null;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Products [id=" + id + ", name=" + name + ", stock=" + stock + ", price=" + price + "]";
	}
	
}
