package com.ar.gl.feign.shop.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name="shop-product", url="http://localhost:9099/products/v1")
public interface FeignCatalog {
	
	@GetMapping(value="/categories")
	public ResponseEntity<Object> findAll();
	
	@GetMapping(value="/categories/name/{name}")
	public ResponseEntity<Object> findbyName(@PathVariable(name = "name") String name);
	
	@GetMapping(value = "/categories/{id}")
	public ResponseEntity<Object> getById(@PathVariable(name = "id") Long id);
	
	@PostMapping(value = "/categories")
	public ResponseEntity<Object> create(@RequestBody Object Object);
	
	@PatchMapping(value = "/categories/{id}/description")
	public ResponseEntity<Object> patchDescription(@PathVariable(name = "id") Long id);
	
	@DeleteMapping(value = "/categories/{id}")
	public ResponseEntity<Object> delete(@PathVariable(name = "id") Long id);

}
