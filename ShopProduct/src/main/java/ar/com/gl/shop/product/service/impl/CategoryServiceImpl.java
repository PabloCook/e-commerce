package ar.com.gl.shop.product.service.impl;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.gl.shop.product.exceptions.CannotDelete;
import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.repository.CategoryRepository;
import ar.com.gl.shop.product.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository repositoryImpl;

	@Autowired
	public CategoryServiceImpl(CategoryRepository repositoryImpl) {
		this.repositoryImpl = repositoryImpl;
	}

	@Override
	public Category create(Category category) {

		return repositoryImpl.save(category);
	}

	@Override
	public List<Category> findAll() {

		return repositoryImpl.findAll().stream().filter(Category::getEnabled).collect(Collectors.toList());
	}

	@Override
	public List<Category> findAllDisabled() {
		return repositoryImpl.findAll();

	}

	@Override
	public Category getById(Long id, Boolean searchEnable) {
		
		if (isNull(id))	return null;

		Optional<Category> category = repositoryImpl.findById(id);

		if (category.isPresent()) {
			
			if (Boolean.TRUE.equals(searchEnable)) {
				return Boolean.TRUE.equals(category.get().getEnabled()) ? category.get() : null;
				
			}	else return category.get();
			
		}	else throw new ItemNotFound();

	}

	@Override
	public Category update(Category category) {

		return repositoryImpl.save(category);
	}

	@Override
	public Category softDelete(Long id) {
		
		if (isNull(id))	return null;

		Optional<Category> categoryO = repositoryImpl.findById(id);
		
		if (categoryO.isPresent()) {
			Category category = categoryO.get();
			category.setEnabled(!category.getEnabled());
			return repositoryImpl.save(category);
			
		} else	throw new ItemNotFound();
	}

	@Override
	public void delete(Long id) {
		
		if (nonNull(id)) {
			
			Optional<Category> category = repositoryImpl.findById(id);

			if (category.isPresent()) {

				if (category.get().getProducts().isEmpty()) {
					repositoryImpl.delete(category.get());
					
				} else	throw new CannotDelete();
			}
		}
		
	}

	@Override
	public Category getByName(String name) {

		Optional<Category> category = repositoryImpl.findByName(name);
		
		if (category.isPresent()) {
			return category.get();
			
		} else	throw new ItemNotFound();
	}

}
