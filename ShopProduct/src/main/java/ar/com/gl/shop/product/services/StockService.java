package ar.com.gl.shop.product.services;

import ar.com.gl.shop.product.model.Stock;

public interface StockService {

    public Stock create(Stock stock);

    public Stock findById(Long id, Boolean searchEnable);

    public void delete(Long id);

    public void  softDelete(Stock stock);

    public Stock update(Stock stock);
}
