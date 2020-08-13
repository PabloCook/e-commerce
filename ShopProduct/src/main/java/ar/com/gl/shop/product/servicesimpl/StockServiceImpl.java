package ar.com.gl.shop.product.servicesimpl;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repository.StockRepository;
import ar.com.gl.shop.product.repositoryimpl.StockRepositoryImpl;
import ar.com.gl.shop.product.services.StockService;

public class StockServiceImpl implements StockService {

	private StockRepositoryImpl repositoryImpl;

	private Stock theStock;

	public StockServiceImpl() {

		repositoryImpl = StockRepositoryImpl.getInstance();
		theStock = new Stock();
	}

	@Override
	public Stock create(Stock stock) {
		Stock stockFind = repositoryImpl.save(new Stock(stock.getQuantity(), stock.getLocationCode()));
		return stockFind;
	}

	@Override
	public Stock findById(Long id, Boolean searchEnable) {
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
		repositoryImpl.updateStock(stock);
	}

	@Override
	public Stock update(Stock stock) {

		stock = repositoryImpl.updateStock(stock);

		return stock;
	}
}
