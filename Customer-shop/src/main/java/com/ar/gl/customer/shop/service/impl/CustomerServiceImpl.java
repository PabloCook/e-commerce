package com.ar.gl.customer.shop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ar.gl.customer.shop.dto.CustomerDTO;
import com.ar.gl.customer.shop.model.Customer;
import com.ar.gl.customer.shop.repository.CustomerRepository;
import com.ar.gl.customer.shop.service.CustomerService;
import com.ar.gl.customer.shop.util.CustomerConverter;

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
		
		List<Customer> customers = customerRepository.findAll().stream()
								   .filter(customer->customer.getEnabled().equals(Boolean.TRUE))
								   .collect(Collectors.toList());

		return customerConverte.toDTOList(customers);
				
	}
	
	public void delete(Long id) {
		customerRepository.delete(this.getCustomerById(id));
	}
	
	public CustomerDTO findById(Long id) {
		return customerConverte.toDTO(this.getCustomerById(id)); 
		
	}
	
	public CustomerDTO update(Long id,CustomerDTO customerDTO) {
		Customer customerUpdate = customerConverte.toEntity(customerDTO, this.getCustomerById(id));
		return customerConverte.toDTO(customerRepository.save(customerUpdate));
	}
	
	public void softDelete(Long id) {			
		Customer customerDelete = this.getCustomerById(id);
		customerDelete.setEnabled(Boolean.FALSE);
		customerRepository.save(customerDelete);
	}
	
	public CustomerDTO restore(Long id) {
		Customer customerRestore = this.getCustomerById(id);
		customerRestore.setEnabled(Boolean.TRUE);
		return customerConverte.toDTO(customerRepository.save(customerRestore));
	}
	
	private Customer getCustomerById(Long id) {
		Optional<Customer> oCustomer = customerRepository.findById(id);
		if (!oCustomer.isPresent()) throw new NoSuchElementException("customer not found");
		return oCustomer.get();
	}
	
}
