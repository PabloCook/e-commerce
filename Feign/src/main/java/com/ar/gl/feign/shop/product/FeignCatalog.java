package com.ar.gl.feign.shop.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ar.gl.feign.dto.CategoryDTO;
import com.ar.gl.feign.model.Category;


//@FeignClient(name="catalog", url="http://localhost:9099/products/v1", fallback = (CatalogHystrixFallBackFactory.class))
public interface FeignCatalog {
	
	@GetMapping(value="/categories")
	public ResponseEntity<Category> findAll();
	
	@GetMapping(value="/categories/name/{name}")
	public ResponseEntity<Category> findbyName(@PathVariable(name = "name") String name);
	
	@GetMapping(value = "/categories/{id}")
	public ResponseEntity<Category> getById(@PathVariable(name = "id") Long id);
	
	@PostMapping(value = "/categories")
	public ResponseEntity<Category> create(@RequestBody CategoryDTO categoryDTO);
	
	@PatchMapping(value = "/categories/{id}/description")
	public ResponseEntity<Category> patchDescription(@PathVariable(name = "id") Long id);
	
	@DeleteMapping(value = "/categories/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id);

}
