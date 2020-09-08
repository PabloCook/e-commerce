package com.ar.gl.feign.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ar.gl.feign.dto.CustomerDTO;
import com.ar.gl.feign.dto.OrderDTO;
import com.ar.gl.feign.dto.ProductDTO;
import com.ar.gl.feign.dto.ResponseOrderDTO;

public class Utilities {
	
	private Utilities() {
		
	}
	
	public static List<ResponseOrderDTO> mergeLists(List<OrderDTO> orders,List<CustomerDTO> customers,List<ProductDTO> products) {
		
		List<ResponseOrderDTO> responseOrder = new ArrayList<>();
		
		for (OrderDTO orderDTO : orders) {	
			
			Optional<ProductDTO> oProductDTO = products.stream().filter(product -> product.getId().equals(orderDTO.getProductId())).findFirst();
			Optional<CustomerDTO> oCustomerDTO = customers.stream().filter(customer -> customer.getId().equals(orderDTO.getCustomerId())).findFirst();
		
			if(oProductDTO.isPresent() && oCustomerDTO.isPresent()) {				
				responseOrder.add(toResponseDTO(orderDTO,	oProductDTO.get(), oCustomerDTO.get()));
			}
		}
		return responseOrder;
	}
	
	public static ResponseOrderDTO toResponseDTO(OrderDTO orderDTO, ProductDTO productDTO, CustomerDTO customerDTO) {
		
		return ResponseOrderDTO.builder()
					.id(orderDTO.getId())
					.quantity(orderDTO.getQuantity())
					.totalPrice(orderDTO.getTotalPrice())
					.productId(productDTO.getId())
					.productName(productDTO.getName())
					.productDescription(productDTO.getDescription())
					.productPrice(productDTO.getPrice())
					.productDate(productDTO.getDate())
					.categoryName(productDTO.getCategoryName())
					.categoryDescription(productDTO.getCategoryDescription())
					.customerId(customerDTO.getId())
					.customerName(customerDTO.getName())
					.customerSurname(customerDTO.getSurname())
					.customerDni(customerDTO.getDni())
					.build();
	}
}
