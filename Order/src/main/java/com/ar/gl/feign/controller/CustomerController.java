package com.ar.gl.feign.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<CustomerDTO> getById(@PathVariable(name = "id") Long id) {
		return customer.getById(id);
	}

	@PostMapping(value = "/customers")
	@Override
	public ResponseEntity<CustomerDTO> save(@Valid @RequestBody CustomerDTO customerDTO) {
		return customer.save(customerDTO);
	}

	@PutMapping(value ="/customers/{id}")
	@Override
	public ResponseEntity<CustomerDTO> update(@PathVariable(name = "id") Long id, @Valid @RequestBody CustomerDTO customerDTO) {
		return customer.update(id, customerDTO);
	}

	@PatchMapping(value ="/customers/{id}")
	@Override
	public ResponseEntity<CustomerDTO> partialUpdate(@PathVariable(name = "id") Long id, @Valid @RequestBody CustomerDTO customerDTO) {
		return customer.partialUpdate(id, customerDTO);
	}

	@PatchMapping(value ="/customersrestore/{id}")
	@Override
	public ResponseEntity<CustomerDTO> restore(@PathVariable(name = "id") Long id) {
		return customer.restore(id);
	}

	@DeleteMapping(value = "/customers/{id}")
	@Override
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
		return customer.delete(id);
	}

	@DeleteMapping(value = "/customers/softdelete/{id}")
	@Override
	public ResponseEntity<String> softDelete(@PathVariable(name = "id") Long id) {
		return customer.softDelete(id);
	}

}
