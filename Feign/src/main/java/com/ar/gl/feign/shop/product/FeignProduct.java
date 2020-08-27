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

import com.ar.gl.feign.dto.ProductDTO;
import com.ar.gl.feign.model.Product;

@FeignClient(name="SHOP-PRODUCT", fallback = ProductHystrixFallBackFactory.class)
public interface FeignProduct {
	
	@GetMapping(value = "/products")
	public ResponseEntity<Product> findAll();
	
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<Product> getById(@PathVariable(name = "id") Long id);
	
	@GetMapping(value = "/products/name/{name}")
	public ResponseEntity<Product> getByName(@PathVariable(name = "name") String name);
	
	@GetMapping(value = "/products/category/{id}")
	public ResponseEntity<Product> getByCategoryId(@PathVariable(name = "id") Long id);
	
	@PostMapping(value = "/products")
	public ResponseEntity<Product> create(@RequestBody ProductDTO productDTO);
	
	@PutMapping(value = "/products/{id}")
	public ResponseEntity<Product> update(@PathVariable(name = "id") Long id, @RequestBody Object object);
	
	@PatchMapping(value = "/products/{id}")
	public ResponseEntity<Product> patchDescription(@PathVariable(name = "id") Long id);
	
	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id);

}
