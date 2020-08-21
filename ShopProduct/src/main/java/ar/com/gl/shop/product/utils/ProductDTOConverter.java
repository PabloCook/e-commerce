package ar.com.gl.shop.product.utils;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import ar.com.gl.shop.product.dto.ProductDTO;
import ar.com.gl.shop.product.model.Product;

@Component
public class ProductDTOConverter extends Converter{

	ModelMapper modelMapper = new ModelMapper();
	
	public Product toEntity(ProductDTO productDTO) {
		Product product = (Product) super.fromTo(productDTO, Product.class);
		return product;
	}

	
	public ProductDTO toDTO(Product product) {
		ProductDTO productDTO = (ProductDTO) super.fromTo(product, ProductDTO.class);
		return productDTO;
	}
	
	
	public List<ProductDTO> toDTOList(List<Product> products) {
		List<ProductDTO> productsDTO = new ArrayList<ProductDTO>();
		for(Product product : products) {
			productsDTO.add(toDTO(product));
		}
		return productsDTO;
	}

}
