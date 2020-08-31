package com.ar.gl.feign.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
		
		final ProductDTO PRODUCT_DTO = feignProduct.getById(orderDTO.getProductId()).getBody();
		
		final CustomerDTO CUSTOMER_DTO = feignCustomer.getById(orderDTO.getCustomerId()).getBody();
		
		if (orderDTO.getQuantity() > PRODUCT_DTO.getStockQuantity()) {
			
			return new ResponseEntity<>(new ResponseOrderDTO("No hay stock disponbile"), HttpStatus.OK);
		}
		
		orderDTO.setTotalPrice(PRODUCT_DTO.getPrice() * orderDTO.getQuantity());
		
		final OrderDTO RESPONSE_ENTITY = feignOrder.create(orderDTO).getBody();
		
		PRODUCT_DTO.setStockQuantity(PRODUCT_DTO.getStockQuantity() - orderDTO.getQuantity());
		
		final ProductDTO UPDATED_PRODUCT_DTO = feignProduct.update(PRODUCT_DTO.getId(), PRODUCT_DTO).getBody();
		
		return new ResponseEntity<>(makeResponseDTO(RESPONSE_ENTITY, UPDATED_PRODUCT_DTO, CUSTOMER_DTO), HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<ResponseOrderDTO> get(Long id) {
		
		final OrderDTO ORDER_DTO = feignOrder.get(id).getBody();
		
		return new ResponseEntity<>(
				makeResponseDTO(
								ORDER_DTO,
								feignProduct.getById(ORDER_DTO.getProductId()).getBody(),
								feignCustomer.getById(ORDER_DTO.getCustomerId()).getBody()
							    ), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<ResponseOrderDTO>> getOrdersByCustomer(Long id) {
		
		return new ResponseEntity<>(getAllOrders().getBody()
				.stream()
				.filter(o->o.getCustomerId().equals(id))
				.collect(Collectors.toList()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<ResponseOrderDTO>> getOrdersByProduct(Long id) {
		
		
		return new ResponseEntity<>(getAllOrders().getBody()
				.stream()
				.filter(o->o.getProductId().equals(id))
				.collect(Collectors.toList()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<ResponseOrderDTO>> getAllOrders() {
		
		List<OrderDTO> orders = feignOrder.getAllOrders().getBody();
		
		List<CustomerDTO> customers = feignCustomer.findAll().getBody();
		List<ProductDTO> products = feignProduct.findAll().getBody();
		
		List<ResponseOrderDTO> responseOrder = new ArrayList<>();
		
		for (OrderDTO orderDTO : orders) {	
			responseOrder.add(this.makeResponseDTO(
				orderDTO,
				products.stream().filter(c -> c.getId().equals(orderDTO.getProductId())).findFirst().get(),
				customers.stream().filter(c -> c.getId().equals(orderDTO.getCustomerId())).findFirst().get())
				);
		}
		
		return new ResponseEntity<>(responseOrder,HttpStatus.OK);
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
	
	private ResponseOrderDTO makeResponseDTO(OrderDTO orderDTO, ProductDTO productDTO, CustomerDTO customerDTO) {
		
		final ResponseOrderDTO RESPONSE_ORDER_DTO = ResponseOrderDTO.builder()
													.id(orderDTO.getId())
													.quantity(orderDTO.getQuantity())
													.totalPrice(orderDTO.getTotalPrice())
													.productId(productDTO.getId())
													.productName(productDTO.getName())
													.productDescription(productDTO.getDescription())
													.productPrice(productDTO.getPrice())
													.categoryName(productDTO.getCategoryName())
													.categoryDescription(productDTO.getCategoryDescription())
													.customerId(customerDTO.getId())
													.customerName(customerDTO.getName())
													.customerSurname(customerDTO.getSurname())
													.customerDni(customerDTO.getDni())
													.build();
		
		return RESPONSE_ORDER_DTO;
	}



}
