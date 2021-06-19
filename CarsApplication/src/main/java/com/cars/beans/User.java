package com.cars.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


@Entity /* It is a class and mapped in the database */
@Table(name="UserForm")
public class User {
	@Id	/*primary key in the database */
	@GeneratedValue(strategy = GenerationType.IDENTITY) /* Automatic Id generation */
	private long userId;
	@NotBlank
	private String password;
	@NotBlank
	private String role;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", role=" + role + "]";
	}
	
}
