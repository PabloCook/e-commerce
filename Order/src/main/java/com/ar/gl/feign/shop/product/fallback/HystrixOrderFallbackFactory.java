package com.ar.gl.feign.shop.product.fallback;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ar.gl.feign.dto.OrderDTO;
import com.ar.gl.feign.shop.product.FeignOrder;

//@Component
public class HystrixOrderFallbackFactory implements FeignOrder {
	
	OrderDTO orderDTO = OrderDTO.builder()
						.id(0l)
						.productId(0l)
						.customerId(0l)
						.quantity(0)
						.totalPrice(0.0)
						.disable(null)
						.build();
						

	@Override
	public ResponseEntity<OrderDTO> create(OrderDTO orderDTO) {
		return new ResponseEntity<>(orderDTO, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<OrderDTO>> getAllOrders() {
		return new ResponseEntity<>(Arrays.asList(orderDTO), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<OrderDTO> get(Long id) {
		return new ResponseEntity<>(orderDTO, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<OrderDTO>> getOrdersByCustomer(Long id) {
		return new ResponseEntity<>(Arrays.asList(orderDTO), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<OrderDTO>> getOrdersByProduct(Long id) {
		return new ResponseEntity<>(Arrays.asList(orderDTO), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<OrderDTO> update(Long id, OrderDTO orderDTO) {
		return new ResponseEntity<>(orderDTO, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> delete(Long id) {
		return new ResponseEntity<>("No se pudo eliminar", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<OrderDTO> softDelete(Long id) {
		return new ResponseEntity<>(orderDTO, HttpStatus.OK);
	}

}
