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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ar.gl.feign.dto.CategoryDTO;

@FeignClient(contextId = "catalog" , name="Product-Shop"/*, fallback = HystrixCatalogFallBackFactory.class*/)
public interface FeignCatalog {
	
	@GetMapping(value="/products/v1/categories")
	public ResponseEntity<List<CategoryDTO>> findAll();
	
	@GetMapping(value="/products/v1/categories/name/{name}")
	public ResponseEntity<CategoryDTO> findbyName(@PathVariable(name = "name") String name);
	
	@GetMapping(value = "/products/v1/categories/{id}")
	public ResponseEntity<CategoryDTO> getById(@PathVariable(name = "id") Long id);
	
	@PostMapping(value = "/products/v1/categories")
	public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO categoryDTO);
	
	@PatchMapping(value = "/products/v1/categories/{id}/description")
	public ResponseEntity<CategoryDTO> patch(@PathVariable(name = "id") Long id, @RequestParam(name = "description") String description);
	
	@DeleteMapping(value = "/products/v1s/categories/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id);

}
