package ar.com.gl.shop.product.servicesimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.repository.Repository;
import ar.com.gl.shop.product.repositoryimpl.RepositoryImpl;
import ar.com.gl.shop.product.services.CategoryService;

public class CategoryServiceImpl implements CategoryService {	
	
	
	Repository repositoryImpl;
	
	Category theCategory;
	
	public CategoryServiceImpl() {
		
		repositoryImpl = new RepositoryImpl();
		
		theCategory = new Category();
	}
	
	//Categorias iniciales
	public void agregarPrimerosObjetos() {		
		
		repositoryImpl.saveCategory(new Category(1l, "Consumibles", "Para comer"));
		repositoryImpl.saveCategory(new Category(2l,"Limpieza", "Para limpiar"));
		repositoryImpl.saveCategory(new Category(3l,"Ropa", "Para vestir"));	
		
	}
	

	@Override
	public void create(Long id, String name, String description) {	
		
		
		theCategory = new Category(id,name,description);
		
		repositoryImpl.saveCategory(theCategory);
		
		//ordernar por id
		repositoryImpl.findAllCategory()
		.sort((o1,o2)->o1.getId()
		.compareTo(o2.getId()));
		
	}
	
	@Override
	public List<Category> findAll() {	
		
		List<Category> theCategoriesEnabled = new ArrayList<>();

		for (Category category : repositoryImpl.findAllCategory()) {
			if (category.getEnabled()) {
				theCategoriesEnabled.add(category);
			}
		}	
		
		/*if (bool) {
					
			return repositoryImpl.findAllCategory().stream()
					.filter(category->category.getEnabled())
					.collect(Collectors.toList());
		}*/
		
		//return repositoryImpl.findAllCategory();
		
		return theCategoriesEnabled;
	}
	
	@Override
	public List<Category> findAllDisabled(){		
		
		return repositoryImpl.findAllCategory();
		
	}

	
	@Override
	public Category findById(Long id, Boolean searchEnable){	
		Category category = repositoryImpl.findCategoryById(id);	
		try {
			if(category == null) {
				throw new ItemNotFound("No se encontró categoria con este id");
			}
			if(searchEnable) {
				category = category.getEnabled() ? category : null;
			}			
		}catch (ItemNotFound e) {
			System.out.println(e.getMessage());	
		}
		return category;		
	}
	
	

	@Override
	public Category updateById(Category category){		

		theCategory = findById(category.getId(), true);
		
		
		//String newName = category.getName();
		
		//theCategory.setName(newName);
		


		repositoryImpl.deleteCategory(theCategory);
		
		repositoryImpl.saveCategory(category);

		
		return theCategory;		
		
	}

	@Override
	public void  deleteById(Category theCategory){
		
		if (theCategory.getEnabled()) {
			theCategory.setEnabled(false);
		}else {
			theCategory.setEnabled(true);
		}
		
	}
	
	@Override
	public void  forceDeleteById(Category theCategory){
		
		repositoryImpl.deleteCategory(theCategory);
			
	}
	




}
