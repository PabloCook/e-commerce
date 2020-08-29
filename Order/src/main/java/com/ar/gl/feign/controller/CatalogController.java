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
public class CatalogController implements FeignCatalog{
	
	private FeignCatalog catalog;
	
	public CatalogController(FeignCatalog catalog) {
		this.catalog = catalog;
	}
	
	@Override
	@GetMapping(value="/categories")
	public ResponseEntity<List<CategoryDTO>> findAll(){
		
		return catalog.findAll();
	}
	
	@Override
	@GetMapping(value="/categories/name/{name}")
	public ResponseEntity<CategoryDTO> findbyName(@PathVariable(name = "name") String name){
		return catalog.findbyName(name);
	}
	
	@Override
	@GetMapping(value = "/categories/{id}")
	public ResponseEntity<CategoryDTO> getById(@PathVariable(name = "id") Long id){
		return catalog.getById(id);
	}
	
	@Override
	@PostMapping(value = "/categories")
	public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO categoryDTO){
		return catalog.create(categoryDTO);
	}
	
	@Override
	@PatchMapping(value = "/categories/{id}/description")
	public ResponseEntity<CategoryDTO> patch(@PathVariable(name = "id") Long id){
		return catalog.patch(id);
	}
	
	@Override
	@DeleteMapping(value = "/categories/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id){
		return catalog.delete(id);
	}

}
