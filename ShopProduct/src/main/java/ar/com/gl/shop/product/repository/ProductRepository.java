package ar.com.gl.shop.product.repository;

import java.util.List;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Product;

public interface ProductRepository {
	
	public Product create(Product category) throws ItemNotFound;
	
	public Product update(Product category) throws ItemNotFound;

	public List<Product> findAll() throws ItemNotFound;

	public void delete(Product product) throws ItemNotFound;
	
	public Product getById(Long id) throws ItemNotFound;

}
