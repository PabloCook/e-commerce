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
		
	private Product theProduct = new Product();
	
	

	public RepositoryImpl getRepositoryImpl() {
		return repositoryImpl;
	}

	public List<Product> getTheProducts() {
		return repositoryImpl.findAllProduct();
	}

	public Product getTheProduct() {
		return theProduct;
	}

	@Override
	public void create(Long id, String name, String description, Double price, Category category) {
		
		theProduct = new Product(id, name, description, price, category);		
		
		repositoryImpl.saveProduct(theProduct);
		
		//ordernar por id
		repositoryImpl.findAllProduct()
		.sort((o1,o2)->o1.getId()
		.compareTo(o2.getId()));
		
	}
	@Override
	public List<Product> findAll(Boolean bool) {	
		
		
		if (bool) {
			return repositoryImpl.findAllProduct().stream()
					.filter(Product->Product.getEnabled())
					.collect(Collectors.toList());
		}
		
		return repositoryImpl.findAllProduct();
	}
	

	

	@Override
	public Product findOneByiD(Long id, Boolean bool) {	
		

			try {
				for (Product product : repositoryImpl.findAllProduct()) {
					
					if (bool && product.equals(repositoryImpl.findProductById(id)) && product.getEnabled()) {
						
							return product;
						
					}else if(!bool && product.equals(repositoryImpl.findProductById(id))) {					

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
	public Product updateById(Product product, CategoryService categoryService){
		
		theProduct = findOneByiD(id, true);
		
		//controller
		String input = Methods.selectedAttribute(theProduct, categoryService);
		
		switch (input) {
		
		case "1":
			//controller
			String newName = Methods.validarInput("Ingrese nuevo nombre: ", Methods.getRegexPalabras());
			
			theProduct.setName(newName);
			break;
			
		case "2":
			//controller
			String description = Methods.validarInput("Ingrese nueva descripcion: ", Methods.getRegexPalabras());
			
			theProduct.setDescription(description);
			break;
			
		case "3":
			//controller
			Double newPrice = Double.parseDouble(Methods.validarInput("Ingrese nuevo precio: ", "\\d+"));
			
			theProduct.setPrice(newPrice);
			break;
		case "4":
			
			//controller
			Integer newQuantity = Integer.parseInt(Methods.validarInput("Ingrese nuevo stock: ", "\\d+"));
			
			theProduct.getStock().setQuantity(newQuantity);
			break;
		case "5":
			
			//controller
			Long categoryId = Long.parseLong(Methods.validarInput("Seleccion el id de la categoria: ", "\\d+"));
			
			theProduct.setCategory(categoryService.findOneByiD(categoryId, true));
			
			break;
			
		case "6" :
			
			//controller
			String newLocation = Methods.validarInput("Inserte nueva locación: ", Methods.getRegexPalabras());
			
			theProduct.getStock().setLocationCode(newLocation);
			
			break;
		}
			
		System.out.println("\nCambios Realizados");
		repositoryImpl.deleteProduct(product);
		repositoryImpl.saveProduct(theProduct);
		
		return theProduct;		
		
	}
	

	@Override
	public void  deleteById(Long id){
		
		theProduct = findOneByiD(id, false);	

		//controller
		String input = Methods.typeOfDelete(theProduct);
			
		
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

			repositoryImpl.deleteProduct(theProduct);
			
			break;
		
		case "3":
			break;

		}
		
	}

}
