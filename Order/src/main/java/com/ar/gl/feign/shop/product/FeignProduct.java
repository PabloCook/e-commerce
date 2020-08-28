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
import org.springframework.web.bind.annotation.RequestMapping;

import com.ar.gl.feign.dto.CategoryDTO;
import com.ar.gl.feign.dto.ProductDTO;

@FeignClient(name="Product-Shop")
@RequestMapping(value="/products/v1")
public interface FeignProduct {
	
	@GetMapping(value = "/products")
	public ResponseEntity<Object> productFindAll();
	
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<Object> productGetById(@PathVariable(name = "id") Long id);
	
	@GetMapping(value = "/products/name/{name}")
	public ResponseEntity<Object> productGetByName(@PathVariable(name = "name") String name);
	
	@GetMapping(value = "/products/category/{id}")
	public ResponseEntity<Object> productGetByCategoryId(@PathVariable(name = "id") Long id);
	
	@PostMapping(value = "/products")
	public ResponseEntity<Object> productCreate(@RequestBody ProductDTO productDTO);
	
	@PutMapping(value = "/productos/{id}")
	public ResponseEntity<Object> productUpdate(@PathVariable(name = "id") Long id, @RequestBody ProductDTO productDTO);
	
	@PatchMapping(value = "/products/{id}")
	public ResponseEntity<Object> productPatchDescription(@PathVariable(name = "id") Long id);
	
	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<Object> productDelete(@PathVariable(name = "id") Long id);

	
	
	@GetMapping(value="/categories")
	public ResponseEntity<Object> categoryFindAll();
	
	@GetMapping(value="/categories/name/{name}")
	public ResponseEntity<Object> categoryFindbyName(@PathVariable(name = "name") String name);
	
	@GetMapping(value = "/categories/{id}")
	public ResponseEntity<Object> categoryGetById(@PathVariable(name = "id") Long id);
	
	@PostMapping(value = "/categories")
	public ResponseEntity<Object> categoryCreate(@RequestBody CategoryDTO categoryDTO);
	
	@PatchMapping(value = "/categories/{id}/description")
	public ResponseEntity<Object> categoryPatchDescription(@PathVariable(name = "id") Long id);
	
	@DeleteMapping(value = "/categories/{id}")
	public ResponseEntity<Object> categoryDelete(@PathVariable(name = "id") Long id);

}
