package ar.com.gl.shop.product.servicesimpl;

import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repositoryimpl.RepositoryImpl;
import ar.com.gl.shop.product.services.StockService;

public class StockServiceImpl implements StockService {
	
    private RepositoryImpl repositoryImpl;
	
	private Stock theStock;	
	
	
	public StockServiceImpl() {
		
		repositoryImpl = new RepositoryImpl();

		
		theStock = new Stock();
	}
	
	@Override
	public Stock create(Stock stock){

        theStock = new Stock(stock.getQuantity(), stock.getLocationCode());		
		
		return repositoryImpl.saveStock(theStock);
    }

}
