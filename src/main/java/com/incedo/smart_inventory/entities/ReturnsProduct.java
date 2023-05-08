package com.incedo.smart_inventory.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.incedo.smart_inventory.common.deserializers.LocalDateDeserializer;
import com.incedo.smart_inventory.common.serializers.LocalDateSerializer;

@Entity(name = "returns_register")
public class ReturnsProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@Column(name="receipt_no", unique = true)
	private Integer receiptNo = null;
	
	@NotNull
	@Column(name="returned_by")
	private String returnedBy = null;
	
	@NotNull
	@JsonProperty(value = "invoice")
	@JsonIgnoreProperties(value = { "product", "quantity" })
	@JoinColumn(name = "invoice_id", unique = true)
	@OneToOne(cascade = CascadeType.ALL)
	private InvoiceIssued invoiceIssued = null;
	
	@NotNull
	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private Godown godown = null;

	@NotNull
	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
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

	public Godown getGodown() {
		return godown;
	}
	
	public void setGodown(Godown godown) {
		this.godown = godown;
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
		return "ReturnsProduct [id=" + id + ", reason=" + reason + ", deliveryDate=" + deliveryDate + ", returnDate="
				+ returnDate + ", receiptNo=" + receiptNo + ", returnedBy=" + returnedBy + ", invoiceIssued="
				+ invoiceIssued + ", godown=" + godown + ", product=" + product + ", quantity=" + quantity + "]";
	}
	
}