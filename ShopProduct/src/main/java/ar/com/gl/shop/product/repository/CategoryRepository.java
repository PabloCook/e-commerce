package ar.com.gl.shop.product.repository;

import java.util.List;

import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;

public interface CategoryRepository {

	public Category create(Category category);
	
	public Category update(Category category);

	public Category delete(Category category);

	public List<Category> findAll();
	
	public Category getById(Long id);



}
