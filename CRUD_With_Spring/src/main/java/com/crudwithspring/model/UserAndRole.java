package com.crudwithspring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

@Entity(name = "USER_AND_ROLE")
public class UserAndRole {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	@Column(name = "USERNAME")
	@NotEmpty(message = "*Please provide an username")
	private String j_username;
	@Column(name = "PASSWORD")
	@NotEmpty(message = "*Please provide your password")
	private String j_password;
	@Column(name = "ROLE")
	@NotEmpty(message = "*Please provide your role")
	private String role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return j_password;
	}

	public void setPassword(String password) {
		this.j_password = password;
	}

	public String getUsername() {
		return j_username;
	}

	public void setUsername(String username) {
		this.j_username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
