package ar.com.gl.shop.product.servicesimpl;

import java.util.List;
import java.util.stream.Collectors;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.repositoryimpl.RepositoryImpl;
import ar.com.gl.shop.product.services.CategoryService;
import ar.com.gl.shop.product.repository.Repository;

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
	public List<Category> findAll(Boolean bool) {		
		
		if (bool) {
					
			return repositoryImpl.findAllCategory().stream()
					.filter(category->category.getEnabled())
					.collect(Collectors.toList());
		}
		return repositoryImpl.findAllCategory();
	}

	

	@Override
	public Category findOneByiD(Long id, Boolean bool){	
		

			try {                        
				for (Category category : repositoryImpl.findAllCategory()) {
					
					if (bool && category.getId().equals(id) && category.getEnabled()) {
						
							return category;
						
					}else if(!bool && repositoryImpl.findCategoryById(id).getId().equals(category.getId())) {					

							return category;
					}
					
				}

				throw new ItemNotFound("No se encontró categoria con este id");

			} catch (ItemNotFound e) {

				System.out.println(e.getMessage());
			}
			
			return null;	
	}
	
	

	@Override
	public Category updateById(Category category){		

		theCategory = findOneByiD(category.getId(), true);
		
		
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
