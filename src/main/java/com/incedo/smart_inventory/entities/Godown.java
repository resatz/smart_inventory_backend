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
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.incedo.smart_inventory.common.deserializers.LocalDateDeserializer;
import com.incedo.smart_inventory.common.serializers.LocalDateSerializer;

@Entity
public class Godown {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Column(name="location")
	private String location = null;
	
	@NotNull
	@Column(name="capacity_in_quintals")
	private Double capacityInQuintals = null;
	
	@NotNull
	@JsonIgnoreProperties(value = { "godown" }, allowGetters = false, allowSetters = true)
	@JoinColumn(unique = true)
	@OneToOne(cascade = CascadeType.ALL)
	private Employee manager = null;
	
	@NotNull
	@Column(name="start_date")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate startDate = null;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Double getCapacityInQuintals() {
		return capacityInQuintals;
	}
	public void setCapacityInQuintals(Double capacityInQuintals) {
		this.capacityInQuintals = capacityInQuintals;
	}
	public Employee getManager() {
		return manager;
	}
	public void setManager(Employee manager) {
		this.manager = manager;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	@Override
	public String toString() {
		return "Godown [id=" + id + ", location=" + location + ", capacityInQuintals=" + capacityInQuintals
				+ ", manager=" + manager + ", startDate=" + startDate + "]";
	}
}

