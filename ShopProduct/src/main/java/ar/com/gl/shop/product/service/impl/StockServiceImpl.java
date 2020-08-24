package ar.com.gl.shop.product.service.impl;

import static java.util.Objects.nonNull;

import java.util.Optional;

import static java.util.Objects.isNull;


import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.gl.shop.product.service.StockService;

@Service
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

		if (isNull(id)) {
			return null;
		}
		Optional<Stock> stock = repositoryImpl.findById(id);

		if (stock.isPresent()) {
			
			if (Boolean.TRUE.equals(searchEnable)) {
				return Boolean.TRUE.equals(stock.get().getEnabled()) ? stock.get() : null;
			} else {
				return stock.get();
			}
		}else {
			throw new ItemNotFound();
		}

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

		Optional<Stock> stockO = repositoryImpl.findById(id);
		if(stockO.isPresent()) {
			Stock stock = stockO.get();
			stock.setEnabled(!stock.getEnabled());
			return repositoryImpl.save(stock);
		}else
			throw new ItemNotFound();
	}

	@Override
	public void delete(Long id) {
		if (nonNull(id)) {

			Optional<Stock> stockO = repositoryImpl.findById(id);
			if(stockO.isPresent())
				repositoryImpl.delete(stockO.get());
			else
				throw new ItemNotFound();
		}
	}
}
