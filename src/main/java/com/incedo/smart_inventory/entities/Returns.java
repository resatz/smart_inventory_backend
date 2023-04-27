
package com.incedo.smart_inventory.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.incedo.smart_inventory.common.deserializers.LocalDateDeserializer;
import com.incedo.smart_inventory.common.serializers.LocalDateSerializer;

@Entity
public class Returns {

@Id
private int id;

@NotNull
@Column(name="reason")
private String reason;


@NotNull
@Column(name="delivery_date")
@JsonSerialize(using = LocalDateSerializer.class)
@JsonDeserialize(using = LocalDateDeserializer.class)
private LocalDate deliveryDate;


@NotNull
@Column(name="return_date")
@JsonSerialize(using = LocalDateSerializer.class)
@JsonDeserialize(using = LocalDateDeserializer.class)
private LocalDate returnDate;


@Column(name="receipt_no")
private int receiptNo;

@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
private Invoice invoiceId;



@Column(name="returned_by")
private float returnedBy;

@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
private Product productId;

@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
private Godown godownId;

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

public int getReceiptNo() {
	return receiptNo;
}

public void setReceiptNo(int receiptNo) {
	this.receiptNo = receiptNo;
}

public Invoice getInvoiceId() {
	return invoiceId;
}

public void setInvoiceId(Invoice invoiceId) {
	this.invoiceId = invoiceId;
}

public float getReturnedBy() {
	return returnedBy;
}

public void setReturnedBy(float returnedBy) {
	this.returnedBy = returnedBy;
}

public Product getProductId() {
	return productId;
}

public void setProductId(Product productId) {
	this.productId = productId;
}

public Godown getGodownId() {
	return godownId;
}

public void setGodownId(Godown godownId) {
	this.godownId = godownId;
}

@Override
public String toString() {
	return "Returns [id=" + id + ", reason=" + reason + ", deliveryDate=" + deliveryDate + ", returnDate=" + returnDate
			+ ", receiptNo=" + receiptNo + ", invoiceId=" + invoiceId + ", returnedBy=" + returnedBy + ", productId="
			+ productId + ", godownId=" + godownId + "]";
}


}