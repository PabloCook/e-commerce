package ar.com.gl.shop.product.controller;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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
import org.springframework.web.bind.annotation.RestController;

import ar.com.gl.shop.product.dto.ProductDTO;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.service.CategoryService;

import ar.com.gl.shop.product.service.impl.ProductServiceImpl;
import ar.com.gl.shop.product.utils.ProductDTOConverter;

@RestController
public class ProductController {


	private ProductServiceImpl productServiceImpl;
	
	private ProductDTOConverter productDTOConverter;
	
	private CategoryService categoryService;
	
	@Autowired
	public ProductController(ProductServiceImpl productServiceImpl, ProductDTOConverter productDTOConverter, CategoryService categoryService) {
		this.productServiceImpl = productServiceImpl;
		this.productDTOConverter = productDTOConverter;
		this.categoryService = categoryService;
	}

	@GetMapping(value = "/products")
	public ResponseEntity<Object> findAll() {

		return new ResponseEntity<>(productDTOConverter.toDTOList(productServiceImpl.findAll()), HttpStatus.OK);
	}

	@GetMapping(value = "/products/{id}")
	public ResponseEntity<Object> getById(@PathVariable(name = "id") Long id) {

		return new ResponseEntity<>(productDTOConverter.toDTO(productServiceImpl.getById(id, true)), HttpStatus.OK);
	}

	@GetMapping(value = "/products/name/{name}")
	public ResponseEntity<Object> getByName(@PathVariable(name = "name") String name) {

		return new ResponseEntity<>(productDTOConverter.toDTO(productServiceImpl.getByName(name)), HttpStatus.OK);
	}

	@GetMapping(value = "/products/category/{id}")
	public ResponseEntity<Object> getByCategoryId(@PathVariable(name = "id") Long id) {

		return new ResponseEntity<>(productDTOConverter.toDTOList(productServiceImpl.findCategoryById(id)),
				HttpStatus.OK);
	}

	@PostMapping(value = "/products")
	public ResponseEntity<Object> create(@RequestBody ProductDTO productDTO) {

		return new ResponseEntity<>(
				productDTOConverter.toDTO(productServiceImpl.create(productDTOConverter.toEntity(productDTO))),
				HttpStatus.CREATED);

	}

	@PutMapping(value = "/products/{id}")
	public ResponseEntity<Object> update(@PathVariable(name = "id") Long id,
			@RequestBody ProductDTO productDTO) {

		Product product = productServiceImpl.getById(id, false);
		if (isNull(product)) {
			productDTO = productDTOConverter
					.toDTO(productServiceImpl.create(productDTOConverter.toEntity(productDTO)));
		} else {
			if (nonNull(productDTO.getCategoryId())) {
				product.setCategory(categoryService.getById(productDTO.getCategoryId(), true));
				productDTO.setCategoryId(null);
			}
			productDTO.setId(id);
			productDTO = productDTOConverter
					.toDTO(productServiceImpl.update(productDTOConverter.toEntity(productDTO, product)));
		}
		return new ResponseEntity<>(productDTO, HttpStatus.OK);
	}

	@PatchMapping(value = "/products/{id}")
	public ResponseEntity<Object> patchDescription(@PathVariable(name = "id") Long id,
												   @RequestBody ProductDTO productDTO) {

			Product product = productServiceImpl.getById(id, false);
			
			if (nonNull(productDTO.getCategoryId())) {
				product.setCategory(categoryService.getById(productDTO.getCategoryId(), true));
				productDTO.setCategoryId(null);
			}
			
			productDTO.setId(id);
			productDTO = productDTOConverter
					.toDTO(productServiceImpl.update(productDTOConverter.toEntity(productDTO, product)));
		
		return new ResponseEntity<>(productDTO, HttpStatus.OK);
	}


	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<Object> delete(@PathVariable(name = "id") Long id) {
		productServiceImpl.delete(id);
		return new ResponseEntity<>("Producto Eliminado", HttpStatus.OK);
	}

}
