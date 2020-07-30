package ar.com.gl.shop.product.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repository.Repository;

public class RepositoryImpl implements Repository {
	
	private List<Category> listaCategorias = new ArrayList<>();

	private List<Product> listaProductos = new ArrayList<>();
	
	private List<Stock> listaStock = new ArrayList<>();
	

	
	@Override
	public List<Product> getListaProductos() {
		return listaProductos;
	}
	@Override
	public List<Category> getListaCategorias() {
		return listaCategorias;
	}
	@Override
	public List<Stock> getListaStock() {
		return listaStock;
	}


	

}
