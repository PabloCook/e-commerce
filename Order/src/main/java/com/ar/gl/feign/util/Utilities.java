package com.ar.gl.feign.util;

import java.util.ArrayList;
import java.util.List;

import com.ar.gl.feign.dto.CustomerDTO;
import com.ar.gl.feign.dto.OrderDTO;
import com.ar.gl.feign.dto.ProductDTO;
import com.ar.gl.feign.dto.ResponseOrderDTO;

public class Utilities {
	
	static public List<ResponseOrderDTO> mergeLists(List<OrderDTO> orders,List<CustomerDTO> customers,List<ProductDTO> products) {
		List<ResponseOrderDTO> responseOrder = new ArrayList<>();
		for (OrderDTO orderDTO : orders) {	
			responseOrder.add(makeResponseDTO(
				orderDTO,
				products.stream().filter(c -> c.getId().equals(orderDTO.getProductId())).findFirst().get(),
				customers.stream().filter(c -> c.getId().equals(orderDTO.getCustomerId())).findFirst().get())
				);
		}
		return responseOrder;
	}
	
	static public ResponseOrderDTO makeResponseDTO(OrderDTO orderDTO, ProductDTO productDTO, CustomerDTO customerDTO) {
		
		return ResponseOrderDTO.builder()
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
	}
}
