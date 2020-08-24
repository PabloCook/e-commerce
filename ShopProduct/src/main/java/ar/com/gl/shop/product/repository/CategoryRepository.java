package ar.com.gl.shop.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.gl.shop.product.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Optional<Category> findByName(String name);
}
