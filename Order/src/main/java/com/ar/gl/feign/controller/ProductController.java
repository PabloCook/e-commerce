package com.ar.gl.feign.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ar.gl.feign.dto.ProductDTO;
import com.ar.gl.feign.shop.product.FeignProduct;

@RestController
public class ProductController {
	
	private FeignProduct product;
	
	public ProductController(FeignProduct product) {
		this.product = product;
	}
	
	@GetMapping(value = "/products")
	public ResponseEntity<List<ProductDTO>> findAll(){
		return product.productFindAll();
	}
	
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<ProductDTO> getById(@PathVariable(name = "id") Long id){
		return product.productGetById(id);
	}
	
	@GetMapping(value = "/products/name/{name}")
	public ResponseEntity<ProductDTO> getByName(@PathVariable(name = "name") String name){
		return product.productGetByName(name);
	}
	
	@GetMapping(value = "/products/category/{id}")
	public ResponseEntity<List<ProductDTO>> getByCategoryId(@PathVariable(name = "id") Long id){
		return product.productGetByCategoryId(id);
	}
	
	@PostMapping(value = "/products")
	public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO){
		return product.productCreate(productDTO);
	}
	
	@PutMapping(value = "/productos/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable(name = "id") Long id,
			@RequestBody ProductDTO productDTO){
		return product.productUpdate(id, productDTO);
	}
	
	@PatchMapping(value = "/products/{id}")
	public ResponseEntity<ProductDTO> patchDescription(@PathVariable(name = "id") Long id){
		return product.productPatch(id);
	}
	
	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id){
		return product.productDelete(id);
	}
	
}
