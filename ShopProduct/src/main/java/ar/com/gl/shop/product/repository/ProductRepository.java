package ar.com.gl.shop.product.repository;

import java.util.List;

import ar.com.gl.shop.product.model.Product;

public interface ProductRepository {
	
	public Product create(Product category);
	
	public Product update(Product category);

	public List<Product> findAll();

	public void delete(Product product);
	
	public Product getById(Long id);

}
