package com.incedo.smart_inventory.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	private String name = null;
	
	@NotNull
	@Column(unique = true)
	private String username = null;
	
	@NotNull
	@Column(name="password_hash")
	private String password = null;
	
	@NotNull
	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private EmployeeRoles role = null;
	
	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Godown godown = null;
	
	@Column(name = "isLocked", columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean isLocked = null;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
	
	public EmployeeRoles getRole() {
		return role;
	}

	public void setRole(EmployeeRoles role) {
		this.role = role;
	}
	
	public Godown getGodown() {
		return godown;
	}

	public void setGodown(Godown godown) {
		this.godown = godown;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", role="
				+ role + ", isLocked=" + isLocked + "]";
	}
	
}