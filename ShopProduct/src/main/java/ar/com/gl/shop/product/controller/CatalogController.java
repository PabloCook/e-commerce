package ar.com.gl.shop.product.controller;

import static java.util.Objects.nonNull;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gl.shop.product.dto.CategoryDTO;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.service.impl.CategoryServiceImpl;

import ar.com.gl.shop.product.utils.CategoryDTOConverter;

@RestController
public class CatalogController {

	private CategoryDTOConverter categoryDTOConverter;
		
	private CategoryServiceImpl categoryServiceImpl;

	@Autowired
	public CatalogController(CategoryDTOConverter categoryDTOConverter, CategoryServiceImpl categoryServiceImpl) {
		this.categoryDTOConverter = categoryDTOConverter;
		this.categoryServiceImpl = categoryServiceImpl;
	}


	@GetMapping(value="/categories")
	public ResponseEntity<Object> findAll(
			@RequestParam(name="name",required = false) String name){
		
		if(nonNull(name)) {
			return new ResponseEntity<>(categoryDTOConverter.toDTO(categoryServiceImpl.getByName(name)),HttpStatus.OK);
		}

		return new ResponseEntity<>(categoryDTOConverter.toDTOList(categoryServiceImpl.findAll()), HttpStatus.OK);

	}

	@GetMapping(value = "/categories/{id}")
	public ResponseEntity<Object> getById(@PathVariable(name = "id") Long id) {

		return new ResponseEntity<>(categoryDTOConverter.toDTO(categoryServiceImpl.getById(id, true)), HttpStatus.OK);

	}

	@PostMapping(value = "/categories")
	public ResponseEntity<Object> create(@Valid @RequestBody CategoryDTO categoryDTO) {
		return new ResponseEntity<>(
				(categoryDTOConverter.toDTO(categoryServiceImpl.create(categoryDTOConverter.toEntity(categoryDTO)))),
				HttpStatus.CREATED);


	}

	@PatchMapping(value = "/categories/{id}/description")
	public ResponseEntity<Object> patchDescription(@PathVariable(name = "id") Long id,
			@RequestParam(name = "description") String description) {

		Category category = categoryServiceImpl.getById(id, true);

		category.setDescription(description);

		return new ResponseEntity<>(categoryDTOConverter.toDTO(categoryServiceImpl.update(category)), HttpStatus.OK);
	}

	@DeleteMapping(value = "/categories/{id}")
	public ResponseEntity<Object> delete(@PathVariable(name = "id") Long id) {
		categoryServiceImpl.delete(id);
		return new ResponseEntity<>("Deleted Successfull", HttpStatus.OK);
	}

}