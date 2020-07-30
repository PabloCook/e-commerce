package ar.com.gl.shop.product.servicesimpl;

import java.util.List;
import java.util.stream.Collectors;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.repositoryimpl.RepositoryImpl;
import ar.com.gl.shop.product.services.CategoryService;
import ar.com.gl.shop.product.utils.Methods;

public class CategoryServiceImpl implements CategoryService {	
	
		
	RepositoryImpl repositoryImpl = new RepositoryImpl();
	
	List<Category> theCategories = repositoryImpl.getListaCategorias();
	
	Category theCategory = new Category();
	
	
	public void agregarPrimerosObjetos() {		
		
		theCategories.add(new Category(1l, "Consumibles", "Para comer"));
		theCategories.add(new Category(2l,"Limpieza", "Para limpiar"));
		theCategories.add(new Category(3l,"Ropa", "Para vestir"));	
		
	}
	

	@Override
	public String create(Long id, String name, String description) {	
		
		
		theCategory = new Category(id,name,description);
		
		//repo.save(thecategory) mari
		
		theCategories.add(theCategory);
		
		//ordernar por id
		theCategories
		.sort((o1,o2)->o1.getId()
		.compareTo(o2.getId()));
		
		return "Categoria creada " + theCategory;
	}
	@Override
	public List<Category> findAll(Boolean bool) {		
		
		if (bool) {
			
			//repo.findAllCategory().str
			
			return theCategories.stream()
					.filter(category->category.getEnabled())
					.collect(Collectors.toList());
		}
		
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

				System.err.println(e.getMessage());
			}
			
			return null;	
	}
	
	

	@Override
	public Category updateById(Long id){
		
		//
		theCategory = findOneByiD(id, true);
		
		String input;
		
	
			System.out.println("=====================\n"
							 + "¿Que atributo quiere cambiar?\n"
							 + "1- Nombre actual: " + theCategory.getName() +"\n"
							 + "2- Descripción actual: " + theCategory.getDescription() +"\n"
							 + "3- Ninguno\n");
			input = Methods.validarInput("Seleccione un numero: ", "^[1|2|3]");
			System.out.println("=====================");
		
		switch (input) {
		
		case "1":
			theCategory.setName(Methods.validarInput("Ingrese nuevo nombre: ", Methods.getRegexPalabras()));
			break;
			
		case "2":
			theCategory.setDescription(Methods.validarInput("Ingrese nueva descripcion: ", Methods.getRegexPalabras()));
			break;
		}
		
		System.err.println("\nCambios Realizados");
		
		return theCategory;		
		
	}
	
	

	@Override
	public void  deleteById(Long id){
		
		theCategory = findOneByiD(id, false);	
		String input;
		

			System.out.println("=========Eliminar==========\n"
							 + "1- Eliminar/Recuperar\n"
							 + "2- Eliminar de memoria\n"
							 + "3- Salir");
					
			input = Methods.validarInput("Seleccione una opción: ", "^[1|2|3]");
			
		
		switch (input) {
		
		case "1":
			
			if (theCategory.getEnabled()) {
				theCategory.setEnabled(false);
			}else {
				theCategory.setEnabled(true);
			}
			
			System.err.println("\nCategoria Eliminada/Recuperada");
			break;
			
		case "2":
	
			input = Methods.validarInput("¿Estas seguro que quieres eliminar permanentemente la siguiente categoria?: \n" 
													+ theCategory + "\nIngrese Respuesta: ", Methods.getRegexConfirmacion());
			
			if (input.matches(Methods.getRegexAfirmativo())) {
				theCategories.remove(theCategory);
				System.err.println("\nCategoria Eliminada Permanentemente");
			}
			
			break;
		
		case "3":
			break;

		}
		
	}

}
