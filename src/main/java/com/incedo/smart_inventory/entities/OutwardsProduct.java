package com.incedo.smart_inventory.entities;

import java.time.LocalDate;

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

@Entity
public class OutwardsProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id=null;
	
	@NotNull
	@Column(name = "supply_date")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate supplyDate;
	
	@NotNull
	@Column(name="delivery_date")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate deliveryDate;
	
	@NotNull
	@Column(name="purpose")
	private String purpose;
	
	@NotNull
	@Column(name="receipt_no")
	private Integer receiptNo=null;
	
	@NotNull
	@Column(name="delivered_to_id")
	private Integer deliveredToId=null;
	
	@NotNull
	@Column(name="invoice_id")
	private Integer invoiceId=null;
	
	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private Godown godown;
	
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
	public Integer getDeliveredToId() {
		return deliveredToId;
	}
	public void setDeliveredToId(Integer deliveredToId) {
		this.deliveredToId = deliveredToId;
	}
	public Integer getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Godown getGodown() {
		return godown;
	}
	public void setGodown(Godown godown) {
		this.godown = godown;
	}
	
	@Override
	public String toString() {
		return "OutwardsRegister [id=" + id + ", supplyDate=" + supplyDate + ", deliveryDate=" + deliveryDate
				+ ", purpose=" + purpose + ", receiptNo=" + receiptNo + ", deliveredToId=" + deliveredToId
				+ ", invoiceId=" + invoiceId + ", godown=" + godown + "]";
	}
	
}
