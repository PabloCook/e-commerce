package ar.com.gl.shop.product.repository;

import java.io.Serializable;
import java.util.List;

import ar.com.gl.shop.product.model.Stock;

public interface StockRepository {
	public Stock save(Stock stock);
	public Stock getById(Long id);
	public void delete(Stock stock);
	public List<Stock> getAll();
	

	
	

	
}
