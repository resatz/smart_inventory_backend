package com.incedo.smart_inventory.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.incedo.smart_inventory.common.deserializers.LocalDateDeserializer;
import com.incedo.smart_inventory.common.serializers.LocalDateSerializer;

@Entity
public class Godown {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id= null;
	
	@NotNull
	@Column(name="location")
	private String location;
	
	@NotNull
	@Column(name="capacity_in_quintals")
	private Double capacityInQuintals= null;
	
	@NotNull
	@Column(name="manager_id")
	private Integer managerId= null;
	
	@NotNull
	@Column(name="start_date")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate startDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	@Override
	public String toString() {
		return "Godowns [id=" + id + ", location=" + location + ", capacityInQuintals=" + capacityInQuintals
				+ ", managerId=" + managerId + ", startDate=" + startDate + "]";
	}
	
}

