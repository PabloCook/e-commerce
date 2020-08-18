package ar.com.gl.shop.product.service.impl;

import static java.util.Objects.nonNull;
import static java.util.Objects.isNull;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repository.impl.StockRepositoryImpl;
import ar.com.gl.shop.product.service.StockService;

public class StockServiceImpl implements StockService {

	private StockRepositoryImpl repositoryImpl;

	public StockServiceImpl() {

		repositoryImpl = StockRepositoryImpl.getInstance();
	}

	@Override
	public Stock create(Stock stock) {
		Stock stockFind;
		try {
			stockFind = repositoryImpl.create(new Stock(stock.getQuantity(), stock.getLocationCode()));
			return stockFind;
		} catch (ItemNotFound e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Stock getById(Long id, Boolean searchEnable) {

		if (isNull(id)) {
			return null;
		}

		Stock stock = null;
		try {
			stock = repositoryImpl.getById(id);
		} catch (ItemNotFound e) {
			e.printStackTrace();
		}
		if (nonNull(stock) && searchEnable) {
			stock = stock.getEnabled() ? stock : null;
		}
		return stock;
	}

	@Override
	public Stock delete(Long id) {
		if(isNull(id)){
			return null;
		}
		try {
			Stock stock = repositoryImpl.getById(id);
			repositoryImpl.delete(repositoryImpl.getById(id));
			return stock;
		} catch (ItemNotFound e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Stock softDelete(Long id) {
		if (isNull(id)) {
			return null;
		}
		try {
			repositoryImpl.getById(id).setEnabled(!repositoryImpl.getById(id).getEnabled());
			repositoryImpl.update(repositoryImpl.getById(id));
			return repositoryImpl.getById(id);
		} catch (ItemNotFound e) {
			
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Stock update(Stock stock) {
		try {
			return repositoryImpl.update(stock);
		} catch (ItemNotFound e) {
			e.printStackTrace();
			return null;
		}
	}
}
