package com.ar.gl.customer.shop.Customershop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ar.gl.customer.shop.Customershop.DTO.CustomerDTO;
import com.ar.gl.customer.shop.Customershop.service.impl.CustomerServiceImpl;

@RequestMapping(value = "/customers")
@RestController
public class CustomerController {
	
	private CustomerServiceImpl customerService;
	
	public CustomerController(CustomerServiceImpl customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping(value = "")
	public ResponseEntity<List<CustomerDTO>> findAll(){
		return new ResponseEntity<List<CustomerDTO>>(customerService.findAll(),HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CustomerDTO> getById(@PathVariable Long id){
		return new ResponseEntity<>(customerService.findById(id),HttpStatus.CREATED);
	}
	
	@PostMapping(value = "")
	public ResponseEntity<CustomerDTO> save(@Valid @RequestBody CustomerDTO customerDTO){
		System.out.println(customerDTO);
		return new ResponseEntity<>(customerService.save(customerDTO),HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		customerService.delete(id);
		return new ResponseEntity<>("deleted",HttpStatus.CREATED);
	}
	
}
