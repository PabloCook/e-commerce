package com.ar.gl.feign.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ar.gl.feign.dto.CustomerDTO;
import com.ar.gl.feign.shop.product.FeignCustomer;

@RestController
public class CustomerController implements FeignCustomer{
	
	private FeignCustomer customer;
	
	public CustomerController(FeignCustomer customer) {
		this.customer = customer;
	}

	@GetMapping(value = "/customers")
	@Override
	public ResponseEntity<List<CustomerDTO>> findAll() {
		return customer.findAll();
	}
	
	@GetMapping(value = "/customers/{id}")
	@Override
	public ResponseEntity<CustomerDTO> getById(Long id) {
		return customer.getById(id);
	}

	@PostMapping(value = "/customers")
	@Override
	public ResponseEntity<CustomerDTO> save(@Valid CustomerDTO customerDTO) {
		return customer.save(customerDTO);
	}

	@PutMapping(value ="/customers/{id}")
	@Override
	public ResponseEntity<CustomerDTO> update(Long id, @Valid CustomerDTO customerDTO) {
		return customer.update(id, customerDTO);
	}

	@PatchMapping(value ="/customers/{id}")
	@Override
	public ResponseEntity<CustomerDTO> partialUpdate(Long id, CustomerDTO customerDTO) {
		return customer.partialUpdate(id, customerDTO);
	}

	@PatchMapping(value ="/customersrestore/{id}")
	@Override
	public ResponseEntity<CustomerDTO> restore(Long id) {
		return customer.restore(id);
	}

	@DeleteMapping(value = "/customers/{id}")
	@Override
	public ResponseEntity<String> delete(Long id) {
		return customer.delete(id);
	}

	@DeleteMapping(value = "/customers/softdelete/{id}")
	@Override
	public ResponseEntity<String> softDelete(Long id) {
		return customer.softDelete(id);
	}

}
