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
import org.springframework.web.bind.annotation.RequestMapping;

import com.ar.gl.feign.dto.ProductDTO;

@FeignClient(contextId = "product" ,name="Product-Shop")
@RequestMapping(value="/products/v1")
public interface FeignProduct {
	
	@GetMapping(value = "/products")
	public ResponseEntity<List<ProductDTO>> productFindAll();
	
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<ProductDTO> productGetById(@PathVariable(name = "id") Long id);
	
	@GetMapping(value = "/products/name/{name}")
	public ResponseEntity<ProductDTO> productGetByName(@PathVariable(name = "name") String name);
	
	@GetMapping(value = "/products/category/{id}")
	public ResponseEntity<List<ProductDTO>> productGetByCategoryId(@PathVariable(name = "id") Long id);
	
	@PostMapping(value = "/products")
	public ResponseEntity<ProductDTO> productCreate(@RequestBody ProductDTO productDTO);
	
	@PutMapping(value = "/productos/{id}")
	public ResponseEntity<ProductDTO> productUpdate(@PathVariable(name = "id") Long id, @RequestBody ProductDTO productDTO);
	
	@PatchMapping(value = "/products/{id}")
	public ResponseEntity<ProductDTO> productPatch(@PathVariable(name = "id") Long id);
	
	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<String> productDelete(@PathVariable(name = "id") Long id);

}
