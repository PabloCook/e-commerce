package com.ar.gl.feign.shop.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ar.gl.feign.dto.CategoryDTO;

@FeignClient(contextId = "catalog" , name="Product-Shop")
public interface FeignCatalog {
	
	@GetMapping(value="/products/v1/categories")
	public ResponseEntity<List<CategoryDTO>> categoryFindAll();
	
	@GetMapping(value="/products/v1/categories/name/{name}")
	public ResponseEntity<CategoryDTO> categoryFindbyName(@PathVariable(name = "name") String name);
	
	@GetMapping(value = "/products/v1/categories/{id}")
	public ResponseEntity<CategoryDTO> categoryGetById(@PathVariable(name = "id") Long id);
	
	@PostMapping(value = "/products/v1/categories")
	public ResponseEntity<CategoryDTO> categoryCreate(@RequestBody CategoryDTO categoryDTO);
	
	@PatchMapping(value = "/products/v1/categories/{id}/description")
	public ResponseEntity<CategoryDTO> categoryPatch(@PathVariable(name = "id") Long id);
	
	@DeleteMapping(value = "/products/v1s/categories/{id}")
	public ResponseEntity<String> categoryDelete(@PathVariable(name = "id") Long id);

}
