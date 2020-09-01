package com.ar.gl.feign.shop.product.fallback;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ar.gl.feign.dto.ProductDTO;
import com.ar.gl.feign.shop.product.FeignProduct;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class HystrixProductFallbackFactory implements FeignProduct {
	
	private ProductDTO productDTO = ProductDTO.builder()
							  .id(0l)
							  .name("none")
							  .description("none")
							  .price(0.0)
							  .enabled(null)
							  .categoryId(0l)
							  .categoryName("none")
							  .categoryDescription("none")
							  .categoryEnabled(null)
							  .stockId(0l)
							  .stockQuantity(0)
							  .stockLocationCode("nose")
							  .build();
	

	@Override
	@HystrixCommand(ignoreExceptions = Exception.class)
	public ResponseEntity<List<ProductDTO>> findAll() {
		
		return new ResponseEntity<>(Arrays.asList(productDTO), HttpStatus.OK);
	}

	@Override
	@HystrixCommand(ignoreExceptions = Exception.class)
	public ResponseEntity<ProductDTO> getById(Long id) {
		return new ResponseEntity<>(productDTO, HttpStatus.OK);
	}

	@Override
	@HystrixCommand(ignoreExceptions = Exception.class)
	public ResponseEntity<ProductDTO> getByName(String name) {
		return new ResponseEntity<>(productDTO, HttpStatus.OK);
	}

	@Override
	@HystrixCommand(ignoreExceptions = Exception.class)
	public ResponseEntity<List<ProductDTO>> getByCategoryId(Long id) {
		return new ResponseEntity<>(Arrays.asList(productDTO), HttpStatus.OK);
	}

	@Override
	@HystrixCommand(ignoreExceptions = Exception.class)
	public ResponseEntity<ProductDTO> create(ProductDTO productDTO) {
		return new ResponseEntity<>(productDTO, HttpStatus.OK);
	}

	@Override
	@HystrixCommand(ignoreExceptions = Exception.class)
	public ResponseEntity<ProductDTO> update(Long id, ProductDTO productDTO) {
		return new ResponseEntity<>(productDTO, HttpStatus.OK);
	}

	@Override
	@HystrixCommand(ignoreExceptions = Exception.class)
	public ResponseEntity<ProductDTO> patch(Long id, ProductDTO productDTO) {
		return new ResponseEntity<>(productDTO, HttpStatus.OK);
	}

	@Override
	@HystrixCommand(ignoreExceptions = Exception.class)
	public ResponseEntity<String> delete(Long id) {
		return new ResponseEntity<>("Servicio Caido no se pudo eliminar", HttpStatus.OK);
	}

}
