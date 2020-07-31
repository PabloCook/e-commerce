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
	public void create(Long id, String name, String description, Double price, Category category) {
		
		theProduct = new Product(id, name, description, price, category);		
		
		theProducts.add(theProduct);
		
		//ordernar por id
		theProducts
		.sort((o1,o2)->o1.getId()
		.compareTo(o2.getId()));
		
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
	public Product updateById(Product product, CategoryService categoryService){
		
		theProduct = findOneByiD(product.getId(), true);
		
		String newName = product.getName();
		
		theProduct.setName(newName);
		
		String newDescription = product.getDescription();
		
		theProduct.setDescription(newDescription);
		
		Double newPrice = product.getPrice();
		
		theProduct.setPrice(newPrice);
		
		Category newCategory = product.getCategory();
		
		theProduct.setCategory(newCategory);
		
		Integer newQuantity = product.getStock().getQuantity();
		
		theProduct.getStock().setQuantity(newQuantity);
		
		String newLocation = product.getStock().getLocationCode();
		
		theProduct.getStock().setLocationCode(newLocation);
		
		return theProduct;		
		
	}
	

	@Override
	public void  deleteById(Product theProduct){
		
		if (theProduct.getEnabled()) {
			theProduct.setEnabled(false);
		}else {
			theProduct.setEnabled(true);
		}

		
	}
	
	@Override
	public void  forceDeleteById(Product theProduct){
		
		theProducts.remove(theProducts.indexOf(theProduct));		
	}

}
