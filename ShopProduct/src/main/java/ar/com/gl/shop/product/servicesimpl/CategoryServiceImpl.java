package ar.com.gl.shop.product.servicesimpl;

import java.util.List;
import java.util.stream.Collectors;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.repositoryimpl.RepositoryImpl;
import ar.com.gl.shop.product.services.CategoryService;
import ar.com.gl.shop.product.utils.Methods;
import ar.com.gl.shop.product.repository.Repository;

public class CategoryServiceImpl implements CategoryService {	
	
		
	Repository repositoryImpl = new RepositoryImpl();
		
	Category theCategory = new Category();
	
	
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
					
					if (bool && repositoryImpl.findCategoryById(id).equals(category) && category.getEnabled()) {
						
							return category;
						
					}else if(!bool && repositoryImpl.findCategoryById(id).equals(category)) {					

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
		
		//controller
		String selectedAtribute = Methods.updateSelectedAtribute(theCategory);
		
		switch (selectedAtribute) {
		
		case "1":
			//controller
			String newName = Methods.validarInput("Ingrese nuevo nombre: ", Methods.getRegexPalabras());
			
			theCategory.setName(newName);
			break;
			
		case "2":
			//controller
			String newDescription = Methods.validarInput("Ingrese nueva descripcion: ", Methods.getRegexPalabras()) ;
			
			theCategory.setDescription(newDescription);
			break;
		}
		
		System.out.println("\nCambios Realizados");
		repositoryImpl.deleteCategory(category);
		repositoryImpl.saveCategory(theCategory);
		
		return theCategory;		
		
	}

	@Override
	public void  deleteById(Long id){
		
		theCategory = findOneByiD(id, false);	
		
		//controller
		String input = Methods.typeOfDelete(theCategory);
			
		
		switch (input) {
		
		case "1":
			
			if (theCategory.getEnabled()) {
				theCategory.setEnabled(false);
			}else {
				theCategory.setEnabled(true);
			}
			
			System.out.println("\nCategoria Eliminada/Recuperada");
			
			break;
			
		case "2":
			
			
			repositoryImpl.deleteCategory(theCategory);
			
			System.out.println("\nCategoria Eliminada Permanentemente");
			
			break;
		
		case "3":
			
			break;

		}
		
	}



}
