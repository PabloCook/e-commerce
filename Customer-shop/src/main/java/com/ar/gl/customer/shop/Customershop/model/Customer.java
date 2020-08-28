package com.ar.gl.customer.shop.Customershop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String surname;
	
	private String dni;
	
	@Column(nullable = false)
	private Boolean enabled;

	public Customer() {
		this.enabled = true;
	}

	public Customer(Long id, String name, String surname, String dni) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dni = dni;
		this.enabled = true;
	}
	
	
	
}
