package ar.com.gl.shop.product.servicesimpl;

import java.util.ArrayList;
import java.util.List;

import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.repositoryimpl.ProductRepositoryImpl;
import ar.com.gl.shop.product.services.ProductService;

public class ProductServiceImpl implements ProductService {

	private ProductRepositoryImpl repositoryImpl;
	private StockServiceImpl stockService;
	private CategoryServiceImpl categoryService;

	public ProductServiceImpl() {

		repositoryImpl = ProductRepositoryImpl.getInstance();
		stockService = new StockServiceImpl();
		categoryService = new CategoryServiceImpl();
	}

	@Override
	public ProductRepositoryImpl getRepositoryImpl() {

		return repositoryImpl;
	}

	@Override
	public Product create(Product product) {

		Product newProduct = new Product(product.getId(), product.getName(), product.getDescription(),
				product.getPrice(), product.getCategory());

		newProduct.setStock(stockService.create(product.getStock()));

		Product productFind = repositoryImpl.create(newProduct);

		// Si no se crea el producto elimina el stock creado
		if (productFind == null) {
			stockService.delete(newProduct.getStock().getId());
		}

		return productFind;

	}

	@Override
	public List<Product> findAll() {

		List<Product> products = new ArrayList<>();

		List<Product> productsRepo = repositoryImpl.findAll();

		Product product = null;


		for (int i = 0; i < productsRepo.size(); i++) { //

			product = productsRepo.get(i);

			if (product.getEnabled()) {

				products.add(product);
			}

		}

		return products;
	}

	public List<Product> findAllDisabled() {


		List<Product> products = new ArrayList<Product>();

		List<Product> productsRepo = repositoryImpl.findAll();

		Product product = null;

		for (int i = 0; i < productsRepo.size(); i++) { 

			product = productsRepo.get(i);

			if (!product.getEnabled()) {

				products.add(product);
			}
		}
		return products;
	}

	@Override
	public Product getById(Long id, Boolean searchEnable) {

		Product product = repositoryImpl.getById(id);

		if (product != null && searchEnable) {
			product.setStock(stockService.getById(product.getStock().getId(), true));
			product.setCategory(categoryService.getById(product.getCategory().getId(), true));
			product = product.getEnabled() ? product : null;
		}

		return product;
	}

	@Override
	public Product update(Product product) {

		product = repositoryImpl.update(product);

		return product;

	}

	@Override
	public Product softDelete(Product product) {

		product.setEnabled(!product.getEnabled());
		return repositoryImpl.update(product);

	}

	@Override
	public Product delete(Product product) {

		repositoryImpl.delete(product);

		return product;

	}

}
