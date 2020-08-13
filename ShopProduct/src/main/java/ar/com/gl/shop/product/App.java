package ar.com.gl.shop.product;

import ar.com.gl.shop.product.service.CategoryService;
import ar.com.gl.shop.product.service.ProductService;
import ar.com.gl.shop.product.service.StockService;
import ar.com.gl.shop.product.service.impl.CategoryServiceImpl;
import ar.com.gl.shop.product.service.impl.ProductServiceImpl;
import ar.com.gl.shop.product.service.impl.StockServiceImpl;
import ar.com.gl.shop.product.utils.Methods;

public class App {
    public static void main( String[] args ){
    	
    	
    	
    	String input;
    	
    	CategoryService categoryService = new CategoryServiceImpl();
    	ProductService productService = new ProductServiceImpl();
    	StockService stockService = new StockServiceImpl();
    	
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
			case "1": //Categorias
				
	    		try {
					Methods.opcion(categoryService);
				} catch (Exception e) {
					
					System.out.println("\nHa ocurrido un error\n" + e.getMessage() + "\n");
				}	    		
	    		
				break;
			case "2": //Productos
				
				try {
					Methods.opcion(productService, categoryService, stockService);
				} catch (Exception e) {
					
					System.out.println("\nHa ocurrido un error\n" + e.getMessage() + "\n");
				}
				
				break;
			}
		} while (!input.equals("3"));
    	
    	      
    }
		
}

