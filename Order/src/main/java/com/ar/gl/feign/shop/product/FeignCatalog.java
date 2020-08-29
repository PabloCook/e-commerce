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
import org.springframework.web.bind.annotation.RequestMapping;

import com.ar.gl.feign.dto.CategoryDTO;

@FeignClient(contextId = "catalog" , name="Product-Shop")
@RequestMapping(value="/products/v1")
public interface FeignCatalog {
	
	@GetMapping(value="/categories")
	public ResponseEntity<List<CategoryDTO>> categoryFindAll();
	
	@GetMapping(value="/categories/name/{name}")
	public ResponseEntity<CategoryDTO> categoryFindbyName(@PathVariable(name = "name") String name);
	
	@GetMapping(value = "/categories/{id}")
	public ResponseEntity<CategoryDTO> categoryGetById(@PathVariable(name = "id") Long id);
	
	@PostMapping(value = "/categories")
	public ResponseEntity<CategoryDTO> categoryCreate(@RequestBody CategoryDTO categoryDTO);
	
	@PatchMapping(value = "/categories/{id}/description")
	public ResponseEntity<CategoryDTO> categoryPatch(@PathVariable(name = "id") Long id);
	
	@DeleteMapping(value = "/categories/{id}")
	public ResponseEntity<String> categoryDelete(@PathVariable(name = "id") Long id);

}
