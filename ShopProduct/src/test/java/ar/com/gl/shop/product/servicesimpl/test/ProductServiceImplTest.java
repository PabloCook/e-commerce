package ar.com.gl.shop.product.servicesimpl.test;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.services.ProductService;
import ar.com.gl.shop.product.servicesimpl.ProductServiceImpl;


class ProductServiceImplTest {

	ProductService productService;
	Product product1, product2;
	
	@BeforeEach
	void setUp(){
		productService =  new ProductServiceImpl();
		product1 = new Product(1L,"Test product", "Product for testing", 500.0, new Category());
		product1.setStock(new Stock(30, "SJ"));
		product2 = new Product(2L,"Test product2", "Second product for testing", 500.0, new Category());
		product2.setStock(new Stock(50, "MDZ"));
		productService.create(product1);
		productService.create(product2);
	}

	/*@ParameterizedTest
	@MethodSource("provideStringsForFindAll")*/
	@Test
	@DisplayName("test find all")
	void testCase_1() {
		
		Product[] theProducts = {
				
				productService.findById(1l, true),
				productService.findById(2l, true)
		};
		
		
		assertArrayEquals(theProducts, productService.findAll().toArray());
	}
	
	/*private static Stream<Arguments> provideStringsForFindAll() {
		
		List<Product> outcome = productService.findAll();
		List<Product> expected = new ArrayList<Product>();
		expected.add(product1);
		expected.add(product2);
		return Stream.of(Arguments.of(expected.get(0).toString(), outcome.get(0).toString()),Arguments.of(expected.get(1).toString(), outcome.get(1).toString()));
	}*/
	
	@Test
	@DisplayName("test DeleteById")
	void testCase_2()
	{
		Product productToDelete = productService.findById(1L, true);
		productService.deleteById(productToDelete);
		assertNull(productService.findById(1L, true));
	}
	
	/*@ParameterizedTest
	@MethodSource("provideStringsForFindAllDisabled")*/
	@Test
	@DisplayName("test FindAllDisabled")
	void testCase_3() {
		
		
		productService.findById(1l, true).setEnabled(false);
		productService.findById(2l, true).setEnabled(false);
		
		Boolean sameSize = productService.findAllDisabled().size() == 2;
				
		assertTrue(sameSize);
	}
	
	@Test
	@DisplayName("test recover product")
	void testCase_4()
	{
		Product  product = productService.findById(2L, true);
		product.setEnabled(false);
		productService.deleteById(product);
		Product  recoveredProducty = productService.findById(2L, true);
		assertNotNull(recoveredProducty);
	}
	
	
	@Test
	@DisplayName("test UpdateById")
	void testCase_5()
	{
		Product updateProduct = productService.findById(1L, true);
		updateProduct.setName("updated product");
		productService.updateById(updateProduct);
		assertEquals("updated product",updateProduct.getName());
	}
	
	
	/*private static Stream<Arguments> provideStringsForFindAllDisabled() {
		
		List<Product> outcome = productService.findAllDisabled();
		List<Product> expected = new ArrayList<Product>();
		expected.add(product1);
		expected.add(product2);
		return Stream.of(Arguments.of(expected.get(0).toString(), outcome.get(0).toString()),Arguments.of(expected.get(1).toString(), outcome.get(1).toString()));
	}*/
	
	
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
