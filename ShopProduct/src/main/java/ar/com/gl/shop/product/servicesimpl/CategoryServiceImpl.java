package ar.com.gl.shop.product.servicesimpl;

import java.util.ArrayList;
import java.util.List;

import ar.com.gl.shop.product.exceptions.CannotDelete;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.repositoryimpl.CategoryRepositoryImpl;
import ar.com.gl.shop.product.repositoryimpl.ProductRepositoryImpl;
import ar.com.gl.shop.product.services.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	CategoryRepositoryImpl repositoryImpl;
	
	ProductRepositoryImpl productRepositoryImpl;

	public CategoryServiceImpl() {

		repositoryImpl = CategoryRepositoryImpl.getInstance();
	}

	@Override
	public Category create(String name, String description) {

		Category category = new Category(name, description);

		return repositoryImpl.create(category);

	}

	@Override
	public List<Category> findAll() {

		List<Category> categoriesEnabled = new ArrayList<>();

		for (Category category : repositoryImpl.findAll()) {

			if (category.getEnabled()) {
				categoriesEnabled.add(category);
			}
		}

		return categoriesEnabled;
	}

	@Override
	public List<Category> findAllDisabled() {

		return repositoryImpl.findAll();

	}

	@Override
	public Category getById(Long id, Boolean searchEnable) {
		Category category = repositoryImpl.getById(id);
		if (category != null && searchEnable) {
			category = category.getEnabled() ? category : null;
		}

		return category;
	}

	@Override
	public Category update(Category category) {

		category = getById(category.getId(), true);

		repositoryImpl.update(category);

		return category;

	}

	@Override
	public Category softDelete(Category category) {
		
		try {
			for (Product product : productRepositoryImpl.findAll()) {
				if (product.getCategory().getId().equals(category.getId())) {
					throw new CannotDelete("No se puede eliminar una categoria asociada a un producto");
				}
			}
		} catch (Exception e) {
			
			e.getMessage();
			return null;
		}

		category.setEnabled(!category.getEnabled());

		return repositoryImpl.update(category);

	}

	@Override
	public Category delete(Category category) {
		
		try {
			for (Product product : productRepositoryImpl.findAll()) {
				if (product.getCategory().getId().equals(category.getId())) {
					throw new CannotDelete("No se puede eliminar una categoria asociada a un producto");
				}
			}
		} catch (Exception e) {
			
			e.getMessage();
			return null;
		}

		return repositoryImpl.delete(category);

	}

}
