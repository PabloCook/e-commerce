package ar.com.gl.shop.product.repository;


import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Stock;

public interface StockRepository {
	public Stock create(Stock stock) throws ItemNotFound;
	public Stock getById(Long id) throws ItemNotFound;
	public Stock update(Stock stock) throws ItemNotFound;
	public void delete(Stock stock) throws ItemNotFound;
	
}
