package com.ar.gl.customer.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		List<Customer> customers = new ArrayList<>();
		for(Customer customer : customerRepository.findAll()) {
			if(customer.getEnabled().equals(Boolean.TRUE)) {
				customers.add(customer);
			}
		}
		return customerConverte.toDTOList(customers);
				
	}
	
	public void delete(Long id) {
		
		Optional<Customer> oCustomer = customerRepository.findById(id);
		
		if(oCustomer.isPresent()) customerRepository.delete(oCustomer.get());
		
	}
	
	public CustomerDTO findById(Long id) {
		
		Optional<Customer> oCustomer = customerRepository.findById(id);
		
		if(oCustomer.isPresent()) return customerConverte.toDTO(oCustomer.get()); 
		
		return null;
		
	}
	
	public CustomerDTO update(Long id,CustomerDTO customerDTO) {
		
		Optional<Customer> oCustomer = customerRepository.findById(id);
		
		if(oCustomer.isPresent()) {
			Customer customerUpdate = customerConverte.toEntity(customerDTO, oCustomer.get());
			return customerConverte.toDTO(customerRepository.save(customerUpdate));
		}
		
		return null;
	}
	
	public void softDelete(Long id) {
		
		Optional<Customer> oCustomer = customerRepository.findById(id);
		
		if(oCustomer.isPresent()) {
			Customer customerDelete = oCustomer.get();
			customerDelete.setEnabled(Boolean.FALSE);
			customerRepository.save(customerDelete);
		}
		

	}
	
	public CustomerDTO restore(Long id) {
		
		Optional<Customer> oCustomer = customerRepository.findById(id);
		
		if (oCustomer.isPresent()) {
			
			Customer customerRestore = oCustomer.get();
			customerRestore.setEnabled(Boolean.TRUE);
			return customerConverte.toDTO(customerRepository.save(customerRestore));
			
		}
		
		return null;

	}
	
}
