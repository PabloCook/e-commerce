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
import com.ar.gl.feign.shop.product.fallback.HystrixCustomerFallBackFactory;

@FeignClient(name="Customer-Shop", fallback = HystrixCustomerFallBackFactory.class)
public interface FeignCustomer {
	
	@GetMapping(value = "/api/v1/customers")
	public ResponseEntity<List<CustomerDTO>> findAll();
	
	@GetMapping(value = "/customers/{id}")
	public ResponseEntity<CustomerDTO> getById(@PathVariable Long id);
	
	@PostMapping(value = "/customers")
	public ResponseEntity<CustomerDTO> save(@Valid @RequestBody CustomerDTO customerDTO);
	
	@PutMapping(value ="/customers/{id}")
	public ResponseEntity<CustomerDTO> update(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO);
	
	@PatchMapping(value ="/customers/{id}")
	public ResponseEntity<CustomerDTO> partialUpdate(@PathVariable Long id, @RequestBody CustomerDTO customerDTO);
	
	@PatchMapping(value ="/customers/restore/{id}")
	public ResponseEntity<CustomerDTO> restore(@PathVariable Long id);
	
	@DeleteMapping(value = "/customers/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id);
	
	@DeleteMapping(value = "/customers/softdelete/{id}")
	public ResponseEntity<String> softDelete(@PathVariable Long id);

}
