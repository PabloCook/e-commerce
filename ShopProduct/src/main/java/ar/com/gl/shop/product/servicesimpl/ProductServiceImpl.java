package ar.com.gl.shop.product.servicesimpl;

import java.util.List;
import java.util.stream.Collectors;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.repositoryimpl.RepositoryImpl;
import ar.com.gl.shop.product.services.CategoryService;
import ar.com.gl.shop.product.services.ProductService;
import ar.com.gl.shop.product.utils.Methods;

public class ProductServiceImpl implements ProductService {
	
	private RepositoryImpl repositoryImpl = new RepositoryImpl();
	
	private List<Product> theProducts = repositoryImpl.getListaProductos();
	
	private Product theProduct = new Product();
	
	

	public RepositoryImpl getRepositoryImpl() {
		return repositoryImpl;
	}

	public List<Product> getTheProducts() {
		return theProducts;
	}

	public Product getTheProduct() {
		return theProduct;
	}

	@Override
	public void agregarPrimerosObjetos() {
		
		/*theProducts.add(new Category(1l, "Fideos", "Para comer", 50, ));
		theProducts.add(new Category(2l,"Limpieza", "Para limpiar"));
		theProducts.add(new Category(3l,"Ropa", "Para vestir"));*/
		
	}

	@Override
	public String create(Long id, String name, String description, Double price, Category category) {
		
		theProduct = new Product(id, name, description, price, category);		
		
		theProducts.add(theProduct);
		
		//ordernar por id
				theProducts
				.sort((o1,o2)->o1.getId()
				.compareTo(o2.getId()));
		
		return "Categoria creada " + theProduct;
	}
	@Override
	public List<Product> findAll(Boolean bool) {	
		
		
		if (bool) {
			return theProducts.stream()
					.filter(Product->Product.getEnabled())
					.collect(Collectors.toList());
		}
		
		return theProducts;
	}
	

	

	@Override
	public Product findOneByiD(Long id, Boolean bool) {	
		

			try {
				for (Product product : theProducts) {
					
					if (bool && product.getId().equals(id) && product.getEnabled()) {
						
							return product;
						
					}else if(!bool && product.getId().equals(id)) {					

							return product;
					}
					
				}

				throw new ItemNotFound("No se encontró producto con este id");

			} catch (ItemNotFound e) {

				System.out.println(e.getMessage());
			}
			
			return null;	
	}
	
	

	@Override
	public Product updateById(Long id, CategoryService categoryService){
		
		theProduct = findOneByiD(id, true);
		
		String input;
		
	
			System.out.println("=====================\n"
							 + "¿Que atributo quiere cambiar?\n"
							 + "1- Nombre actual: " + theProduct.getName() +"\n"
							 + "2- Descripción actual: " + theProduct.getDescription() +"\n"
							 + "3- Precio actual: $" + theProduct.getPrice() +"\n"
							 + "4- Stock actual: " + theProduct.getStock().getQuantity() + "\n"
							 + "5- Categoria actual: " + theProduct.getCategory().getName() + "\n"
							 + "6- Locacion actual: " +theProduct.getStock().getLocationCode() + "\n"								 
							 + "7- Ninguno\n");
			input = Methods.validarInput("Seleccione un numero: ", "^[1|2|3|4|5|6|7]");
			System.out.println("=====================");
		
		switch (input) {
		
		case "1":
			theProduct
			.setName(Methods.validarInput("Ingrese nuevo nombre: ", Methods.getRegexPalabras()));
			break;
			
		case "2":
			theProduct
			.setDescription(Methods.validarInput("Ingrese nueva descripcion: ", Methods.getRegexPalabras()));
			break;
			
		case "3":
			theProduct
			.setPrice(Double.parseDouble(Methods.validarInput("Ingrese nuevo precio: ", "\\d+")));
			break;
		case "4":
			theProduct
			.getStock()
			.setQuantity(Integer.parseInt(Methods.validarInput("Ingrese nuevo stock: ", "\\d+")));
			break;
		case "5":
			
			categoryService.findAll(true)
			.stream()
			.forEach(System.out::println);
			
			Long categoryId = Long.parseLong(Methods.validarInput("Seleccion el id de la categoria: ", "\\d+"));
			
			theProduct.setCategory(categoryService.findOneByiD(categoryId, true));
			
			break;
		case "6" :
			theProduct
			.getStock()
			.setLocationCode(Methods.validarInput("Inserte nueva locación: ", Methods.getRegexPalabras()));
			break;
		}
			
		System.out.println("\nCambios Realizados");
		
		return theProduct;		
		
	}
	
	

	@Override
	public void  deleteById(Long id){
		
		theProduct = findOneByiD(id, false);	
		String input;
		

			System.out.println("=========Eliminar==========\n"
							 + "1- Eliminar/Recuperar\n"
							 + "2- Eliminar de memoria\n"
							 + "3- Salir");
					
			input = Methods.validarInput("Seleccione una opción: ", "^[1|2|3]");
			
		
		switch (input) {
		
		case "1":
			
			if (theProduct.getEnabled()) {
				theProduct.setEnabled(false);
			}else {
				theProduct.setEnabled(true);
			}
			
			System.out.println("\nCategoria Eliminada/Recuperada");
			break;
			
		case "2":
	
			input = Methods.validarInput("\n¿Estas seguro que quieres eliminar permanentemente el siguiente producto?: \n" 
													+ theProduct + "\n\nIngrese Respuesta: ", Methods.getRegexConfirmacion());
			
			if (input.matches(Methods.getRegexAfirmativo())) {
				theProducts.remove(theProduct);
				System.out.println("\nProducto eliminado permanentemente");
			}
			
			break;
		
		case "3":
			break;

		}
		
	}

}
