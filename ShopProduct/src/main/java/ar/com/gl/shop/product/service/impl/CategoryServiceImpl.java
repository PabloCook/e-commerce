package ar.com.gl.shop.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.nonNull;
import static java.util.Objects.isNull;

import ar.com.gl.shop.product.exceptions.CannotDelete;
import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.repository.impl.CategoryRepositoryImpl;
import ar.com.gl.shop.product.repository.impl.ProductRepositoryImpl;
import ar.com.gl.shop.product.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	CategoryRepositoryImpl repositoryImpl;
	
	ProductRepositoryImpl productRepositoryImpl;

	public CategoryServiceImpl() {

		repositoryImpl = CategoryRepositoryImpl.getInstance();
	}

	@Override
	public Category create(String name, String description) {

		Category category = new Category(name, description);
		try {
			return repositoryImpl.create(category);
		} catch (ItemNotFound e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public List<Category> findAll() {

		List<Category> categoriesEnabled = new ArrayList<>();
		try {
			for (Category category : repositoryImpl.findAll()) {
				if (category.getEnabled()) {
					categoriesEnabled.add(category);
				}
			}
		} catch (ItemNotFound e) {
			e.printStackTrace();
		}
		return categoriesEnabled;
	}

	@Override
	public List<Category> findAllDisabled() {

		try {
			return repositoryImpl.findAll();
		} catch (ItemNotFound e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Category getById(Long id, Boolean searchEnable) {
		if(isNull(id)){
			return null;
		}
		Category category;
		try {
			category = repositoryImpl.getById(id);
			
		} catch (ItemNotFound e) {
			e.printStackTrace();
			return null;
		}
		if (nonNull(category) && searchEnable) {
			category = category.getEnabled() ? category : null;
		}
		
		return category;
	}

	@Override
	public Category update(Category category) {

		try {
			repositoryImpl.update(category);
			return category;
		} catch (ItemNotFound e) {
			e.printStackTrace();
			return null;
		}

		

	}

	@Override
	public Category softDelete(Long id) {
		if(isNull(id)){
			return null;
		}
		productRepositoryImpl = ProductRepositoryImpl.getInstance();
		
		try {
			for (Product product : productRepositoryImpl.findAll()) {
				if (product.getCategory().getId().equals(repositoryImpl.getById(id).getId())) {
					throw new CannotDelete("No se puede eliminar una categoria asociada a un producto");
				}
			}
		} catch (CannotDelete e) {
			e.getMessage();
			return null;
		}
		catch (ItemNotFound e) {
			e.getMessage();
			return null;
		}
		try {
			repositoryImpl.getById(id).setEnabled(!repositoryImpl.getById(id).getEnabled());
			return repositoryImpl.update(repositoryImpl.getById(id));
		} catch (ItemNotFound e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Category delete(Long id) {
		if(isNull(id)){
			return null;
		}
		productRepositoryImpl = ProductRepositoryImpl.getInstance();
		try {
			for (Product product : productRepositoryImpl.findAll()) {
				if (product.getCategory().getId().equals(repositoryImpl.getById(id).getId())) {
					throw new CannotDelete("No se puede eliminar una categoria asociada a un producto");
				}
			}
		} catch (Exception e) {
			
			e.getMessage();
			return null;
		}

		try {
			return repositoryImpl.delete(repositoryImpl.getById(id));
		} catch (ItemNotFound e) {
			e.printStackTrace();
			return null;
		}

	}

}
