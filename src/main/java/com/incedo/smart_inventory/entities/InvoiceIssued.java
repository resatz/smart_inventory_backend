package com.incedo.smart_inventory.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
@Entity
public class InvoiceIssued {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	@Column(name="invoice_no")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int invoiceNo;
	
	@NotNull
	@Column(name="bill_value")
	private Double billValue = null;
	
	@NotNull
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
	private Employee billCheckedBy = null;
	
	@NotNull
	@OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<ProductsMap> products = null;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(int invoiceNo) {
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
	public List<ProductsMap> getProducts() {
		return products;
	}
	public void setProductsMap(List<ProductsMap> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "InvoiceIssued [id=" + id + ", invoiceNo=" + invoiceNo + ", billValue=" + billValue + ", billCheckedBy="
				+ billCheckedBy + ", products=" + products + "]";
	}
	
	
}
