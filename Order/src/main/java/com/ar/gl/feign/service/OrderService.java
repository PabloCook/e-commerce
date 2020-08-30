package com.ar.gl.feign.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ar.gl.feign.dto.OrderDTO;
import com.ar.gl.feign.dto.ResponseOrderDTO;

public interface OrderService {
	
	public ResponseEntity<ResponseOrderDTO> create(OrderDTO orderDTO);
	
	public ResponseEntity<ResponseOrderDTO> get(Long id);
	
	public ResponseEntity<List<ResponseOrderDTO>> getAllOrders();
	
	public ResponseEntity<List<ResponseOrderDTO>> getOrdersByCustomer(Long id);

	public ResponseEntity<List<ResponseOrderDTO>> getOrdersByProduct(Long id);
	
	public ResponseEntity<ResponseOrderDTO> update(Long id, OrderDTO orderDTO);
	
	public ResponseEntity<ResponseOrderDTO> delete(Long id);
	
	public ResponseEntity<ResponseOrderDTO> softDelete(Long id);
	
}
