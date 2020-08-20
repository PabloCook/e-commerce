package ar.com.gl.shop.product.utils;

import java.util.ArrayList;
import java.util.List;

import ar.com.gl.shop.product.dto.CategoryDTO;
import ar.com.gl.shop.product.model.Category;

public class CategoryDtoConverter extends Converter{
	
	public CategoryDTO toDto(Category category) {
		CategoryDTO categoryDTO = (CategoryDTO) super.fromTo(category, CategoryDTO.class);
		return categoryDTO;
	}
	
	public Category toEntity(CategoryDTO categoryDTO) {
		Category category = (Category) super.fromTo(categoryDTO, Category.class);
		return category;
	}
	
	public List<CategoryDTO> toDtoList(List<Category> categories) {
		List<CategoryDTO> categoriesDTO = new ArrayList<CategoryDTO>();
		for(Category category : categories) {
			categoriesDTO.add(toDto(category));
		}
		return categoriesDTO;
	}
}
