package ar.com.gl.shop.product.service.impl;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.repository.ProductRepository;
import ar.com.gl.shop.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repositoryImpl;

	
	@Override
	public Product create(Product product) {

		return repositoryImpl.save(product);
	}

	@Override
	public List<Product> findAll() {
		
		return repositoryImpl.findAll()
							 .stream()
						 	 .filter(product -> product.getEnabled())
						 	 .collect(Collectors.toList());

	}

	@Override
	public List<Product> findAllDisabled() {

		return repositoryImpl.findAll();
	}

	@Override
	public Product getById(Long id, Boolean searchEnable) {

		if (isNull(id)) {
			return null;
		}

		Optional<Product> product = repositoryImpl.findById(id);

		if (product.isPresent() && searchEnable) {
			return product.get().getEnabled() ? product.get() : null;
		}

		return null;
	}

	@Override
	public Product update(Product product) {

		return repositoryImpl.save(product);
	}

	@Override
	public Product softDelete(Long id) {

		if (isNull(id)) {
			return null;
		}

		Product product = repositoryImpl.findById(id).get();

		product.setEnabled(!product.getEnabled());

		return repositoryImpl.save(product);

	}

	@Override
	public void delete(Long id) {

		if (nonNull(id)) {
			repositoryImpl.delete(repositoryImpl.findById(id).get());
		}

	}

	@Override
	public Product getByName(String name){
		return repositoryImpl.findByName(name);
	}
	
	@Override
	public List<Product> findCategoryById(Long id) {
		 
		
		return 	findAll()
				.stream()
				.filter(p->p.getCategory().getId().equals(id))
				.collect(Collectors.toList());
	}

}
