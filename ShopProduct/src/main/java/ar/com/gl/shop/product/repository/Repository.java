package ar.com.gl.shop.product.repository;

import java.util.List;

import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;

public interface Repository {

	public Category saveCategory(Category category);
	
	public Category updateCategory(Category category);

	public void deleteCategory(Category category);

	public List<Category> findAllCategory();
	
	public Category findCategoryById(Long id);



}
