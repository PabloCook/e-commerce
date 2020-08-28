package com.ar.gl.customer.shop.Customershop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ar.gl.customer.shop.Customershop.DTO.CustomerDTO;
import com.ar.gl.customer.shop.Customershop.model.Customer;
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
		List<Customer> customers = new ArrayList<Customer>();
		for(Customer customer : customerRepository.findAll()) {
			if(customer.getEnabled().equals(Boolean.TRUE)) {
				customers.add(customer);
			}
		}
		return customerConverte.toDTOList(customers);
				
	}
	
	public void delete(Long id) {
		customerRepository.delete(customerRepository.findById(id).get());
	}
	
	public CustomerDTO findById(Long id) {
		return customerConverte.toDTO(customerRepository.findById(id).get()); 
	}
	
	public CustomerDTO update(Long id,CustomerDTO customerDTO) {
		Customer customerUpdate = customerConverte.toEntity(customerDTO, customerRepository.findById(id).get());
		return customerConverte.toDTO(customerRepository.save(customerUpdate));
	}
	
	public void softDelete(Long id) {
		Customer customerDelete = customerRepository.findById(id).get();
		customerDelete.setEnabled(Boolean.FALSE);
		customerRepository.save(customerDelete);
	}
	
	public CustomerDTO restore(Long id) {
		Customer customerRestore = customerRepository.findById(id).get();
		customerRestore.setEnabled(Boolean.TRUE);
		return customerConverte.toDTO(customerRepository.save(customerRestore));
	}
	
}
