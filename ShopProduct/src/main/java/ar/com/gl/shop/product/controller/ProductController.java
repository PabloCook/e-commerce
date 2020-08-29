package ar.com.gl.shop.product.controller;

import static java.util.Objects.isNull;

import java.util.List;

import javax.validation.Valid;

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
import ar.com.gl.shop.product.service.impl.ProductServiceImpl;
import ar.com.gl.shop.product.utils.ProductDTOConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value="Product Controller")
public class ProductController {

	private ProductServiceImpl productServiceImpl;
	
	private ProductDTOConverter productDTOConverter;
	
	
	public ProductController(ProductServiceImpl productServiceImpl, ProductDTOConverter productDTOConverter) {
		this.productServiceImpl = productServiceImpl;
		this.productDTOConverter = productDTOConverter;
	}

	@ApiOperation(value = "return all products", response = ProductDTO.class, responseContainer = "List")
	@GetMapping(value = "/products")
	public ResponseEntity<List<ProductDTO>> findAll() {

		return new ResponseEntity<>(productDTOConverter.toDTOList(productServiceImpl.findAll()), HttpStatus.OK);
	}

	@ApiOperation(value = "return product by ID", response = ProductDTO.class)
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<ProductDTO> getById(@PathVariable(name = "id") Long id) {

		return new ResponseEntity<>(productDTOConverter.toDTO(productServiceImpl.getById(id, true)), HttpStatus.OK);
	}

	@ApiOperation(value = "return product by name", response = ProductDTO.class)
	@GetMapping(value = "/products/name/{name}")
	public ResponseEntity<ProductDTO> getByName(@PathVariable(name = "name") String name) {

		return new ResponseEntity<>(productDTOConverter.toDTO(productServiceImpl.getByName(name)), HttpStatus.OK);
	}

	@ApiOperation(value = "return all products by category", response = ProductDTO.class, responseContainer = "List" )
	@GetMapping(value = "/products/category/{id}")
	public ResponseEntity<List<ProductDTO>> getByCategoryId(@PathVariable(name = "id") Long id) {

		return new ResponseEntity<>(productDTOConverter.toDTOList(productServiceImpl.findCategoryById(id)),
				HttpStatus.OK);
	}

	@ApiOperation(value = "create product", response = ProductDTO.class)
	@PostMapping(value = "/products")
	public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO productDTO) {

		return new ResponseEntity<>(
				productDTOConverter.toDTO(productServiceImpl.create(productDTOConverter.toEntity(productDTO))),
				HttpStatus.CREATED);

	}

	
	@ApiOperation(value = "update product", response = ProductDTO.class)
	@PutMapping(value = "/products/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable(name = "id") Long id,
			@Valid @RequestBody ProductDTO productDTO) {

		Product product = productServiceImpl.getById(id, false);
		if (isNull(product)) {
			productDTO = productDTOConverter
					.toDTO(productServiceImpl.create(productDTOConverter.toEntity(productDTO)));
		} else {

			productDTO = productDTOConverter
					.toDTO(productServiceImpl.update(productDTO, product));
		}
		return new ResponseEntity<>(productDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "update specific fields of product", response = ProductDTO.class)
	@ApiModelProperty(value = "update specific fields of product", required = false)
	@PatchMapping(value = "/products/{id}")
	public ResponseEntity<ProductDTO> patch(@PathVariable(name = "id") Long id,
												   @Valid @RequestBody ProductDTO productDTO) {

			Product product = productServiceImpl.getById(id, false);
			
			productDTO = productDTOConverter
					.toDTO(productServiceImpl.update(productDTO, product));
		
		return new ResponseEntity<>(productDTO, HttpStatus.OK);
	}

	@ApiResponses(value = {@ApiResponse(code=200,message = "Producto Eliminado")})
	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
		productServiceImpl.delete(id);
		return new ResponseEntity<>("Producto Eliminado", HttpStatus.OK);
	}

}
