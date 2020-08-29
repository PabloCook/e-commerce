package com.ar.gl.feign.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ar.gl.feign.dto.CategoryDTO;
import com.ar.gl.feign.shop.product.FeignCatalog;

@RestController
public class CatalogController {
	
	private FeignCatalog catalog;
	
	public CatalogController(FeignCatalog catalog) {
		this.catalog = catalog;
	}
	
	@GetMapping(value="/categories")
	public ResponseEntity<List<CategoryDTO>> categoryFindAll(){
		
		return catalog.categoryFindAll();
	}
	
	@GetMapping(value="/categories/name/{name}")
	public ResponseEntity<CategoryDTO> categoryFindbyName(@PathVariable(name = "name") String name){
		return catalog.categoryFindbyName(name);
	}
	
	@GetMapping(value = "/categories/{id}")
	public ResponseEntity<CategoryDTO> categoryGetById(@PathVariable(name = "id") Long id){
		return catalog.categoryGetById(id);
	}
	
	@PostMapping(value = "/categories")
	public ResponseEntity<CategoryDTO> categoryCreate(@RequestBody CategoryDTO categoryDTO){
		return catalog.categoryCreate(categoryDTO);
	}
	
	@PatchMapping(value = "/categories/{id}/description")
	public ResponseEntity<CategoryDTO> categoryPatchDescription(@PathVariable(name = "id") Long id){
		return catalog.categoryPatch(id);
	}
	
	@DeleteMapping(value = "/categories/{id}")
	public ResponseEntity<String> categoryDelete(@PathVariable(name = "id") Long id){
		return catalog.categoryDelete(id);
	}

}
