package ar.com.gl.shop.product.servicesimpl.test;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repositoryimpl.ProductRepositoryImpl;
import ar.com.gl.shop.product.servicesimpl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
	@InjectMocks
	ProductServiceImpl productService = new ProductServiceImpl();

	@Mock
	ProductRepositoryImpl productRepositoryImpl;


	Product product1, product2;
	
	@BeforeEach
	void setUp(){
		product1 = new Product(1L,"Test product", "Product for testing", 500.0, new Category());
		product1.setStock(new Stock(30, "SJ"));
		product2 = new Product(2L,"Test product2", "Second product for testing", 500.0, new Category());
		product2.setStock(new Stock(50, "MDZ"));
		productService.create(product1);
		productService.create(product2);
		lenient().when(productRepositoryImpl.getBydId(2L)).thenReturn(product2);
		lenient().when(productRepositoryImpl.getBydId(1L)).thenReturn(product1);
	}

	@Test
	@DisplayName("test find all")
	void testCase_1() {	
		Product[] theProducts = {
				productService.getById(1l, true),
				productService.getById(2l, true)
		};	
			
		when(productRepositoryImpl.findAll()).thenReturn(Arrays.asList(theProducts));
		
		assertArrayEquals(theProducts, productService.findAll().toArray());		
	}

	
	@Test
	@DisplayName("test DeleteById")
	void testCase_2()
	{
		Product productToDelete = productService.getById(1L, true);
		productService.softDelete(productToDelete);
		
		when(productRepositoryImpl.getBydId(1L)).thenReturn(null);
		assertNull(productService.getById(1L, true));
		
		when(productRepositoryImpl.getBydId(1L)).thenReturn(product1);
		assertNotNull(productService.getById(1L, false));
	}

	@Test
	@DisplayName("test FindAllDisabled")
	void testCase_3() {		
		productService.getById(1l, true).setEnabled(false);
		productService.getById(2l, true).setEnabled(false);
		
		product1.setEnabled(false);
		product2.setEnabled(false);
		
		Product[] theProducts = {product1,product2};	
		
		when(productRepositoryImpl.findAll()).thenReturn(Arrays.asList(theProducts));
		
		Boolean sameSize = productService.findAllDisabled().size() == 2;
		assertTrue(sameSize);
	}
	
	@Test
	@DisplayName("test recover product")
	void testCase_4()
	{
		Product  product = productService.getById(2L, true);
		product.setEnabled(false);
		productService.softDelete(product);
		Product  recoveredProducty = productService.getById(2L, true);
		assertNotNull(recoveredProducty);
	}
	
	
	@Test
	@DisplayName("test UpdateById")
	void testCase_5()
	{
		Product updateProduct = productService.getById(1L, true);
		updateProduct.setName("updated product");
		productService.update(updateProduct);
		assertEquals("updated product",updateProduct.getName());
	}
	
	
	@Test
	@DisplayName("test ForceDelete")
	void testCase_6()
	{
		Product product = productService.getById(2L, true);
		productService.delete(product);
		
		when(productRepositoryImpl.getBydId(2L)).thenReturn(null);
		assertNull(productService.getById(2L, true));
		assertNull(productService.getById(2L, false));
	}
	
}
