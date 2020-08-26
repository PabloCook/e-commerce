package ar.com.gl.shop.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.gl.shop.product.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

}
