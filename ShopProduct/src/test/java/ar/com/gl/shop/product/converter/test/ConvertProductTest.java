package ar.com.gl.shop.product.converter.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import ar.com.gl.shop.product.dto.ProductDTO;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.utils.Converter;
import ar.com.gl.shop.product.utils.ProductDTOConverter;

@ExtendWith(MockitoExtension.class)
public class ConvertProductTest {

	@Mock
	ModelMapper modelMapperMock;
	
	@InjectMocks
	Converter converter;
	
	ProductDTOConverter productDTOConverter;
	
	ProductDTO productDTO1;
	Product product1;
	Stock stock1;

	Category category1;
	
	List<Product> productList;
	
	List<ProductDTO> productDTOList;
	
	@BeforeEach
	void setUp() {
		productDTOConverter = new ProductDTOConverter();
		category1 = new Category(
				"category1",
				"descCategory1");
		category1.setId(1L);
		
		stock1 = new Stock(30, "SJ");
		stock1.setId(1L);
		
		product1 = new Product(
				"Test product",
				"Product for testing",
				500.0,category1);
		product1.setId(1L);
		
		product1.setStock(stock1);
		
		productDTO1 = new ProductDTO(
				product1.getId(),
				product1.getName(),
				product1.getDescription(),
				product1.getPrice(),
				product1.getEnabled(),
				product1.getDate(),
				product1.getCategory().getId(),
				product1.getCategory().getName(),
				product1.getCategory().getDescription(),
				product1.getCategory().getEnabled(),
				product1.getStock().getId(),
				product1.getStock().getQuantity(),
				product1.getStock().getLocationCode()
		);
		productList = new ArrayList<Product>();
		productList.add(product1);
		productList.add(product1);
		
		productDTOList = new ArrayList<ProductDTO>();
		productDTOList.add(productDTO1);
		productDTOList.add(productDTO1);
	}
	
	@Test
	@DisplayName("toEntity")
	void testCase_1(){
		lenient().when(modelMapperMock.map(productDTO1, Product.class)).thenReturn(product1);
		
		assertEquals(product1, 	productDTOConverter.toEntity(productDTO1));
	}
	
	@Test
	@DisplayName("toDto")
	void testCase_2(){
		lenient().when(modelMapperMock.map(product1, ProductDTO.class)).thenReturn(productDTO1);
		
		assertEquals(productDTO1, 	productDTOConverter.toDTO(product1));
	}
	
	@Test
	@DisplayName("toDtoList")
	void testCase_3(){
		lenient().when(modelMapperMock.map(product1, ProductDTO.class)).thenReturn(productDTO1);
		
		assertEquals(productDTOList, productDTOConverter.toDTOList(productList));
	}
	
	@Test
	@DisplayName("toEntity") //Para update
	void testCase_4(){
		lenient().when(modelMapperMock.map(productDTO1, Product.class)).thenReturn(product1);
		
		productDTO1.setName("name2");
		Product product2 = product1;
		product2.setName("name2");
		
		assertEquals(product2, productDTOConverter.toEntity(productDTO1, product1));
	}
	
}
