package com.ar.gl.feign.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ar.gl.feign.dto.CategoryDTO;
import com.ar.gl.feign.dto.ProductDTO;
import com.ar.gl.feign.shop.product.FeignProduct;

@RestController
public class ProductController {
	
	private FeignProduct product;
	
	public ProductController(FeignProduct product) {
		this.product = product;
	}
	
	@GetMapping(value = "/products")
	public ResponseEntity<Object> findAll(){
		return product.productFindAll();
	}
	
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<Object> getById(@PathVariable(name = "id") Long id){
		return product.productGetById(id);
	}
	
	@GetMapping(value = "/products/name/{name}")
	public ResponseEntity<Object> getByName(@PathVariable(name = "name") String name){
		return product.productGetByName(name);
	}
	
	@GetMapping(value = "/products/category/{id}")
	public ResponseEntity<Object> getByCategoryId(@PathVariable(name = "id") Long id){
		return product.productGetByCategoryId(id);
	}
	
	@PostMapping(value = "/products")
	public ResponseEntity<Object> create(@RequestBody ProductDTO productDTO){
		return product.productCreate(productDTO);
	}
	
	@PutMapping(value = "/productos/{id}")
	public ResponseEntity<Object> update(@PathVariable(name = "id") Long id,
			@RequestBody ProductDTO productDTO){
		return product.productUpdate(id, productDTO);
	}
	
	@PatchMapping(value = "/products/{id}")
	public ResponseEntity<Object> patchDescription(@PathVariable(name = "id") Long id){
		return product.productPatchDescription(id);
	}
	
	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<Object> delete(@PathVariable(name = "id") Long id){
		return product.productDelete(id);
	}
	
	
	
	@GetMapping(value="/categories")
	public ResponseEntity<Object> categoryFindAll(){
		
		return product.categoryFindAll();
	}
	
	@GetMapping(value="/categories/name/{name}")
	public ResponseEntity<Object> categoryFindbyName(@PathVariable(name = "name") String name){
		return product.categoryFindbyName(name);
	}
	
	@GetMapping(value = "/categories/{id}")
	public ResponseEntity<Object> categoryGetById(@PathVariable(name = "id") Long id){
		return product.categoryGetById(id);
	}
	
	@PostMapping(value = "/categories")
	public ResponseEntity<Object> categoryCreate(@RequestBody CategoryDTO categoryDTO){
		return product.categoryCreate(categoryDTO);
	}
	
	@PatchMapping(value = "/categories/{id}/description")
	public ResponseEntity<Object> categoryPatchDescription(@PathVariable(name = "id") Long id){
		return product.categoryPatchDescription(id);
	}
	
	@DeleteMapping(value = "/categories/{id}")
	public ResponseEntity<Object> categoryDelete(@PathVariable(name = "id") Long id){
		return product.categoryDelete(id);
	}

}
