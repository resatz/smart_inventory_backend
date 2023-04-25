package com.incedo.smart_inventory.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Invoice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id=null;
	
	@NotNull
	@Column(name="quantity")
	private Integer quantity=null;
	
	@NotNull
	@Column(name="bill_value")
	private Double billValue= null;
	
	@NotNull
	@Column(name="bill_checked_by_id")
	private Integer billCheckedById;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getBillValue() {
		return billValue;
	}
	public void setBillValue(Double billValue) {
		this.billValue = billValue;
	}
	public Integer getBillCheckedById() {
		return billCheckedById;
	}
	public void setBillCheckedById(Integer billCheckedById) {
		this.billCheckedById = billCheckedById;
	}
	
	@Override
	public String toString() {
		return "Invoice [id=" + id + ", quantity=" + quantity + ", billValue=" + billValue + ", billCheckedById="
				+ billCheckedById + "]";
	}
	
}
