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

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repository.impl.ProductRepositoryImpl;
import ar.com.gl.shop.product.repository.impl.StockRepositoryImpl;
import ar.com.gl.shop.product.service.impl.CategoryServiceImpl;
import ar.com.gl.shop.product.service.impl.ProductServiceImpl;
import ar.com.gl.shop.product.service.impl.StockServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
	@InjectMocks
	ProductServiceImpl productService;

	@Mock
	ProductRepositoryImpl productRepositoryImpl;
		
	Product product1, product2;
	
	@BeforeEach
	void setUp() throws ItemNotFound{
		product1 = new Product("Test product", "Product for testing", 500.0, new Category());
		product1.setStock(new Stock(30, "SJ"));
		product2 = new Product("Test product2", "Second product for testing", 500.0, new Category());
		product2.setStock(new Stock(50, "MDZ"));
		productService.create(product1);
		productService.create(product2);
		
		lenient().when(productRepositoryImpl.getById(2L)).thenReturn(product2);
		lenient().when(productRepositoryImpl.getById(1L)).thenReturn(product1);
	}

	@Test
	@DisplayName("test find all")
	void testCase_1() throws ItemNotFound {	
		Product[] theProducts = {
				productService.getById(1l, true),
				productService.getById(2l, true)
		};	
			
		when(productRepositoryImpl.findAll()).thenReturn(Arrays.asList(theProducts));
		
		assertArrayEquals(theProducts, productService.findAll().toArray());		
	}

	
	@Test
	@DisplayName("test DeleteById")
	void testCase_2() throws ItemNotFound
	{
		Product productToDelete = productService.getById(1L, true);
		productService.softDelete(productToDelete.getId());
		
		when(productRepositoryImpl.getById(1L)).thenReturn(null);
		assertNull(productService.getById(1L, true));
		
		when(productRepositoryImpl.getById(1L)).thenReturn(product1);
		assertNotNull(productService.getById(1L, false));
	}

	@Test
	@DisplayName("test FindAllDisabled")
	void testCase_3() throws ItemNotFound {		
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
	void testCase_6() throws ItemNotFound
	{
		Product product = productService.getById(2L, true);
		productService.delete(product.getId());
		
		when(productRepositoryImpl.getById(2L)).thenReturn(null);
		assertNull(productService.getById(2L, true));
		assertNull(productService.getById(2L, false));
	}
	
}
