package ar.com.gl.shop.product.service;

import java.time.LocalDate;
import java.util.List;

import ar.com.gl.shop.product.dto.ProductDTO;
import ar.com.gl.shop.product.model.Product;

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
	public Product getById(Long id, Boolean enabled);
	public List<Product> findAll();
	public Product softDelete(Long id);
	public void delete(Long id);
	public Product update(ProductDTO productDTO,Product product);
	public List<Product> findAllDisabled();
	public Product getByName(String name);
	public List<Product> findCategoryById(Long id);
	public default LocalDate getCurrentLocalDate()
	{
		return LocalDate.now();
	}
}
