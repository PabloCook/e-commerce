package ar.com.gl.shop.product.servicesimpl;

import java.util.ArrayList;
import java.util.List;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.repositoryimpl.CategoryRepositoryImpl;
import ar.com.gl.shop.product.repositoryimpl.StockRepositoryImpl;
import ar.com.gl.shop.product.services.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	CategoryRepositoryImpl repositoryImpl;

	public CategoryServiceImpl() {

		repositoryImpl = CategoryRepositoryImpl.getInstance();
	}

	@Override
	public void create(Long id, String name, String description) {

		Category category = new Category(id, name, description);

		repositoryImpl.saveCategory(category);

	}

	@Override
	public List<Category> findAll() {

		List<Category> categoriesEnabled = new ArrayList<>();

		for (Category category : repositoryImpl.findAllCategory()) {

			if (category.getEnabled()) {
				categoriesEnabled.add(category);
			}
		}

		return categoriesEnabled;
	}

	@Override
	public List<Category> findAllDisabled() {

		return repositoryImpl.findAllCategory();

	}

	@Override
	public Category findById(Long id, Boolean searchEnable) {
		Category category = repositoryImpl.findCategoryById(id);
		// try {
		// if(category == null) {
		// throw new ItemNotFound("No se encontró categoria con este id");
		// }
		if (category != null && searchEnable) {
			category = category.getEnabled() ? category : null;
		}
		// }catch (ItemNotFound e) {
		// System.out.println(e.getMessage());
		// }
		return category;
	}

	@Override
	public Category updateById(Category category) {

		category = findById(category.getId(), true);

		repositoryImpl.updateCategory(category);

		return category;

	}

	@Override
	public void deleteById(Category category) {

		category.setEnabled(!category.getEnabled());

		repositoryImpl.updateCategory(category);

	}

	@Override
	public void forceDeleteById(Category category) {

		repositoryImpl.deleteCategory(category);

	}

}
