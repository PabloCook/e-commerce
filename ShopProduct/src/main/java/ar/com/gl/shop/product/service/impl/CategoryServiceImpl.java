package ar.com.gl.shop.product.service.impl;


import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.repository.CategoryRepository;
import ar.com.gl.shop.product.repository.ProductRepository;
import ar.com.gl.shop.product.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepository repositoryImpl;
	@Autowired
	ProductRepository productRepositoryImpl;

	@Override
	public Category create(Category category) {

		return repositoryImpl.save(category);
	}

	@Override
	public List<Category> findAll() {

		return repositoryImpl.findAll()
							 .stream()
						 	 .filter(category -> category.getEnabled())
						 	 .collect(Collectors.toList());
	}

	@Override
	public List<Category> findAllDisabled() {
		return repositoryImpl.findAll();

	}

	@Override
	public Category getById(Long id, Boolean searchEnable) {
		if (isNull(id)) {
			return null;
		}
		Optional<Category> category = repositoryImpl.findById(id);

		if (category.isPresent() && searchEnable) {
			return category.get().getEnabled() ? category.get() : null;
		}
		return null;

	}

	@Override
	public Category update(Category category) {

		return repositoryImpl.save(category);
	}

	@Override
	public Category softDelete(Long id) {
		if (isNull(id)) {
			return null;
		}
		Category category = repositoryImpl.findById(id).get();
		category.setEnabled(!category.getEnabled());
		return repositoryImpl.save(category);

	}

	@Override
	public void delete(Long id) {

		if (nonNull(id)) {
			repositoryImpl.delete(repositoryImpl.findById(id).get());
		}
	}

	@Override
	public Category getByName(String name) {
		return repositoryImpl.findByName(name);
	}

}
