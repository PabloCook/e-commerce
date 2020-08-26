package com.ar.gl.feign.shop.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="shop-product", url="http://localhost:9099/products/v1")
public interface FeignProduct {
	
	@GetMapping(value = "/products")
	public ResponseEntity<Object> findAll();
	
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<Object> getById(@PathVariable(name = "id") Long id);
	
	@GetMapping(value = "/products/name/{name}")
	public ResponseEntity<Object> getByName(@PathVariable(name = "name") String name);
	
	@GetMapping(value = "/products/category/{id}")
	public ResponseEntity<Object> getByCategoryId(@PathVariable(name = "id") Long id);
	
	@PostMapping(value = "/products")
	public ResponseEntity<Object> create(@RequestBody Object object);
	
	@PutMapping(value = "/products/{id}")
	public ResponseEntity<Object> update(@PathVariable(name = "id") Long id, @RequestBody Object object);
	
	@PatchMapping(value = "/products/{id}")
	public ResponseEntity<Object> patchDescription(@PathVariable(name = "id") Long id);
	
	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<Object> delete(@PathVariable(name = "id") Long id);







}
