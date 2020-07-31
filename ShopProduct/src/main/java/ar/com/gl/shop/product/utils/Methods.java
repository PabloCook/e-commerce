package ar.com.gl.shop.product.utils;

import java.util.Optional;
import java.util.Scanner;

import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.services.CategoryService;
import ar.com.gl.shop.product.services.ProductService;

public class Methods {
	
	private static Scanner scanner = new Scanner(System.in);
	
	private static String regexConfirmacion = "Sí|sí|Si|si|s|S|Yes|yes|y|1|No|no|n|N|0";
	private static String regexAfirmativo = "Sí|sí|Si|si|s|Yes|yes|y|Y|1";
	private static String regexPalabras = "^\\S[\\w|\\s]+\\S$";	

	private static Optional<Product> productOptional = Optional.ofNullable(new Product());
	private static Optional<Category> categoryOptional = Optional.ofNullable(new Category());
	public static String getRegexPalabras() {
		return regexPalabras;
	}

	public static String getRegexConfirmacion() {
		return regexConfirmacion;
	}

	public static String getRegexAfirmativo() {
		return regexAfirmativo;
	}


	/**
	 * Este metodo sirve para validar un input pedido al usuario,
	 * toma un parametro <code>String mensaje</code> para mostrar por consola,
	 * el segundo parametro es un regex.<pre>
	 * 
	 * Sí se da que    <code>input.matches(regex) == true;</code> </pre> 
	 * retorna el input como String
	 * @param mensaje
	 * @param regex
	 * @return String
	 */
	public static String validarInput(String mensaje, String regex) {
		String input;
		
		do {
			System.out.print(mensaje);
			input = scanner.nextLine();
		} while (!input.matches(regex));
		

		
		
		return input;
	}
	
