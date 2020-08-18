package ar.com.gl.shop.product.repository;

import java.util.List;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Category;

public interface CategoryRepository {

	public Category create(Category category) throws ItemNotFound;
	
	public Category update(Category category) throws ItemNotFound;

	public Category delete(Category category) throws ItemNotFound;

	public List<Category> findAll() throws ItemNotFound;
	
	public Category getById(Long id)throws ItemNotFound;



}
