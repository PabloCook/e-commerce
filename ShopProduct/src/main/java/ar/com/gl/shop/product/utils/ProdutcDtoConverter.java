package ar.com.gl.shop.product.utils;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ar.com.gl.shop.product.dto.ProductDTO;
import ar.com.gl.shop.product.model.Product;

public class ProdutcDtoConverter extends Converter{

	ModelMapper modelMapper = new ModelMapper();
	
	public Product toEntity(ProductDTO productDTO) {
		Product product = (Product) super.fromTo(productDTO, Product.class);
		return product;
	}

	
	public ProductDTO toDto(Product product) {
		ProductDTO productDTO = (ProductDTO) super.fromTo(product, ProductDTO.class);
		return productDTO;
	}
	
	
	public List<ProductDTO> toDtoList(List<Product> products) {
		List<ProductDTO> productsDto = new ArrayList<ProductDTO>();
		for(Product product : products) {
			productsDto.add(toDto(product));
		}
		return productsDto;
	}

}
