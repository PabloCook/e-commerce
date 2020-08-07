package ar.com.gl.shop.product.servicesimpl;

import java.util.ArrayList;
import java.util.List;

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
	public List<Product> findAll() {	
		
		List<Product> theProducts = new ArrayList<>();	
		
		int listSize = repositoryImpl.findAllProduct().size();
		
		
		for (int i = 0; i < listSize; i++) {
			
			theProduct = repositoryImpl.findAllProduct().get(i);
			
			if (theProduct.getEnabled()) {
				
				theProducts.add(theProduct);
			}
			
			
		}
		
		/*if (bool) {
			return repositoryImpl.findAllProduct().stream()
					.filter(Product->Product.getEnabled())
					.collect(Collectors.toList());
		}
		
		return repositoryImpl.findAllProduct();*/
		
		return theProducts;
	}
	
	public List<Product> findAllDisabled(){
		
		return repositoryImpl.findAllProduct();
	}
	
	@Override
	public Product findById(Long id, Boolean searchEnable){	
		Product product = repositoryImpl.findProductById(id);	
		try {
			if(product == null) {
				throw new ItemNotFound("No se encontró categoria con este id");
			}
			if(searchEnable) {
				product = product.getEnabled() ? product : null;
			}			
		}catch (ItemNotFound e) {
			System.out.println(e.getMessage());	
		}
		return product;		
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

		theProduct.setStock(stockService.update(product.getStock()));
		
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
