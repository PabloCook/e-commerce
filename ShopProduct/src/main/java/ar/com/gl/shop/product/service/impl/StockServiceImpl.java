package ar.com.gl.shop.product.service.impl;

import static java.util.Objects.nonNull;

import java.util.Optional;

import static java.util.Objects.isNull;


import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.gl.shop.product.service.StockService;


public class StockServiceImpl implements StockService {

private StockRepository repositoryImpl;
	
	@Autowired
	public StockServiceImpl(StockRepository repositoryImpl) {
		this.repositoryImpl= repositoryImpl;
	}

	@Override
	public Stock create(Stock stock) {
		return repositoryImpl.save(stock);
		
	}

	@Override
	public Stock getById(Long id, Boolean searchEnable) {
		
		if (isNull(id)){
			return null;
		}
		Optional<Stock> stock = repositoryImpl.findById(id);

		if (stock.isPresent() && searchEnable) {
			return stock.get().getEnabled() ? stock.get() : null;
		}
		return null;
	}

	@Override
	public Stock update(Stock stock) {
		return repositoryImpl.save(stock);
	}

	@Override
	public Stock softDelete(Long id) {
		if (isNull(id)) {
			return null;
		}
		Stock stock = repositoryImpl.findById(id).get();
		stock.setEnabled(!stock.getEnabled());
		return repositoryImpl.save(stock);
		
	}

	@Override
	public void delete(Long id) {
		if (nonNull(id)) {
			repositoryImpl.delete(repositoryImpl.findById(id).get());
		}
	}
}
