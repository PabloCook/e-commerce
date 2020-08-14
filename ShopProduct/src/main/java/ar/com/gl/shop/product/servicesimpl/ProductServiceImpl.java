package ar.com.gl.shop.product.servicesimpl;

import java.util.ArrayList;
import java.util.List;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.repositoryimpl.CategoryRepositoryImpl;
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
	public void create(Product product) {

		Product newProduct = new Product(product.getId(), product.getName(), product.getDescription(),
				product.getPrice(), product.getCategory());

		newProduct.setStock(stockService.create(product.getStock()));

		Product productFind = repositoryImpl.create(newProduct);

		// Si no se crea el producto elimina el stock creado
		if (productFind == null) {
			stockService.delete(newProduct.getStock().getId());
		}

	}

	@Override
	public List<Product> findAll() {

		List<Product> products = new ArrayList<>();

		Product product = null;

		for (int i = 0; i < repositoryImpl.findAll().size(); i++) { //

			product = repositoryImpl.findAll().get(i);

			if (product.getEnabled()) {

				products.add(product);
			}

		}

		/*
		 * if (bool) { return repositoryImpl.findAllProduct().stream()
		 * .filter(Product->Product.getEnabled()) .collect(Collectors.toList()); }
		 * 
		 * return repositoryImpl.findAllProduct();
		 */

		return products;
	}

	public List<Product> findAllDisabled() {

		return repositoryImpl.findAll();
	}

	@Override
	public Product getById(Long id, Boolean searchEnable) {
		Product product = repositoryImpl.getBydId(id);

		// try {
		// if(product == null) {
		// throw new ItemNotFound("No se encontró producto con este id");
		// }
		if (product != null && searchEnable) {
			product.setStock(stockService.getById(product.getStock().getId(), true));
			product.setCategory(categoryService.getById(product.getCategory().getId(), true));
			product = product.getEnabled() ? product : null;
		}
		// }catch (ItemNotFound e) {
		// System.out.println(e.getMessage());
		// }
		return product;
	}

	@Override
	public Product update(Product product) {

		product = repositoryImpl.update(product);

		return product;

	}

	@Override
	public void softDelete(Product product) {

		product.setEnabled(!product.getEnabled());
		repositoryImpl.update(product);

	}

	@Override
	public void delete(Product product) {

		repositoryImpl.delete(product);

	}

}
