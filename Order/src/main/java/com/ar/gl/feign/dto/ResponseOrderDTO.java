package com.ar.gl.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOrderDTO {
	
	private OrderDTO orderDTO;
	private ProductDTO productDTO;
	private CustomerDTO customerDTO;

}
