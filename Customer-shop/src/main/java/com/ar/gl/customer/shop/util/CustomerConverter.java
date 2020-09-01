package com.ar.gl.customer.shop.Customershop.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ar.gl.customer.shop.Customershop.DTO.CustomerDTO;
import com.ar.gl.customer.shop.Customershop.model.Customer;

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
		List<CustomerDTO> customersDTO = new ArrayList<>();
		for(Customer customer : customers) {
			customersDTO.add(toDTO(customer));
		}
		return customersDTO;
	}
	
}
