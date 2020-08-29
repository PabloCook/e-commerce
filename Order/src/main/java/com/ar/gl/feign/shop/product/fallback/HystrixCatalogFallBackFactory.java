package com.ar.gl.feign.shop.product.fallback;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ar.gl.feign.dto.CategoryDTO;
import com.ar.gl.feign.shop.product.FeignCatalog;

@Component
public class HystrixCatalogFallBackFactory implements FeignCatalog {
	
	CategoryDTO categoryDTO = CategoryDTO.builder()
							  .id(0l)
							  .name("none")
							  .description("none")
							  .enabled(null)
							  .build();

	@Override
	public ResponseEntity<List<CategoryDTO>> findAll() {
		return new ResponseEntity<>(Arrays.asList(categoryDTO), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CategoryDTO> findbyName(String name) {
		return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CategoryDTO> getById(Long id) {
		return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CategoryDTO> create(CategoryDTO categoryDTO) {
		return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CategoryDTO> patch(Long id) {
		return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> delete(Long id) {
		return new ResponseEntity<>("No se pudo eliminar", HttpStatus.OK);
	}

}
