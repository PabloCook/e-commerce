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
public class ProductController implements FeignProduct{
	
	private FeignProduct product;
	
	public ProductController(FeignProduct product) {
		this.product = product;
	}
	
	@Override
	@GetMapping(value = "/products")
	public ResponseEntity<List<ProductDTO>> findAll(){
		return product.findAll();
	}
	
	@Override
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<ProductDTO> getById(@PathVariable(name = "id") Long id){
		return product.getById(id);
	}
	
	@Override
	@GetMapping(value = "/products/name/{name}")
	public ResponseEntity<ProductDTO> getByName(@PathVariable(name = "name") String name){
		return product.getByName(name);
	}
	
	@Override
	@GetMapping(value = "/products/category/{id}")
	public ResponseEntity<List<ProductDTO>> getByCategoryId(@PathVariable(name = "id") Long id){
		return product.getByCategoryId(id);
	}
	
	@Override
	@PostMapping(value = "/products")
	public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO){
		return product.create(productDTO);
	}
	
	@Override
	@PutMapping(value = "/productos/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable(name = "id") Long id,
			@RequestBody ProductDTO productDTO){
		return product.update(id, productDTO);
	}
	
	@Override
	@PatchMapping(value = "/products/{id}")
	public ResponseEntity<ProductDTO> patch(@PathVariable(name = "id") Long id){
		return product.patch(id);
	}
	
	@Override
	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id){
		return product.delete(id);
	}
	
}
