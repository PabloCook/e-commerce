package com.ar.gl.feign.shop.product.fallback;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ar.gl.feign.dto.CategoryDTO;
import com.ar.gl.feign.shop.product.FeignCatalog;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component

public class HystrixCatalogFallBackFactory implements FeignCatalog {
	
	CategoryDTO categoryDTO = CategoryDTO.builder()
							  .id(0l)
							  .name("none")
							  .description("none")
							  .enabled(null)
							  .build();

	@Override
	@HystrixCommand(ignoreExceptions = Exception.class)
	public ResponseEntity<List<CategoryDTO>> findAll() {
		return new ResponseEntity<>(Arrays.asList(categoryDTO), HttpStatus.OK);
	}

	@Override
	@HystrixCommand(ignoreExceptions = Exception.class)
	public ResponseEntity<CategoryDTO> findbyName(String name) {
		return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
	}

	@Override
	@HystrixCommand(ignoreExceptions = Exception.class)
	public ResponseEntity<CategoryDTO> getById(Long id) {
		return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
	}

	@Override
	@HystrixCommand(ignoreExceptions = Exception.class)
	public ResponseEntity<CategoryDTO> create(CategoryDTO categoryDTO) {
		return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
	}

	@Override
	@HystrixCommand(ignoreExceptions = Exception.class)
	public ResponseEntity<CategoryDTO> patch(@PathVariable(name = "id") Long id, @RequestParam(name = "description") String description) {
		return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
	}

	@Override
	@HystrixCommand(ignoreExceptions = Exception.class)
	public ResponseEntity<String> delete(Long id) {
		return new ResponseEntity<>("No se pudo eliminar", HttpStatus.OK);
	}

}
