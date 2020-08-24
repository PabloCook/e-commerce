package ar.com.gl.shop.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.gl.shop.product.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	public Optional<Product> findByName(String name);
	public List<Product> findByCategoryId(Long id);
}
