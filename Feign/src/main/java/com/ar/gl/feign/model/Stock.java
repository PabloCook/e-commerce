package com.ar.gl.feign.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

	private Long id;

	private Integer quantity;

	private String locationCode;

	private Boolean enabled;

}
