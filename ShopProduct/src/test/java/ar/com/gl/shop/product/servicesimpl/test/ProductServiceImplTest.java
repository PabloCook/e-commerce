package ar.com.gl.shop.product.servicesimpl.test;


import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.MethodSorters;

import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.servicesimpl.ProductServiceImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ProductServiceImplTest {

	static ProductServiceImpl productService;
	static Product product1, product2;
	
	@BeforeAll
	static void setUp() throws Exception {
		productService =  new ProductServiceImpl();
		product1 = new Product(1L,"Test product", "Product for testing", 500.0, new Category());
		product1.setStock(new Stock(30, "SJ"));
		product2 = new Product(2L,"Test product2", "Second product for testing", 500.0, new Category());
		product2.setStock(new Stock(50, "MDZ"));
		productService.create(product1);
		productService.create(product2);
	}

	@ParameterizedTest
	@MethodSource("provideStringsForFindAll")
	@DisplayName("test find all")
	void testCase_1(String expected, String outcome) {
		
		assertEquals(expected, outcome);
	}
	
	private static Stream<Arguments> provideStringsForFindAll() {
		
		List<Product> outcome = productService.findAll();
		List<Product> expected = new ArrayList<Product>();
		expected.add(product1);
		expected.add(product2);
		return Stream.of(Arguments.of(expected.get(0).toString(), outcome.get(0).toString()),Arguments.of(expected.get(1).toString(), outcome.get(1).toString()));
	}
	
	@Test
	@DisplayName("test DeleteById")
	void testCase_2()
	{
		Product productToDelete = productService.findById(1L, true);
		productService.deleteById(productToDelete);
		assertNull(productService.findById(1L, true));
	}
	
	@ParameterizedTest
	@MethodSource("provideStringsForFindAllDisabled")
	@DisplayName("test FindAllDisabled")
	void testCase_3(String expected, String outcome) {
		
		assertEquals(expected, outcome);
	}
	
	@Test
	@DisplayName("test recover product")
	void testCase_4()
	{
		Product product = productService.findById(1L, false);
		productService.deleteById(product);
		Product  recoveredProduct = productService.findById(1L, true);
		assertEquals(product.toString(), recoveredProduct.toString());
	}
	
	
	@Test
	@DisplayName("test UpdateById")
	void testCase_5()
	{
		Product updateProduct = productService.findById(1L, true);
		updateProduct.setName("updated product");
		productService.updateById(updateProduct);
		assertEquals(productService.findById(1L, true).toString(),updateProduct.toString());
	}
	
	
	private static Stream<Arguments> provideStringsForFindAllDisabled() {
		
		List<Product> outcome = productService.findAllDisabled();
		List<Product> expected = new ArrayList<Product>();
		expected.add(product1);
		expected.add(product2);
		return Stream.of(Arguments.of(expected.get(0).toString(), outcome.get(0).toString()),Arguments.of(expected.get(1).toString(), outcome.get(1).toString()));
	}
	
	
	@Test
	@DisplayName("test ForceDelete")
	void testCase_6()
	{
		Product product = productService.findById(2L, true);
		productService.forceDeleteById(product);
		assertNull(productService.findById(2L, true));
		assertNull(productService.findById(2L, false));
	}

}
