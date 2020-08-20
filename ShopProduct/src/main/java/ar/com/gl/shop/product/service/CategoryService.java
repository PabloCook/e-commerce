package ar.com.gl.shop.product.service;

import java.util.List;
import ar.com.gl.shop.product.model.Category;

public interface CategoryService {
	
	public Category create(String name, String description);

	public List<Category> findAll();
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
	public Category getById(Long id, Boolean bool);
	public Category update(Category theCategory);
	public Category softDelete(Long id);
	public Category delete(Long id);
	public List<Category> findAllDisabled();


}
