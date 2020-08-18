package ar.com.gl.shop.product.service;

import ar.com.gl.shop.product.model.Stock;

public interface StockService {

    public Stock create(Stock stock);

    public Stock getById(Long id, Boolean searchEnable);

    public Stock delete(Long id);

    public Stock  softDelete(Long id);

    public Stock update(Stock stock);
}