	public static void opcion(CategoryService categoryService) {
		
		String input;
		
		do {
			System.out.println("\n============Category===========\n"
							 + "Elige una opcion:\n"
							 + "1- Crear Categoria\n"
							 + "2- Obtener categoria por Id\n"
							 + "3- Obtener todas las categorias\n"
							 + "4- Actualizar categoria\n"
							 + "5- Eliminar/recuperar Categoria\n"
							 + "6- Salir\n"
							 + "================================");
			input = Methods.validarInput("Ingrese una opción: ", "^[1|2|3|4|5|6]");
			
			switch (input) {
			
			case "6":
				break;
			
			case "1":
				
					
				System.out.println("\nCategorias existentes");
				
				categoryService.findAll(true)
				.stream()
				.forEach(System.out::println);
				
				System.out.println("\n========Nueva Categoria=======");
				Long id;
				
				do {
					id = Long.parseLong(Methods.validarInput("\nInserte un id único: ", "^\\d+"));
					categoryOptional = Optional.ofNullable(categoryService.findOneByiD(id, true));
				} while (categoryOptional.isPresent());
				
				
				
				String name = Methods.validarInput("Ingrese un nombre: ", regexPalabras);	
				
				String description = Methods.validarInput("Ingrese una descripción: ", regexPalabras);
				
				categoryService.create(id, name, description);
				
				System.out.println("\nCategoria creada");
				
				break;
				
			case "2":
				
				//Verifico si la lista esta vacia
				if(categoryService.findAll(true).isEmpty()) {
					System.out.println("\nNo hay categorias");
					break;
				}
				
				//Valida input
				id = Long.parseLong(Methods.validarInput("Inserte un id: ", "^\\d+"));				
				
				Boolean check;
				
				categoryOptional = Optional.ofNullable(categoryService.findOneByiD(id, true));
				
					
					if (!categoryOptional.isPresent()) {
						
						break;
					}
					
					//Verifico si esta eliminado con metodo logico
					check = categoryOptional.get().getEnabled();
					
					if (check) {
						
						System.out.println("\n" + categoryOptional.get());	
						
					}

				break;
				
			case "3":
				
				//Verifico si la lista esta vacia
				if(categoryService.findAll(true).isEmpty()) {
					System.out.println("\nNo hay categorias");
					break;
				}
				
				System.out.println("\n");
				//imprimo todas las habilitadas				
				categoryService.findAll(true)
				.stream()
				.forEach(System.out::println);
			
				break;
				
			case "4":
				
				//Verifico si la lista esta vacia
				if(categoryService.findAll(true).isEmpty()) {
					System.out.println("\nNo hay categorias");
					break;
				}
				
				System.out.println("\n");
				//imprimo todos lo habilitados
				categoryService.findAll(true)
				.stream()			
				.forEach(System.out::println);	
				
				
				
				do {
					id = Long.parseLong(Methods.validarInput("\nInserte un id: ", "^\\d+"));
					categoryOptional = Optional.ofNullable(categoryService.findOneByiD(id, true));
				} while (!categoryOptional.isPresent());
				
				String selectedAtribute;
				
				
				System.out.println("=====================\n"
								 + "¿Que atributo quiere cambiar?\n"
								 + "1- Nombre actual: " + categoryOptional.get().getName() +"\n"
								 + "2- Descripción actual: " + categoryOptional.get().getDescription() +"\n"
								 + "3- Ninguno\n");
				selectedAtribute = Methods.validarInput("Seleccione un numero: ", "^[1|2|3]");
				System.out.println("=====================");
				
				switch (selectedAtribute) {
				
				case "1":					
					
					String newName = Methods.validarInput("Ingrese nuevo nombre: ", Methods.getRegexPalabras());
					
					
					categoryService.updateById(new Category(id, newName, categoryOptional.get().getDescription()));
					
					break;
					
				case "2":
					
					String newDescription = Methods.validarInput("Ingrese nueva descripcion: ", Methods.getRegexPalabras()) ;
					
					categoryService.updateById(new Category(id, categoryOptional.get().getName(), newDescription));					
								
					break;
				}
				break;
				
			case "5":
				
				if(categoryService.findAll(false).isEmpty()) {
					System.out.println("\nNo hay categorias");
					break;
				}
				
				System.out.println("================================"
						         + "\n¿Cual quieres eliminar/recuperar?\n");
				
				categoryService.findAll(false)
				.stream()
				.forEach(System.out::println);
				
				System.out.println("\n");
				
				id = Long.parseLong(Methods.validarInput("Seleccione un id: ", "^\\d+"));
				
				categoryOptional = Optional.ofNullable(categoryService.findOneByiD(id, false));
				
				if (!categoryOptional.isPresent()) {
					
					break;
				}
				
				
				String input1;
				

				System.out.println("=========Eliminar==========\n"
								 + "1- Eliminar/Recuperar\n"
								 + "2- Eliminar de memoria\n"
								 + "3- Salir");
						
				input1 = Methods.validarInput("Seleccione una opción: ", "^[1|2|3]");
				
				switch (input1) {
				
				case "1":				
					
					categoryService.deleteById(categoryOptional.get());
					
					System.out.println("\nCategoria Eliminada/Recuperada");
					
					break;
					
				case "2":
					
					input1 = Methods.validarInput("¿Estas seguro que quieres eliminar permanentemente la siguiente categoria?: \n" 
							+ categoryOptional.get() + "\nIngrese Respuesta: ", Methods.getRegexConfirmacion());

					if (input1.matches(Methods.getRegexAfirmativo())) {

						categoryService.forceDeleteById(categoryOptional.get());

					}					
					
					System.out.println("\nCategoria Eliminada Permanentemente");
					
					break;
				
				case "3":
					
					break;

				}
			
			}
			
		} while (!input.equals("6"));
	}

