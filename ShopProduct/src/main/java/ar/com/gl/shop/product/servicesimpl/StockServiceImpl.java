package ar.com.gl.shop.product.servicesimpl;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
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

	@Override
	public Stock findById(Long id, Boolean searchEnable){	
		Stock stock = repositoryImpl.findStockById(id);	
		try {
			if(stock == null) {
				throw new ItemNotFound("No se encontró categoria con este id");
			}
			if(searchEnable) {
				stock = stock.getEnabled() ? stock : null;
			}			
		}catch (ItemNotFound e) {
			System.out.println(e.getMessage());	
		}
		return stock;		
	}

	@Override
	public void delete(Stock stock){
		repositoryImpl.deleteStock(stock);
	}

	@Override
	public void softDelete(Stock stock){
		
		if (stock.getEnabled()) {
			stock.setEnabled(false);
		}else {
			stock.setEnabled(true);
			}
	}

	@Override
	public Stock update(Stock stock){		

		Stock oldStock = findById(stock.getId(), true);
		repositoryImpl.deleteStock(oldStock);
		
		repositoryImpl.saveStock(stock);
		return stock;		
	}
}
