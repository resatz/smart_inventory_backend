package com.incedo.smart_inventory.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Employees {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String username;
	
	@NotNull
	@Column(name="password_hash")
	private String passwordHash;
	
	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private EmployeeRoles rollId;

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

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	


	

	public EmployeeRoles getRollId() {
		return rollId;
	}

	public void setRollId(EmployeeRoles rollId) {
		this.rollId = rollId;
	}

	@Override
	public String toString() {
		return "Employees [id=" + id + ", name=" + name + ", username=" + username + ", passwordHash=" + passwordHash
				+ ", rollId=" + rollId + "]";
	}
	
	
	
}