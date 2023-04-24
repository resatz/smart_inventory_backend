package com.incedo.smart_inventory.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.incedo.smart_inventory.common.deserializers.LocalDateDeserializer;
import com.incedo.smart_inventory.common.serializers.LocalDateSerializer;

@Entity
public class InwardsProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id = null;
	
	@NotNull
	@Column(name = "supply_date")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate supplyDate;
	
	@NotNull
	@Column(name = "receipt_no")
	private Integer receiptNo = null;
	
	@NotNull
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Supplier receivedFrom;
	
	@NotNull
	@Column(name = "invoice_id")
	private Integer invoiceId = null;
	
	@NotNull
	@Column(name = "godown_id")
	private Integer godownId = null;
	
	@NotNull
	@Column(name = "product_id")
	private Integer productId = null;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDate getSupplyDate() {
		return supplyDate;
	}
	public void setSupplyDate(LocalDate supplyDate) {
		this.supplyDate = supplyDate;
	}
	public Integer getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(Integer receiptNo) {
		this.receiptNo = receiptNo;
	}
	public Supplier getReceivedFrom() {
		return receivedFrom;
	}
	public void setReceivedFrom(Supplier receivedFrom) {
		this.receivedFrom = receivedFrom;
	}
	public Integer getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Integer getGodownId() {
		return godownId;
	}
	public void setGodownId(Integer godownId) {
		this.godownId = godownId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "InwardsProduct [id=" + id + ", supplyDate=" + supplyDate + ", receiptNo=" + receiptNo
				+ ", receivedFrom=" + receivedFrom + ", invoiceId=" + invoiceId + ", godownId=" + godownId
				+ ", productId=" + productId + "]";
	}	
	
}
