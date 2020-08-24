package ar.com.gl.shop.product.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.com.gl.shop.product.dto.CategoryDTO;
import ar.com.gl.shop.product.model.Category;

@Component
public class CategoryDTOConverter extends Converter{
	
	public CategoryDTO toDTO(Category category) {
		
		return (CategoryDTO) super.fromTo(category, CategoryDTO.class);
	}
	
	public Category toEntity(CategoryDTO categoryDTO) {
		
		return (Category) super.fromTo(categoryDTO, Category.class);

	}
	
	public List<CategoryDTO> toDTOList(List<Category> categories) {
		List<CategoryDTO> categoriesDTO = new ArrayList<>();
		for(Category category : categories) {
			categoriesDTO.add(toDTO(category));
		}
		return categoriesDTO;
	}
}
