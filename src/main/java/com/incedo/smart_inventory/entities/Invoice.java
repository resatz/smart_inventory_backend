package com.incedo.smart_inventory.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Invoice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id = null;
	
	@NotNull
	@Column(name="quantity")
	private Integer quantity = null;
	
	@NotNull
	@Column(name="bill_value")
	private Double billValue = null;
	
	@NotNull
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
	private Employee billCheckedBy = null;
	
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
	public Employee getBillCheckedBy() {
		return billCheckedBy;
	}
	public void setBillCheckedBy(Employee billCheckedBy) {
		this.billCheckedBy = billCheckedBy;
	}
	
	@Override
	public String toString() {
		return "Invoice [id=" + id + ", quantity=" + quantity + ", billValue=" + billValue + ", billCheckedBy=" + billCheckedBy
				+ "]";
	}
}
