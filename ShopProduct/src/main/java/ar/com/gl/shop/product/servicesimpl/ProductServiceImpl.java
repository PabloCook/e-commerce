package ar.com.gl.shop.product.servicesimpl;

import java.util.List;
import java.util.stream.Collectors;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.repositoryimpl.RepositoryImpl;
import ar.com.gl.shop.product.services.ProductService;

public class ProductServiceImpl implements ProductService {
	
	private RepositoryImpl repositoryImpl;
	private StockServiceImpl stockService;
	
	private Product theProduct;	
	
	
	public ProductServiceImpl() {
		
		repositoryImpl = new RepositoryImpl();
		stockService = new StockServiceImpl();
		
		theProduct = new Product();
	}

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
	public void create(Product product) {
		
		theProduct = new Product(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCategory());		
		
		theProduct.setStock(stockService.create(product.getStock()));
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
	public Product updateById(Product product){
		
		
		Product theProduct = repositoryImpl.findProductById(product.getId());
		
		String newName = product.getName();
		
		theProduct.setName(newName);		

		String newDescription = product.getDescription();
		
		theProduct.setDescription(newDescription);
		
		Double newPrice = product.getPrice();
		
		theProduct.setPrice(newPrice);
		
		Category newCategory = product.getCategory();
		
		theProduct.setCategory(newCategory);
		
		Integer newQuantity = theProduct.getStock().getQuantity();
		
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
		
		repositoryImpl.deleteProduct(theProduct);
				
	}

}
