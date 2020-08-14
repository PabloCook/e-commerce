package ar.com.gl.shop.product.service.impl;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repository.StockRepository;
import ar.com.gl.shop.product.repository.impl.StockRepositoryImpl;
import ar.com.gl.shop.product.service.StockService;

public class StockServiceImpl implements StockService {

	private StockRepositoryImpl repositoryImpl;

	private Stock theStock;

	public StockServiceImpl() {

		repositoryImpl = StockRepositoryImpl.getInstance();
		theStock = new Stock();
	}

	@Override
	public Stock create(Stock stock) {
		Stock stockFind = repositoryImpl.create(new Stock(stock.getQuantity(), stock.getLocationCode()));
		return stockFind;
	}

	@Override
	public Stock getById(Long id, Boolean searchEnable) {
		Stock stock = repositoryImpl.getById(id);
		//try {
			//if (stock == null) {
			//	throw new ItemNotFound("No se encontró stock con este id");
			//}
			if (stock != null && searchEnable) {
				stock = stock.getEnabled() ? stock : null;
			}
	//	} catch (ItemNotFound e) {
		//	System.out.println(e.getMessage());
		//}
		return stock;
	}

	@Override
	public void delete(Long idStock) {

		repositoryImpl.delete(repositoryImpl.getById(idStock));
	}

	@Override
	public void softDelete(Stock stock) {

		stock.setEnabled(!stock.getEnabled());
		repositoryImpl.update(stock);
	}

	@Override
	public Stock update(Stock stock) {

		stock = repositoryImpl.update(stock);

		return stock;
	}
}
