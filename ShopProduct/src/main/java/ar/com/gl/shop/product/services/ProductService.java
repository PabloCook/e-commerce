package ar.com.gl.shop.product.services;

import java.util.List;

import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.repositoryimpl.RepositoryImpl;

public interface ProductService {
	
	public void create(Long id, String name, String description, Double price, Category category);
	public List<Product> findAll(Boolean bool);
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
	public Product findOneByiD(Long id, Boolean bool);
	public Product updateById(Long id, CategoryService categoryService);
	public void deleteById(Long id);
	public RepositoryImpl getRepositoryImpl();
	public List<Product> getTheProducts();
	public Product getTheProduct();

}
