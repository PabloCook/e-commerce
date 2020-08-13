package ar.com.gl.shop.product.repository;

import java.util.List;

import ar.com.gl.shop.product.model.Product;

public interface ProductRepository {
	
	public Product saveProduct(Product category);
	
	public Product updateProduct(Product category);

	public List<Product> findAllProduct();

	public void deleteProduct(Product product);
	
	public Product findProductById(Long id);

}
