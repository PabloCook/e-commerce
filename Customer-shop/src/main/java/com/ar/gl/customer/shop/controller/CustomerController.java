package com.ar.gl.customer.shop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ar.gl.customer.shop.dto.CustomerDTO;
import com.ar.gl.customer.shop.service.impl.CustomerServiceImpl;

@RequestMapping(value = "/customers")
@RestController
public class CustomerController {
	
	private CustomerServiceImpl customerService;
	
	public CustomerController(CustomerServiceImpl customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping(value = "")
	public ResponseEntity<List<CustomerDTO>> findAll(){
		return new ResponseEntity<>(customerService.findAll(),HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CustomerDTO> getById(@PathVariable Long id){
		return new ResponseEntity<>(customerService.findById(id),HttpStatus.OK);
	}
	
	@PostMapping(value = "")
	public ResponseEntity<CustomerDTO> save(@Valid @RequestBody CustomerDTO customerDTO){
		return new ResponseEntity<>(customerService.save(customerDTO),HttpStatus.CREATED);
	}
	
	@PutMapping(value ="/{id}")
	public ResponseEntity<CustomerDTO> update(@PathVariable Long id,@Valid @RequestBody CustomerDTO customerDTO){
		return new ResponseEntity<>(customerService.update(id, customerDTO),HttpStatus.OK);
	}
	
	@PatchMapping(value ="/{id}")
	public ResponseEntity<CustomerDTO> partialUpdate(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
		return new ResponseEntity<>(customerService.update(id, customerDTO),HttpStatus.OK);
	}
	
	@PatchMapping(value ="restore/{id}")
	public ResponseEntity<CustomerDTO> restore(@PathVariable Long id){
		return new ResponseEntity<>(customerService.restore(id),HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		customerService.delete(id);
		return new ResponseEntity<>("deleted",HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/softdelete/{id}")
	public ResponseEntity<String> softDelete(@PathVariable Long id){
		customerService.softDelete(id);
		return new ResponseEntity<>("soft deleted customer",HttpStatus.OK);
	}
	
}
