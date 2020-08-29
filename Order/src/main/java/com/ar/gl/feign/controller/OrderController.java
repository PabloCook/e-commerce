package com.ar.gl.feign.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ar.gl.feign.dto.OrderDTO;
import com.ar.gl.feign.service.impl.OrderServiceImpl;
import com.ar.gl.feign.shop.product.FeignOrder;

@RestController
public class OrderController implements FeignOrder {
	
	private FeignOrder order;
	private OrderServiceImpl orderService;
	
	public OrderController(FeignOrder order, OrderServiceImpl orderService) {
		this.order = order;
		this.orderService = orderService;
	}

	@Override
	@PostMapping(value = "/orders")
	public ResponseEntity<OrderDTO> create(OrderDTO orderDTO) {
		return orderService.create(orderDTO);
	}

	@Override
	@GetMapping(value = "/orders")
	public ResponseEntity<List<OrderDTO>> getAllOrders() {
		return order.getAllOrders();
	}

	@Override
	@GetMapping(value = "/orders/{id}")
	public ResponseEntity<OrderDTO> get(Long id) {
		return order.get(id);
	}

	@Override
	@GetMapping(value = "/orders/customer/{id}")
	public ResponseEntity<List<OrderDTO>> getOrdersByCustomer(Long id) {//id de customer
		return order.getOrdersByCustomer(id);
	}

	@Override
	@GetMapping(value = "/orders/product/{id}")
	public ResponseEntity<List<OrderDTO>> getOrdersByProduct(Long id) {//id de products
		return order.getOrdersByProduct(id);
	}

	@Override
	@PutMapping(value = "/orders/{id}")
	public ResponseEntity<OrderDTO> update(Long id, OrderDTO orderDTO) {
		return order.update(id, orderDTO);
	}

	@Override
	@DeleteMapping(value = "/orders/{id}")
	public ResponseEntity<String> delete(Long id) {
		return order.delete(id);
	}

	@Override
	@PatchMapping(value = "/orders/{id}")
	public ResponseEntity<OrderDTO> softDelete(Long id) {
		return order.softDelete(id);
	}

}
