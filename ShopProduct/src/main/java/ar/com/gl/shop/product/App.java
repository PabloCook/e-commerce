package ar.com.gl.shop.product;

import ar.com.gl.shop.product.services.CategoryService;
import ar.com.gl.shop.product.services.ProductService;
import ar.com.gl.shop.product.servicesimpl.CategoryServiceImpl;
import ar.com.gl.shop.product.servicesimpl.ProductServiceImpl;
import ar.com.gl.shop.product.utils.Methods;

public class App {
    public static void main( String[] args ){
    	
    	
    	
    	String input;
    	
    	CategoryService categoryService = new CategoryServiceImpl();
    	ProductService productService = new ProductServiceImpl();
    	
    	categoryService.agregarPrimerosObjetos();
    	
    	do {
    		System.out.println("========Pagina de inicio========\n"
    				+ "Elige una opcion:\n"
    				+ "1- Categorias\n"
    				+ "2- Productos\n"
    				+ "3- Salir\n"
    				+ "================================");
			input = Methods.validarInput("Inserte un numero: ", "^[1|2|3]");
			
			switch (input) {
			case "1":
				
	    		try {
					Methods.opcion(categoryService);
				} catch (Exception e) {
					
					System.out.println("\nHa ocurrido un error\n" + e.getMessage() + "\n");
				}	    		
	    		
				break;
			case "2":
				
				try {
					Methods.opcion(productService, categoryService);
				} catch (Exception e) {
					
					System.out.println("\nHa ocurrido un error\n" + e.getMessage() + "\n");
				}
				
				break;
			}
		} while (!input.equals("3"));
    	
    	      
    }
		
}

