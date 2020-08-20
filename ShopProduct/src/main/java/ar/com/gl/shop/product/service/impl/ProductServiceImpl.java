package ar.com.gl.shop.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.repository.impl.ProductRepositoryImpl;
import ar.com.gl.shop.product.service.ProductService;

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

		Product newProduct = new Product(product.getName(), product.getDescription(),
				product.getPrice(), product.getCategory());
		
		Product productFind = null;
		
		newProduct.setStock(stockService.create(product.getStock()));

		try {
			productFind = repositoryImpl.create(newProduct);
			stockService.delete(newProduct.getStock().getId());
		} catch (ItemNotFound e) {
			System.out.println(e.getMessage());
		}
		return productFind;
	}

	@Override
	public List<Product> findAll() {

		List<Product> products = new ArrayList<>();

		List<Product> productsRepo = new ArrayList<>();

		try {
			productsRepo = repositoryImpl.findAll();
		} catch (ItemNotFound e) {
			e.printStackTrace();
			return null;
		}

		for (int i = 0; i < productsRepo.size(); i++) { 

			if (productsRepo.get(i).getEnabled()) {

				products.add(productsRepo.get(i));
			}

		}

		return products;
	}

	public List<Product> findAllDisabled() {
		
		List<Product> products = new ArrayList<Product>();
		List<Product> productsRepo = new ArrayList<Product>();
		try {
			productsRepo = repositoryImpl.findAll();
		} catch (ItemNotFound e) {
			e.printStackTrace();
		}
		for (int i = 0; i < productsRepo.size(); i++) { 
			if (!productsRepo.get(i).getEnabled()) {
				products.add(productsRepo.get(i));
			}
		}
		return products;
	}

	@Override
	public Product getById(Long id, Boolean searchEnable) {
		if(isNull(id)){
			return null;
		}
		Product product = null;
		try {
			product = repositoryImpl.getById(id);
		} catch (ItemNotFound e) {
			e.printStackTrace();
		}

		if (nonNull(product) && searchEnable) {
			product.setStock(stockService.getById(product.getStock().getId(), true));
			product.setCategory(categoryService.getById(product.getCategory().getId(), true));
			product = product.getEnabled() ? product : null;
		}

		return product;
	}

	@Override
	public Product update(Product product) {
		
		try {
			return repositoryImpl.update(product);
		} catch (ItemNotFound e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Product softDelete(Long id) {
		if(isNull(id)){
			return null;
		}
		try {
			repositoryImpl.getById(id).setEnabled(!repositoryImpl.getById(id).getEnabled());
			return repositoryImpl.update(repositoryImpl.getById(id));
		} catch (ItemNotFound e) {
			e.printStackTrace();
			return null;
		}
		

	}

	@Override
	public Product delete(Long id) {
		if(isNull(id)){
			return null;
		}
		Product product;
		try {
			product = repositoryImpl.getById(id);
			repositoryImpl.delete(repositoryImpl.getById(id));
			return product;
		} catch (ItemNotFound e) {
			e.printStackTrace();
			return null;
		}
	}

}
