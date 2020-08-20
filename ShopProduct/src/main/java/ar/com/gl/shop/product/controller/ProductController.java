package ar.com.gl.shop.product.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gl.shop.product.dto.ProductDTO;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.service.impl.CategoryServiceImpl;
import ar.com.gl.shop.product.service.impl.ProductServiceImpl;
import ar.com.gl.shop.product.utils.ProdutcDtoConverter;

@RestController
public class ProductController {
	
	@Autowired
	ProductServiceImpl productServiceImpl;
	
	@Autowired
	CategoryServiceImpl categoryServiceImpl;
	
	@Autowired
	ProdutcDtoConverter produtcDtoConverter;
	
	@GetMapping(value="/products")
	public ResponseEntity<Object> findAll(){
		
		return new ResponseEntity<>(produtcDtoConverter.toDtoList(productServiceImpl.findAll()),HttpStatus.OK);
	}
	
	@GetMapping(value="/products/{id}")
	public ResponseEntity<Object> getById(@PathVariable(name = "id") Long id){
		
		return new ResponseEntity<>(produtcDtoConverter.toDto(productServiceImpl.getById(id, true)),HttpStatus.OK);
	}
	
	@GetMapping(value="/products/{name}")
	public ResponseEntity<Object> getByName(@PathVariable(name = "name") String name){
		
		return new ResponseEntity<>(produtcDtoConverter.toDto(productServiceImpl.getByName(name)),HttpStatus.OK);
	}
	
	@GetMapping(value="/products/category/{id}")
	public ResponseEntity<Object> getByCategoryId(@PathVariable(name = "id") Long id){
		
		return new ResponseEntity<>(productServiceImpl.findAll()
									.stream()
									.filter(p->p.getCategory().getId().equals(id))
									.collect(Collectors.toList()),HttpStatus.OK);
	}
	
	@PostMapping(value="/products")
	public ResponseEntity<Product> create(@Valid @RequestBody ProductDTO productDTO){
		
		return new ResponseEntity<>(productServiceImpl.create(produtcDtoConverter.toEntity(productDTO)),HttpStatus.CREATED);
	}
	
	@PutMapping(value="/products/{id}")
	public ResponseEntity<Product> update(@PathVariable(name="id")Long id, @Valid @RequestBody ProductDTO productDTO){
		
		productDTO.setId(id);
		
		return new ResponseEntity<>(productServiceImpl.update(produtcDtoConverter.toEntity(productDTO)),HttpStatus.OK);
	}
	
	@PatchMapping(value="/products/{id}/description")
	public ResponseEntity<Product> patchDescription(@PathVariable(name="id")Long id,
												    @RequestParam(name="description") String description){
		
		Product product = productServiceImpl.getById(id, true);
		
		product.setDescription(description);
		
		return new ResponseEntity<>(productServiceImpl.update(product),HttpStatus.OK);
	}
	
	@PatchMapping(value="/products/{id}/category/{categoryId}")
	public ResponseEntity<Product> patchCategory(@PathVariable(name="id")Long id,
												 @PathVariable(name="CategoryId") Long categoryId){
		
		Product product = productServiceImpl.getById(id, true);	
		
		product.setCategory(categoryServiceImpl.getById(categoryId, true));
		
		return new ResponseEntity<>(productServiceImpl.update(product),HttpStatus.OK);
	}
	
	@PatchMapping(value="/products/{id}/stock")
	public ResponseEntity<Product> patchStock(@PathVariable(name="id")Long id,
											  @RequestParam(name="quantity") Integer quantity){
		
		Product product = productServiceImpl.getById(id, true);
		
		product.getStock().setQuantity(quantity);
		
		return new ResponseEntity<>(productServiceImpl.update(product),HttpStatus.OK);
	}
	
	@PatchMapping(value="/products/{id}/location")
	public ResponseEntity<Product> patccLocation(@PathVariable(name="id")Long id,
											  @RequestParam(name="location") String location){
		
		Product product = productServiceImpl.getById(id, true);
		
		product.getStock().setLocationCode(location);
		
		return new ResponseEntity<>(productServiceImpl.update(product),HttpStatus.OK);
	}
	
	@DeleteMapping(value="/products/{id}")
	public void delete(@PathVariable(name="id") Long id) {
		productServiceImpl.delete(id);
	}
	


	

}
