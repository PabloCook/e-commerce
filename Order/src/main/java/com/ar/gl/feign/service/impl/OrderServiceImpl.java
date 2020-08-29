package com.ar.gl.feign.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ar.gl.feign.dto.OrderDTO;
import com.ar.gl.feign.dto.ProductDTO;
import com.ar.gl.feign.service.OrderService;
import com.ar.gl.feign.shop.product.FeignCustomer;
import com.ar.gl.feign.shop.product.FeignOrder;
import com.ar.gl.feign.shop.product.FeignProduct;

@Service
public class OrderServiceImpl implements OrderService {
	
	private FeignProduct feignProduct;
	private FeignCustomer feignCustomer;
	private FeignOrder feignOrder;
	
	public OrderServiceImpl(FeignProduct product, FeignCustomer customer, FeignOrder feignOrder) {
		this.feignProduct = product;
		this.feignCustomer = customer;
		this.feignOrder = feignOrder;
	}

	@Override
	public ResponseEntity<OrderDTO> create(OrderDTO orderDTO) {
		
		
		ProductDTO productDTO = feignProduct.getById(orderDTO.getProductId()).getBody();
		
		feignCustomer.getById(orderDTO.getCustomerId()).getBody();
		
		if (orderDTO.getQuantity() > productDTO.getStockQuantity()) {
			return null;
		}
		
		orderDTO.setTotalPrice(productDTO.getPrice() * orderDTO.getQuantity());
		
		ResponseEntity<OrderDTO> responseEntity = feignOrder.create(orderDTO);
		
		productDTO.setStockQuantity(productDTO.getStockQuantity() - orderDTO.getQuantity());
		
		feignProduct.update(productDTO.getId(), productDTO);
		
		return responseEntity;
	}

}
