package com.ar.gl.customer.shop.Customershop.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ar.gl.customer.shop.Customershop.DTO.CustomerDTO;
import com.ar.gl.customer.shop.Customershop.repository.CustomerRepository;
import com.ar.gl.customer.shop.Customershop.service.CustomerService;
import com.ar.gl.customer.shop.Customershop.util.CustomerConverter;

@Service
public class CustomerServiceImpl implements CustomerService {

	CustomerRepository customerRepository;
	CustomerConverter customerConverte;
	
	public CustomerServiceImpl(CustomerRepository customerRepository,CustomerConverter customerConverter) {
		this.customerRepository = customerRepository;
		this.customerConverte = customerConverter;
	}
	
	public CustomerDTO save(CustomerDTO customerDTO) {
		return customerConverte.toDTO(
				customerRepository.save(
						customerConverte.toEntity(customerDTO)
				));
	}
	
	public List<CustomerDTO> findAll(){
		return customerConverte.toDTOList(customerRepository.findAll());
				
	}
	
	public void delete(Long id) {
		customerRepository.delete(customerRepository.findById(id).get());
	}
	
	public CustomerDTO findById(Long id) {
		return customerConverte.toDTO(customerRepository.findById(id).get()); 
	}
	
}
