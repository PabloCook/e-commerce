package com.ar.gl.feign.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ar.gl.feign.dto.OrderDTO;
import com.ar.gl.feign.dto.ResponseOrderDTO;
import com.ar.gl.feign.service.impl.OrderServiceImpl;
import com.ar.gl.feign.shop.product.FeignOrder;

@RestController
public class OrderController {
	
	private FeignOrder order;
	private OrderServiceImpl orderService;
	
	public OrderController(FeignOrder order, OrderServiceImpl orderService) {
		this.order = order;
		this.orderService = orderService;
	}

	@PostMapping(value = "/orders")
	public ResponseEntity<ResponseOrderDTO> create(@Valid @RequestBody OrderDTO orderDTO) {
		return orderService.create(orderDTO);
	}

	@GetMapping(value = "/orders")
	public ResponseEntity<List<ResponseOrderDTO>> getAllOrders() {
		return orderService.getAllOrders();
	}

	@GetMapping(value = "/orders/{id}")
	public ResponseEntity<ResponseOrderDTO> get(@PathVariable(name = "id") Long id) {
		return orderService.get(id);
	}

	@GetMapping(value = "/orders/customer/{id}")
	public ResponseEntity<List<ResponseOrderDTO>> getOrdersByCustomer(@PathVariable(name = "id") Long id) {
		return orderService.getOrdersByCustomer(id);
	}

	@GetMapping(value = "/orders/product/{id}")
	public ResponseEntity<List<ResponseOrderDTO>> getOrdersByProduct(@PathVariable(name = "id") Long id) {
		return orderService.getOrdersByProduct(id);
	}

	@PutMapping(value = "/orders/{id}")
	public ResponseEntity<ResponseOrderDTO> update(@PathVariable(name = "id") Long id, @Valid @RequestBody OrderDTO orderDTO) {
		return orderService.update(id, orderDTO);
	}

	@DeleteMapping(value = "/orders/{id}")
	public ResponseEntity<ResponseOrderDTO> delete(@PathVariable(name = "id") Long id) {
		return orderService.delete(id);
	}

	@PatchMapping(value = "/orders/{id}")
	public ResponseEntity<ResponseOrderDTO> softDelete(@PathVariable(name = "id") Long id) {
		return orderService.softDelete(id);
	}

}
