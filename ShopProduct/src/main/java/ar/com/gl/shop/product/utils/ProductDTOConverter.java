package ar.com.gl.shop.product.utils;

import java.util.List;
import java.util.stream.Collectors;

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
		return products.stream().map(product -> toDTO(product)).collect(Collectors.toList());
	}
	
	public Product toEntity(ProductDTO productDTO, Product prod) {
		return (Product) super.fromTo(productDTO, prod);
	}

}
