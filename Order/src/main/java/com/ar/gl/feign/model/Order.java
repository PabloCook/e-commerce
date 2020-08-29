package com.ar.gl.feign.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Order {

	private Long id;

	private Long productId;

	private Long customerId;

	private Integer quantity;

	private Double totalPrice;

	private Boolean disable;
}
