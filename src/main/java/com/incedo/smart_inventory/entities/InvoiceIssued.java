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
public class InvoiceIssued {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Column(name="invoice_no", unique = true, length = 12)
	private String invoiceNo = null;
	
	@NotNull
	@Column(name="bill_value")
	private Double billValue = null;
	
	@NotNull
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
	private Employee billCheckedBy = null;
	
	@NotNull
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private Product product = null;
	
	@NotNull
	@Column(name="quantity")
	private Integer quantity = null;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "InvoiceIssued [id=" + id + ", invoiceNo=" + invoiceNo + ", billValue=" + billValue + ", billCheckedBy="
				+ billCheckedBy + ", product=" + product + ", quantity=" + quantity + "]";
	}
	
}
