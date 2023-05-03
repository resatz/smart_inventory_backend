package com.incedo.smart_inventory.entities;

import java.time.LocalDate;
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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.incedo.smart_inventory.common.deserializers.LocalDateDeserializer;
import com.incedo.smart_inventory.common.serializers.LocalDateSerializer;

@Entity(name = "returns_register")
public class ReturnsProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	@Column(name="reason")
	private String reason = null;
	
	@NotNull
	@Column(name="delivery_date")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate deliveryDate = null;
	
	@NotNull
	@Column(name="return_date")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate returnDate = null;
	
	@NotNull
	@Column(name="receipt_no")
	private Integer receiptNo = null;
	
	@NotNull
	@Column(name="returned_by")
	private String returnedBy = null;
	
	@NotNull
	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private InvoiceIssued invoiceIssued = null;
	
	@NotNull
	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private List<ProductsMap> products = null;
	
	@NotNull
	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private Godown godown = null;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}
	
	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	public LocalDate getReturnDate() {
		return returnDate;
	}
	
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	
	public Integer getReceiptNo() {
		return receiptNo;
	}
	
	public void setReceiptNo(Integer receiptNo) {
		this.receiptNo = receiptNo;
	}
	
	public String getReturnedBy() {
		return returnedBy;
	}
	
	public void setReturnedBy(String returnedBy) {
		this.returnedBy = returnedBy;
	}
	
	public InvoiceIssued getInvoiceIssued() {
		return invoiceIssued;
	}

	public void setInvoiceIssued(InvoiceIssued invoiceIssued) {
		this.invoiceIssued = invoiceIssued;
	}

	public List<ProductsMap> getProducts() {
		return products;
	}

	public void setProducts(List<ProductsMap> products) {
		this.products = products;
	}

	public Godown getGodown() {
		return godown;
	}
	
	public void setGodown(Godown godown) {
		this.godown = godown;
	}

	@Override
	public String toString() {
		return "ReturnsProduct [id=" + id + ", reason=" + reason + ", deliveryDate=" + deliveryDate + ", returnDate="
				+ returnDate + ", receiptNo=" + receiptNo + ", returnedBy=" + returnedBy + ", invoiceIssued="
				+ invoiceIssued + ", products=" + products + ", godown=" + godown + "]";
	}
	
}