	public static void opcion(ProductService productService, CategoryService categoryService) {
		
		String input;
		
		do {
			System.out.println("\n============Product===========\n"
							 + "Elige una opcion:\n"
							 + "1- Crear producto\n"
							 + "2- Obtener producto por Id\n"
							 + "3- Obtener todos los productos\n"
							 + "4- Actualizar producto\n"
							 + "5- Eliminar/recuperar producto\n"
							 + "6- Salir\n"
							 + "================================");
			input = Methods.validarInput("Ingrese una opción: ", "^[1|2|3|4|5|6]");
			
			switch (input) {
			
			case "1":
				
				System.out.println("\nProductos existentes");
				productService.findAll(true)
				.stream()
				.forEach(System.out::println);
				
				System.out.println("\n========Nuevo Producto=======");
				Long id;
				do {
					
					id = Long.parseLong(Methods.validarInput("\nInserte un id único: ", "^\\d+"));
					productOptional = Optional.ofNullable(productService.findOneByiD(id, true));					
					
				} while (productOptional.isPresent());
				
				
				String name = Methods.validarInput("Ingrese un nombre: ", regexPalabras);				
				String description = Methods.validarInput("Ingrese una descripción: ", regexPalabras);
				Double price = Double.parseDouble(Methods.validarInput("Ingrese un precio: ", "\\d+"));
				
				categoryService.findAll(true)
				.stream()
				.forEach(System.out::println);
				
				Long categoryId = Long.parseLong(Methods.validarInput("Ingrese el id de la categoria correspondiente: ", "\\d+"));

				categoryOptional = Optional.ofNullable(categoryService.findOneByiD(categoryId, true));	
				
				
				
				if (!categoryOptional.isPresent()) {
					
					break;
				}
				
				productService.create(id, name, description, price, categoryOptional.get());		
				
				productOptional = Optional.ofNullable(productService.findOneByiD(id, true));				
				
				productOptional.get().getStock().setProduct(productOptional.get());
				
				Integer stock = Integer.parseInt(Methods.validarInput("Ingrese stock para este producto: ", "\\d+"));
				
				productOptional.get().getStock().setQuantity(stock);
				
				String location = Methods.validarInput("Ingrese la locacion del producto: ", regexPalabras);
				
				productOptional.get().getStock().setLocationCode(location);
				
				System.out.println("\nProducto creado\n" + productOptional.get());
				break;
				
			case "2":
				
				//Verifico si la lista esta vacia
				if(productService.findAll(true).isEmpty()) {
					System.out.println("\nNo hay productos");
					break;
				}
				
				//Valida input
				id = Long.parseLong(Methods.validarInput("Inserte un id: ", "^\\d+"));				
				
				productOptional = Optional.ofNullable(productService.findOneByiD(id, true));
					
					if (!productOptional.isPresent()) {
						
						break;
					}
					
					//Verifico si esta eliminado con metodo logico
					Boolean check = productOptional.get().getEnabled();
					
					if (check) {
						
						System.out.println("\n" + productOptional.get());	
						
					}
	
				break;
				
			case "3":
				
				//Verifico si la lista esta vacia
				if(productService.findAll(true).isEmpty()) {
					System.out.println("\nNo hay productos");
					break;
				}
				
				System.out.println("\n");
				//imprimo todas las habilitadas				
				productService.findAll(true)
				.stream()
				.forEach(System.out::println);
			
				break;
				
			case "4":
				
				//Verifico si la lista esta vacia
				if(productService.findAll(true).isEmpty()) {
					System.out.println("\nNo hay productos");
					break;
				}
				
				System.out.println("\n");
				//imprimo todos lo habilitados
				productService.findAll(false)
				.stream()			
				.forEach(System.out::println);	
				
				
				
				id = Long.parseLong(Methods.validarInput("\nInserte un id: ", "^\\d+"));	
				
				productOptional = Optional.ofNullable(productService.findOneByiD(id, true));
				
				if (!productOptional.isPresent()) {
					
					break;
				}
				
				String input1;
				
				
				System.out.println("=====================\n"
								 + "¿Que atributo quiere cambiar?\n"
								 + "1- Nombre actual: " + productOptional.get().getName() +"\n"
								 + "2- Descripción actual: " + productOptional.get().getDescription() +"\n"
								 + "3- Precio actual: $" + productOptional.get().getPrice() +"\n"
								 + "4- Stock actual: " + productOptional.get().getStock().getQuantity() + "\n"
								 + "5- Categoria actual: " + productOptional.get().getCategory().getName() + "\n"
								 + "6- Locacion actual: " +productOptional.get().getStock().getLocationCode() + "\n"								 
								 + "7- Ninguno\n");
				input1 = Methods.validarInput("Seleccione un numero: ", "^[1|2|3|4|5|6|7]");
				System.out.println("=====================");
				
				
				switch (input1) {
				
				case "1":
					//controller
					String newName = Methods.validarInput("Ingrese nuevo nombre: ", Methods.getRegexPalabras());
					
					
					
					productService.updateById(new Product(
							
							productOptional.get().getId(),
							newName, 
							productOptional.get().getDescription(),
							productOptional.get().getPrice(),
							productOptional.get().getCategory()							
							
							));
					
					System.out.println("\nCambios Realizados");
					
					break;
					
				case "2":
					//controller
					String newDescription = Methods.validarInput("Ingrese nueva descripcion: ", Methods.getRegexPalabras());
					
							productService.updateById(new Product(
							
							productOptional.get().getId(),
							productOptional.get().getName(), 
							newDescription,
							productOptional.get().getPrice(),
							productOptional.get().getCategory()							
							
							));
					
					System.out.println("\nCambios Realizados");
					break;
					
				case "3":
					//controller
					Double newPrice = Double.parseDouble(Methods.validarInput("Ingrese nuevo precio: ", "\\d+"));
					
					productService.updateById(new Product(
							
					productOptional.get().getId(),
					productOptional.get().getName(), 
					productOptional.get().getDescription(),
					newPrice,
					productOptional.get().getCategory()							
					
					));
			
					System.out.println("\nCambios Realizados");
			
					break;
				case "4":
					
					//controller
					Integer newQuantity = Integer.parseInt(Methods.validarInput("Ingrese nuevo stock: ", "\\d+"));
					
					Product theProduct = new Product(
							
					productOptional.get().getId(),
					productOptional.get().getName(), 
					productOptional.get().getDescription(),
					productOptional.get().getPrice(),
					productOptional.get().getCategory()						
					
					);
					
					theProduct.getStock().setLocationCode(productOptional.get().getStock().getLocationCode());
					theProduct.getStock().setQuantity(newQuantity);
					
					productService.updateById(theProduct);
			
					System.out.println("\nCambios Realizados");
					
					break;
				case "5":
					
					categoryService.findAll(true)
					.stream()
					.forEach(System.out::println);
					
					Long newCategory = Long.parseLong(Methods.validarInput("Seleccion el id de la categoria: ", "\\d+"));

					productService.updateById(new Product(
							
					productOptional.get().getId(),
					productOptional.get().getName(), 
					productOptional.get().getDescription(),
					productOptional.get().getPrice(),
					categoryService.findOneByiD(newCategory, true)							
					
					));
			
					System.out.println("\nCambios Realizados");
					
					break;
					
				case "6" :
					
					//controller
					String newLocation = Methods.validarInput("Ingrese nuevo locacion: ", Methods.getRegexPalabras());
					
					theProduct = new Product(
							
					productOptional.get().getId(),
					productOptional.get().getName(), 
					productOptional.get().getDescription(),
					productOptional.get().getPrice(),
					productOptional.get().getCategory()						
					
					);
					
					theProduct.getStock().setQuantity(productOptional.get().getStock().getQuantity());
					theProduct.getStock().setLocationCode(newLocation);
					
					productService.updateById(theProduct);
			
					System.out.println("\nCambios Realizados");
					
					break;
				}				
				
				break;
				
			case "5":
				
				if(productService.findAll(false).isEmpty()) {
					System.out.println("\nNo hay productos");
					break;
				}
				
				System.out.println("================================"
						         + "\n¿Cual quieres eliminar/recuperar?\n");
				
				productService.findAll(false)
				.stream()
				.forEach(System.out::println);
				
				System.out.println("\n");
				
				id = Long.parseLong(Methods.validarInput("Seleccione un id: ", "^\\d+"));
				
				productService.deleteById(productService.findOneByiD(id, false));
				
				break;
			case "6":
				break;
			}
			
		} while (!input.equals("6"));
	}
}
