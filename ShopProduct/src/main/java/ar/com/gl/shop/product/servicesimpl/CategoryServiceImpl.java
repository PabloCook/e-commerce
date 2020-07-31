package ar.com.gl.shop.product.servicesimpl;

import java.util.List;
import java.util.stream.Collectors;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.repositoryimpl.RepositoryImpl;
import ar.com.gl.shop.product.services.CategoryService;
import ar.com.gl.shop.product.repository.Repository;

public class CategoryServiceImpl implements CategoryService {	
	
		
	Repository repositoryImpl = new RepositoryImpl();
	
	List<Category> theCategories = repositoryImpl.getListaCategorias();
	
	Category theCategory = new Category();
	
	
	//Categorias iniciales
	public void agregarPrimerosObjetos() {		
		
		theCategories.add(new Category(1l, "Consumibles", "Para comer"));
		theCategories.add(new Category(2l,"Limpieza", "Para limpiar"));
		theCategories.add(new Category(3l,"Ropa", "Para vestir"));	
		
	}
	

	@Override
	public void create(Long id, String name, String description) {	
		
		
		theCategory = new Category(id,name,description);
		
		//repo.save(thecategory) mari		
		theCategories.add(theCategory);
		
		//ordernar por id
		theCategories
		.sort((o1,o2)->o1.getId()
		.compareTo(o2.getId()));
		
	}
	
	@Override
	public List<Category> findAll(Boolean bool) {		
		
		if (bool) {
			
			//repo.findAllCategory().str			
			return theCategories.stream()
					.filter(category->category.getEnabled())
					.collect(Collectors.toList());
		}
		//repo.findAllCategory
		return theCategories;
	}

	

	@Override
	public Category findOneByiD(Long id, Boolean bool){	
		

			try {                        //repo.findallcategory()
				for (Category category : theCategories) {
					
					if (bool && category.getId().equals(id) && category.getEnabled()) {
						
							return category;
						
					}else if(!bool && category.getId().equals(id)) {					

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
		
		
		String newName = category.getName();
		
		theCategory.setName(newName);
		
		String newDescription = category.getDescription();
		
		theCategory.setDescription(newDescription);		
		
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
		
		theCategories.remove(theCategories.indexOf(theCategory));		
	}



}
