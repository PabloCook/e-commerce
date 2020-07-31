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
	private static List<Stock> listaStock;
	
	public RepositoryImpl() {
		
		listaCategorias = new ArrayList<>();
		
		listaProductos = new ArrayList<>();
		
		listaStock = new ArrayList<>();
	}
	
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
