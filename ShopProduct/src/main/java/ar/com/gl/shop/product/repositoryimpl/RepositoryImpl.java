package ar.com.gl.shop.product.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repository.Repository;

public class RepositoryImpl implements Repository {

	private static List<Category> listaCategorias;
	private static List<Product> listaProductos;


	public RepositoryImpl() {
		listaCategorias = new ArrayList<Category>();
		listaProductos = new ArrayList<Product>();

	}

	//Category
	@Override
	public Category saveCategory(Category category) {
		listaCategorias.add(category);
		return category;
	}

	@Override
	public void deleteCategory(Category category) {
		listaCategorias.remove(category);

	}

	@Override
	public List<Category> findAllCategory() {
		return listaCategorias;
	}

	@Override
	public Category findCategoryById(Long id) {
		for (Category category : listaCategorias) {
			if (category.getId().equals(id)) {
				return category;
			}
		}
		return null;
	}
		
	// PRODUCT

	@Override
	public Product saveProduct(Product product) {
		listaProductos.add(product);
		return product;
	}

	@Override
	public List<Product> findAllProduct() {
		return listaProductos;
	}

	@Override
	public void deleteProduct(Product product) {
		listaProductos.remove(product);
	}

	@Override
	public Product findProductById(Long id) {
		for (Product product : listaProductos) {
			if (product.getId().equals(id)) {
				return product;
			}
		}
		return null;
	}

}
