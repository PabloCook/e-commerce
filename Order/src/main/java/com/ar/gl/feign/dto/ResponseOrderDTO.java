package com.ar.gl.feign.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(value= {"customerId", "productId"})
public class ResponseOrderDTO {
	
	private Long id;
	private Integer quantity;
	private Double totalPrice;
	private Boolean disable;
	
	private Long productId;
	private String productName;
	private String productDescription;
	private Double productPrice;
	private LocalDate productDate;
	
	private String categoryName;
	private String categoryDescription;
	
	private Long customerId;
	private String customerName;
	private String customerSurname;
	private String customerDni;
	
	private String message;
	
	
	public ResponseOrderDTO(String message) {
		this.message = message;
	}

}
