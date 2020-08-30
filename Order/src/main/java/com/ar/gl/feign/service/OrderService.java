package com.ar.gl.feign.service;

import org.springframework.http.ResponseEntity;

import com.ar.gl.feign.dto.OrderDTO;

public interface OrderService {
	
	public ResponseEntity<OrderDTO> create(OrderDTO orderDTO);

}
