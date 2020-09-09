package com.ar.gl.customer.shop.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ar.gl.customer.shop.dto.CustomerDTO;
import com.ar.gl.customer.shop.model.Customer;

@Component
public class CustomerConverter extends Converter {

	public Customer toEntity(CustomerDTO customerDTO) {
		return (Customer) super.fromTo(customerDTO, Customer.class);
	}

	public Customer toEntity(CustomerDTO customerDTO, Customer customer) {
		return (Customer) super.fromTo(customerDTO, customer);
	}

	public CustomerDTO toDTO(Customer customer) {
		return (CustomerDTO) super.fromTo(customer, CustomerDTO.class);
	}

	public List<CustomerDTO> toDTOList(List<Customer> customers) {
		return customers.stream().map(this::toDTO).collect(Collectors.toList());
	}

}
