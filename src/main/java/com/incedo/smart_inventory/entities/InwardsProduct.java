package com.incedo.smart_inventory.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.incedo.smart_inventory.common.deserializers.LocalDateDeserializer;
import com.incedo.smart_inventory.common.serializers.LocalDateSerializer;

@Entity(name = "inwards_register")
public class InwardsProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	@Column(name = "supply_date")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate supplyDate = null;
	
	@NotNull
	@Column(name = "receipt_no")
	private Integer receiptNo = null;
	
	@NotNull
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Supplier supplier = null;
	
	@NotNull
	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private InvoiceReceived invoiceReceived = null;
	
	@NotNull
	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Godown godown = null;
	
	@NotNull
	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
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
	public Integer getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(Integer receiptNo) {
		this.receiptNo = receiptNo;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public InvoiceReceived getInvoiceReceived() {
		return invoiceReceived;
	}
	public void setInvoice(InvoiceReceived invoiceReceived) {
		this.invoiceReceived = invoiceReceived;
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
	public void setProducts(List<ProductsMap> products) {
		this.products = products;
	}
	
	@Override
	public String toString() {
		return "InwardsProduct [id=" + id + ", supplyDate=" + supplyDate + ", receiptNo=" + receiptNo + ", supplier="
				+ supplier + ", invoiceReceived=" + invoiceReceived + ", godown=" + godown + ", products=" + products
				+ "]";
	}

	
}
