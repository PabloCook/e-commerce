package com.ar.gl.feign.service.impl;

import static com.ar.gl.feign.util.Utilities.toResponseDTO;
import static com.ar.gl.feign.util.Utilities.mergeLists;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ar.gl.feign.dto.CustomerDTO;
import com.ar.gl.feign.dto.OrderDTO;
import com.ar.gl.feign.dto.ProductDTO;
import com.ar.gl.feign.dto.ResponseOrderDTO;
import com.ar.gl.feign.service.OrderService;
import com.ar.gl.feign.shop.product.FeignCustomer;
import com.ar.gl.feign.shop.product.FeignOrder;
import com.ar.gl.feign.shop.product.FeignProduct;

@Service
public class OrderServiceImpl implements OrderService {
	
	private FeignProduct feignProduct;
	private FeignCustomer feignCustomer;
	private FeignOrder feignOrder;
	
	public OrderServiceImpl(FeignProduct feignProduct, FeignCustomer feignCustomer, FeignOrder feignOrder) {
		this.feignProduct = feignProduct;
		this.feignCustomer = feignCustomer;
		this.feignOrder = feignOrder;
	}

	@Override
	public ResponseEntity<ResponseOrderDTO> create(OrderDTO orderDTO) {
		
		final ProductDTO productDTO = feignProduct.getById(orderDTO.getProductId()).getBody();
		
		final CustomerDTO customerDTO = feignCustomer.getById(orderDTO.getCustomerId()).getBody();
		
		if (orderDTO.getQuantity() > productDTO.getStockQuantity()) {
			
			return new ResponseEntity<>(new ResponseOrderDTO("No hay stock disponbile"), HttpStatus.OK);
		}
		
		orderDTO.setTotalPrice(productDTO.getPrice() * orderDTO.getQuantity());
		
		final OrderDTO responseEntity = feignOrder.create(orderDTO).getBody();
		
		productDTO.setStockQuantity(productDTO.getStockQuantity() - orderDTO.getQuantity());
		
		final ProductDTO updatedProductDTO = feignProduct.update(productDTO.getId(), productDTO).getBody();
		
		return new ResponseEntity<>(toResponseDTO(responseEntity, updatedProductDTO, customerDTO), HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<ResponseOrderDTO> get(Long id) {
		
		final OrderDTO orderDTO = feignOrder.get(id).getBody();
		
		return new ResponseEntity<>(toResponseDTO(
													orderDTO,
													feignProduct.getById(orderDTO.getProductId()).getBody(),
													feignCustomer.getById(orderDTO.getCustomerId()).getBody()
												    ), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<ResponseOrderDTO>> getOrdersByCustomer(Long id) {
		
		return new ResponseEntity<>(getAllOrders().getBody()
				.stream()
				.filter(order -> order.getCustomerId().equals(id))
				.collect(Collectors.toList()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<ResponseOrderDTO>> getOrdersByProduct(Long id) {
		
		return new ResponseEntity<>(getAllOrders().getBody()
				.stream()
				.filter(order -> order.getProductId().equals(id))
				.collect(Collectors.toList()), HttpStatus.OK);
	}

	@Override

	public ResponseEntity<List<ResponseOrderDTO>> getAllOrders(Pageable pageable) {
		
		return new ResponseEntity<>(mergeLists(
												feignOrder.getAllOrders(pageable).getBody(),
												feignCustomer.findAll().getBody(),
												feignProduct.findAll().getBody()
												), HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<List<ResponseOrderDTO>> getAllOrders() {
		
		return new ResponseEntity<>(mergeLists(
												feignOrder.getAllOrders().getBody(),
												feignCustomer.findAll().getBody(),
												feignProduct.findAll().getBody()
												), HttpStatus.OK);
	}
	
	

	@Override
	public ResponseEntity<ResponseOrderDTO> update(Long id, OrderDTO orderDTO) {

		return new ResponseEntity<>(get(feignOrder.update(id, orderDTO).getBody().getId()).getBody(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseOrderDTO> delete(Long id) {
		
		feignOrder.delete(id);
		
		return new ResponseEntity<>(new ResponseOrderDTO("Order eliminada"),HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseOrderDTO> softDelete(Long id) {
		
		feignOrder.softDelete(id);
		
		return new ResponseEntity<>(new ResponseOrderDTO("Order eliminada"),HttpStatus.OK);
	}

}
