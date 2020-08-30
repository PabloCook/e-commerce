package com.ar.gl.feign.shop.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ar.gl.feign.dto.ProductDTO;

@FeignClient(contextId = "product", name="Product-Shop"/*, fallback = HystrixProductFallbackFactory.class*/)
public interface FeignProduct {
	
	@GetMapping(value = "/products/v1/products")
	public ResponseEntity<List<ProductDTO>> findAll();
	
	@GetMapping(value = "/products/v1/products/{id}")
	public ResponseEntity<ProductDTO> getById(@PathVariable(name = "id") Long id);
	
	@GetMapping(value = "/products/v1/products/name/{name}")
	public ResponseEntity<ProductDTO> getByName(@PathVariable(name = "name") String name);
	
	@GetMapping(value = "/products/v1/products/category/{id}")
	public ResponseEntity<List<ProductDTO>> getByCategoryId(@PathVariable(name = "id") Long id);
	
	@PostMapping(value = "/products/v1/products")
	public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO);
	
	@PutMapping(value = "/products/v1/products/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable(name = "id") Long id, @RequestBody ProductDTO productDTO);
	
	@PatchMapping(value = "/products/v1/products/{id}")
	public ResponseEntity<ProductDTO> patch(@PathVariable(name = "id") Long id, @RequestBody ProductDTO productDTO);
	
	@DeleteMapping(value = "/products/v1/products/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id);

}
