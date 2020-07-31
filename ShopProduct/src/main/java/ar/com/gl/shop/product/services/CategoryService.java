package ar.com.gl.shop.product.services;

import java.util.List;
import ar.com.gl.shop.product.model.Category;

public interface CategoryService {
	
	public void agregarPrimerosObjetos();
	public void create(Long id, String name, String description);
	public List<Category> findAll(Boolean bool);
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
	public Category findOneByiD(Long id, Boolean bool);
	public Category updateById(Long id);
	public void deleteById(Long id);


}
