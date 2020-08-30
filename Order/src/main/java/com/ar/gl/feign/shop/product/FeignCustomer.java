package com.ar.gl.feign.shop.product;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ar.gl.feign.dto.CustomerDTO;

@FeignClient(name="Customer-Shop"/*, fallback = HystrixCustomerFallBackFactory.class*/)
public interface FeignCustomer {
	
	@GetMapping(value = "/api/v1/customers")
	public ResponseEntity<List<CustomerDTO>> findAll();
	
	@GetMapping(value = "/api/v1/customers/{id}")
	public ResponseEntity<CustomerDTO> getById(@PathVariable(name = "id") Long id);
	
	@PostMapping(value = "/api/v1/customers")
	public ResponseEntity<CustomerDTO> save(@Valid @RequestBody CustomerDTO customerDTO);
	
	@PutMapping(value ="/api/v1/customers/{id}")
	public ResponseEntity<CustomerDTO> update(@PathVariable(name = "id") Long id, @Valid @RequestBody CustomerDTO customerDTO);
	
	@PatchMapping(value ="/api/v1/customers/{id}")
	public ResponseEntity<CustomerDTO> partialUpdate(@PathVariable(name = "id") Long id, @Valid @RequestBody CustomerDTO customerDTO);
	
	@PatchMapping(value ="/api/v1/customers/restore/{id}")
	public ResponseEntity<CustomerDTO> restore(@PathVariable(name = "id") Long id);
	
	@DeleteMapping(value = "/api/v1/customers/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id);
	
	@DeleteMapping(value = "/api/v1/customers/softdelete/{id}")
	public ResponseEntity<String> softDelete(@PathVariable(name = "id") Long id);

}
