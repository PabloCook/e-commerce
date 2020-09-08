package ar.com.gl.shop.product.utils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

import ar.com.gl.shop.product.dto.ProductDTO;
import ar.com.gl.shop.product.model.Product;

@Component
public class ProductDTOConverter extends Converter{
	
	public Product toEntity(ProductDTO productDTO) {
		return (Product) super.fromTo(productDTO, Product.class);
	}

	
	public ProductDTO toDTO(Product product) {
		return (ProductDTO) super.fromTo(product, ProductDTO.class);
	}
	
	
	public List<ProductDTO> toDTOList(List<Product> products) {
		List<ProductDTO> productsDTO = new ArrayList<>();
		products.stream().map(product -> productsDTO.add(toDTO(product)));
		return productsDTO;
	}
	
	public Product toEntity(ProductDTO productDTO, Product prod) {
		return (Product) super.fromTo(productDTO, prod);
	}

}
