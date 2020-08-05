package ar.com.gl.shop.product.servicesimpl;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repository.StockRepository;
import ar.com.gl.shop.product.repositoryimpl.StockRepositoryImpl;
import ar.com.gl.shop.product.services.StockService;

public class StockServiceImpl implements StockService {
	
    private StockRepository repositoryImpl;
	
	private Stock theStock;	
	
	
	public StockServiceImpl() {
		
		repositoryImpl = StockRepositoryImpl.getInstance();
		theStock = new Stock();
	}
	
	@Override
	public Stock create(Stock stock){
		return repositoryImpl.save(new Stock(stock.getQuantity(), stock.getLocationCode()));
    }

	@Override
	public Stock findById(Long id, Boolean searchEnable){	
		Stock stock = repositoryImpl.getById(id);	
		try {
			if(stock == null) {
				throw new ItemNotFound("No se encontró stock con este id");
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
		repositoryImpl.delete(stock);
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
		repositoryImpl.delete(oldStock);
		
		repositoryImpl.save(stock);
		return stock;		
	}
}
