package com.incedo.smart_inventory.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.incedo.smart_inventory.common.deserializers.LocalDateDeserializer;
import com.incedo.smart_inventory.common.serializers.LocalDateSerializer;

import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "outwards_register")
public class OutwardsProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	@Column(name = "supply_date")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate supplyDate = null;
	
	@NotNull
	@Column(name="delivery_date")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate deliveryDate = null;
	
	@NotNull
	@Column(name="purpose")
	private String purpose = null;
	
	@NotNull
	@Column(name="receipt_no")
	private Integer receiptNo = null;
	
	@NotNull
	@Column(name="delivered_to")
	private String deliveredTo = null;
	
	@NotNull
	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private InvoiceIssued invoiceIssued = null;
	
	@NotNull
	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private Godown godown = null;
	
	@NotNull
	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<ProductsMap> products = null;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getSupplyDate() {
		return supplyDate;
	}
	public void setSupplyDate(LocalDate supplyDate) {
		this.supplyDate = supplyDate;
	}
	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public Integer getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(Integer receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getDeliveredTo() {
		return deliveredTo;
	}
	public void setDeliveredTo(String deliveredTo) {
		this.deliveredTo = deliveredTo;
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
	
	public List<ProductsMap> getProducts() {
		return products;
	}
	public void setProductsMap(List<ProductsMap> products) {
		this.products = products;
	}
	
	@Override
	public String toString() {
		return "OutwardsProduct [id=" + id + ", supplyDate=" + supplyDate + ", deliveryDate=" + deliveryDate
				+ ", purpose=" + purpose + ", receiptNo=" + receiptNo + ", deliveredTo=" + deliveredTo
				+ ", invoiceIssued=" + invoiceIssued + ", godown=" + godown + ", products=" + products + "]";
	}
}
