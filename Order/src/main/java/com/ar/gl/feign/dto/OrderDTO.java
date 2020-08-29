package com.ar.gl.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

	private Long id;
	private Long productId;
	private Long customerId;
	private Integer quantity;
	private Double totalPrice;
	private Boolean disable;
}
