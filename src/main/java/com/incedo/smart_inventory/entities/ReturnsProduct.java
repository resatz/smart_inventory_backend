package com.incedo.smart_inventory.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.incedo.smart_inventory.common.deserializers.LocalDateDeserializer;
import com.incedo.smart_inventory.common.serializers.LocalDateSerializer;

@Entity
public class ReturnsProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id = null;
	
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
	
	@Column(name="receipt_no")
	private Integer receiptNo = null;
	
	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private Invoice invoice = null;
	
	@Column(name="returned_by")
	private String returnedBy = null;
	
	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private Product product = null;
	
	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private Godown godown = null;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
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
	
	public Invoice getInvoice() {
		return invoice;
	}
	
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	public String getReturnedBy() {
		return returnedBy;
	}
	
	public void setReturnedBy(String returnedBy) {
		this.returnedBy = returnedBy;
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
	
	@Override
	public String toString() {
		return "Returns [id=" + id + ", reason=" + reason + ", deliveryDate=" + deliveryDate + ", returnDate=" + returnDate
				+ ", receiptNo=" + receiptNo + ", invoice=" + invoice + ", returnedBy=" + returnedBy + ", product="
				+ product + ", godown=" + godown + "]";
	}

}