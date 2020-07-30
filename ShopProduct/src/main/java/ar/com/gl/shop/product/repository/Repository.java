package ar.com.gl.shop.product.repository;

import java.util.List;

import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;

public interface Repository {

	public List<Product> getListaProductos();
	public List<Category> getListaCategorias();
	public List<Stock> getListaStock();

}
