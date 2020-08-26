package com.ar.gl.feign.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ar.gl.feign.shop.product.FeignCatalog;

@RestController
public class CatalogController {
	
	FeignCatalog catalog;
	
	public CatalogController(FeignCatalog catalog) {
		this.catalog = catalog;
	}
	
	@GetMapping(value="/categories")
	public ResponseEntity<Object> findAll(){
		return catalog.findAll();
	}
	
	@GetMapping(value="/categories/name/{name}")
	public ResponseEntity<Object> findbyName(@PathVariable(name = "name") String name){
		return catalog.findAll();
	}
	
	@GetMapping(value = "/categories/{id}")
	public ResponseEntity<Object> getById(@PathVariable(name = "id") Long id){
		return catalog.getById(id);
	}
	
	@PostMapping(value = "/categories")
	public ResponseEntity<Object> create(@RequestBody Object Object){
		return catalog.create(Object);
	}
	
	@PatchMapping(value = "/categories/{id}/description")
	public ResponseEntity<Object> patchDescription(@PathVariable(name = "id") Long id){
		return catalog.patchDescription(id);
	}
	
	@DeleteMapping(value = "/categories/{id}")
	public ResponseEntity<Object> delete(@PathVariable(name = "id") Long id){
		return catalog.delete(id);
	}

}
