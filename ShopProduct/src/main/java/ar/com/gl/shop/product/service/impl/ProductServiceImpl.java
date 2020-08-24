package ar.com.gl.shop.product.service.impl;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.repository.ProductRepository;
import ar.com.gl.shop.product.service.CategoryService;
import ar.com.gl.shop.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository repositoryImpl;

	private CategoryService categoryService;

	@Autowired
	public ProductServiceImpl(ProductRepository repositoryImpl, CategoryService categoryService) {
		this.repositoryImpl = repositoryImpl;
		this.categoryService = categoryService;
	}

	@Override
	public Product create(Product product) {
		return repositoryImpl.save(product);
	}

	@Override
	public List<Product> findAll() {

		return repositoryImpl.findAll().stream().filter(product -> product.getEnabled()).collect(Collectors.toList());

	}

	@Override
	public List<Product> findAllDisabled() {

		return repositoryImpl.findAll();
	}

	@Override
	public Product getById(Long id, Boolean searchEnable) {

		if (isNull(id)) {
			return null;
		}

		Optional<Product> product = repositoryImpl.findById(id);

		if (product.isPresent()) {
			if (searchEnable) {
				return product.get().getEnabled() ? product.get() : null;
			} else {
				return product.get();
			}
		}else
		{
			throw new ItemNotFound();
		}
	}

	@Override
	public Product update(Product product) {

		product.setCategory(categoryService.getById(product.getCategory().getId(), true));
		return repositoryImpl.save(product);
	}

	@Override
	public Product softDelete(Long id) {

		if (isNull(id)) {
			return null;
		}

		Optional<Product> productO = repositoryImpl.findById(id);
		if(productO.isPresent()) {
			Product product = productO.get();
			product.setEnabled(!product.getEnabled());
			return repositoryImpl.save(product);
		}else {
			throw new ItemNotFound();
		}
	}

	@Override
	public void delete(Long id) {
		if (nonNull(id)) {
			Optional<Product> productO = repositoryImpl.findById(id);
			if(productO.isPresent())
				repositoryImpl.delete(productO.get());
			else
				throw new ItemNotFound();
		}

	}

	@Override
	public Product getByName(String name) {
		Optional<Product> product = repositoryImpl.findByName(name);
		if(product.isPresent()) {
			return product.get();
			}else {
			throw new ItemNotFound();
			}
		}
	
	@Override
	public List<Product> findCategoryById(Long id) {
		
		return 	findAll()
				.stream()
				.filter(p->p.getCategory().getId().equals(id))
				.collect(Collectors.toList());
	}

}
