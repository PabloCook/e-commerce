package ar.com.gl.shop.product.service;

import java.util.List;

import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.repository.impl.RepositoryImpl;

public interface ProductService {
	
	public void create(Product product);
	public List<Product> findAll();
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
	public Product findById(Long id, Boolean bool);
	public Product updateById(Product product);
	public void deleteById(Product theProduct);
	public void  forceDeleteById(Product theProduct);
	public RepositoryImpl getRepositoryImpl();
	public List<Product> getTheProducts();
	public Product getTheProduct();
	public List<Product> findAllDisabled();

}
