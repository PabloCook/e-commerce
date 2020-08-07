package ar.com.gl.shop.product.servicesimpl.test;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import ar.com.gl.shop.product.repositoryimpl.RepositoryImpl;
import ar.com.gl.shop.product.services.ProductService;
import ar.com.gl.shop.product.servicesimpl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
	@InjectMocks
	ProductServiceImpl productService = new ProductServiceImpl();
	
	@Mock
	RepositoryImpl repositoryImpl;

	Product product1, product2;
	
	@BeforeEach
	void setUp(){
		product1 = new Product(1L,"Test product", "Product for testing", 500.0, new Category());
		product1.setStock(new Stock(30, "SJ"));
		product2 = new Product(2L,"Test product2", "Second product for testing", 500.0, new Category());
		product2.setStock(new Stock(50, "MDZ"));
		productService.create(product1);
		productService.create(product2);
		lenient().when(repositoryImpl.findProductById(2L)).thenReturn(product2);
		lenient().when(repositoryImpl.findProductById(1L)).thenReturn(product1);
	}

	@Test
	@DisplayName("test find all")
	void testCase_1() {	
		Product[] theProducts = {
				productService.findById(1l, true),
				productService.findById(2l, true)
		};	
			
		when(repositoryImpl.findAllProduct()).thenReturn(Arrays.asList(theProducts));
		
		assertArrayEquals(theProducts, productService.findAll().toArray());		
	}

	
	@Test
	@DisplayName("test DeleteById")
	void testCase_2()
	{
		Product productToDelete = productService.findById(1L, true);
		productService.deleteById(productToDelete);
		
		when(repositoryImpl.findProductById(1L)).thenReturn(null);
		assertNull(productService.findById(1L, true));
		
		when(repositoryImpl.findProductById(1L)).thenReturn(product1);
		assertNotNull(productService.findById(1L, false));
	}

	@Test
	@DisplayName("test FindAllDisabled")
	void testCase_3() {		
		productService.findById(1l, true).setEnabled(false);
		productService.findById(2l, true).setEnabled(false);
		
		product1.setEnabled(false);
		product2.setEnabled(false);
		
		Product[] theProducts = {product1,product2};	
		
		when(repositoryImpl.findAllProduct()).thenReturn(Arrays.asList(theProducts));
		
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
	
	
	@Test
	@DisplayName("test ForceDelete")
	void testCase_6()
	{
		Product product = productService.findById(2L, true);
		productService.forceDeleteById(product);
		
		when(repositoryImpl.findProductById(2L)).thenReturn(null);
		assertNull(productService.findById(2L, true));
		assertNull(productService.findById(2L, false));
	}
	
}
