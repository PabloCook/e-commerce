package ar.com.gl.shop.product.service;

import java.util.List;

import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.repository.impl.ProductRepositoryImpl;

public interface ProductService {
	
	public Product create(Product product);
	/**
	 * Este metodo sirve para buscar un elemento por id pasado por parametro,
	 * el segundo parametro es la manera de buscar, si se pasa true va a hacer una busqueda
	 * solamente en los elementos con el atributo enabled == true,
	 * si pasamos por parametro false, entonces busca en todos los elementos en memoria.
	 * 
	 * @param id
	 * @param bool
	 * @return Repository
	 */
	public Product getById(Long id, Boolean bool);
	public List<Product> findAll();

	public Product softDelete(Long id);
	public Product  delete(Long id);
	public Product update(Product product);
	
	public ProductRepositoryImpl getRepositoryImpl();

	public List<Product> findAllDisabled();

}
