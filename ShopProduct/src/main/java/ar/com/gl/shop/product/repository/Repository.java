package ar.com.gl.shop.product.repository;

import java.util.List;

import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;

public interface Repository {

	public Category saveCategory(Category category);

	public void deleteCategory(Category category);

	public List<Category> findAllCategory();
	
	public Category findCategoryById(Long id);

	public Stock saveStock(Stock category);
	
	public void deleteStock(Stock stock);

	public List<Stock> findAllStock();

	public Product saveProduct(Product category);

	public List<Product> findAllProduct();

	public void deleteProduct(Product product);


}
