package ar.com.gl.shop.product.servicesimpl.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.com.gl.shop.product.dto.ProductDTO;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repository.CategoryRepository;
import ar.com.gl.shop.product.repository.ProductRepository;
import ar.com.gl.shop.product.repository.StockRepository;
import ar.com.gl.shop.product.service.impl.CategoryServiceImpl;
import ar.com.gl.shop.product.service.impl.ProductServiceImpl;
import ar.com.gl.shop.product.utils.ProductDTOConverter;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
	
	@InjectMocks
	ProductServiceImpl productService;
	
	@Mock
	ProductDTOConverter productDTOConverter;
	
	@Mock
	CategoryServiceImpl categoryServiceImpl;

	@Mock
	ProductRepository productRepository;

	@Mock
	CategoryRepository categoryRepository;

	@Mock
	StockRepository stockRepository;
		
	Product product1, product2;

	Optional<Product> oProduct1, oProduct2;
	
	ProductDTO productDTO1, productDTO2;
	
	
	
	@BeforeEach
	void setUp() {
		product1 = new Product("Test product", "Product for testing", 500.0, new Category());
		product1.setStock(new Stock(30, "SJ"));
		product2 = new Product("Test product2", "Second product for testing", 500.0, new Category());
		product2.setStock(new Stock(50, "MDZ"));
		
		product1.setId(1l);
		
		oProduct1 = Optional.of(product1);
		oProduct2 = Optional.of(product2);
		
		productDTO1 = new ProductDTO(1l, "test productDTO", "test description", 500.0, true, 1l, "category name", "category description", true, 1l, 20, "buenos aires");
		
	}

	@Test
	@DisplayName("test service get By id with id=null")
	void tesCase_1(){

		assertNull(productService.getById(null, true));
		
	}

	@Test
	@DisplayName("test service get By id with, id != null, oProduct.isPresent()=true, searchEnabled = true, product.getEnabled() = true")
	void tesCase_2(){

		product1 = oProduct1.get();

		product1.setEnabled(true);

		when(productRepository.findById(1l)).thenReturn(oProduct1);

		assertEquals(product1, productService.getById(1l, true));
	}

	@Test
	@DisplayName("test service get By id with, id != null, oProduct.isPresent()=true, searchEnabled = true, product.getEnabled() = false")
	void tesCase_3(){

		product1 = oProduct1.get();	

		product1.setEnabled(false);

		when(productRepository.findById(1l)).thenReturn(oProduct1);

		assertNull(productService.getById(1l, true));
	}

	@Test
	@DisplayName("test service getByid with, id != null, oProduct.isPresent()=true, searchEnabled=false")
	void tesCase_4(){

		product1 = oProduct1.get();

		product1.setEnabled(true);

		when(productRepository.findById(1l)).thenReturn(oProduct1);

		assertEquals(product1, productService.getById(1l, false));
	}

	/*@Test
	@DisplayName("test service get By id with, id != null, oProduct.isPresent()=false")
	void tesCase_5(){
		
		oProduct1 = Optional.empty();

		when(productRepository.findById(1l)).thenReturn(oProduct1);

		assertNull(productService.getById(1l, true));
	}*/

	@Test
	@DisplayName("test find all")
	void testCase_6() {	
		
		product1.setEnabled(false);

		Product[] theProducts = {product1, product2};
		
		Product[] theProductsEnabled = {product2};		

		when(productRepository.findAll()).thenReturn(Arrays.asList(theProducts));
		
		assertArrayEquals(theProductsEnabled, productService.findAll().toArray());		
	}
	
	@Test
	@DisplayName("test find all diabled")
	void testCase_7() {	
		
		product1.setEnabled(false);
		product2.setEnabled(false);

		Product[] theProducts = {product1, product2};

		when(productRepository.findAll()).thenReturn(Arrays.asList(theProducts));
		
		assertArrayEquals(theProducts, productService.findAllDisabled().toArray());		
	}
	
	@Test
	@DisplayName("test create")
	void testCase_8() {
		
		when(productRepository.save(product1)).thenReturn(product1);
		
		assertEquals(product1, productService.create(product1));
	}
	
	@Test
	@DisplayName("test update productDTO != null")
	void testCase_9() {
		
		Category category1 = new Category("category name", "description name");
		
		category1.setId(1l);
		
		when(categoryServiceImpl.getById(productDTO1.getCategoryId(), true)).thenReturn(category1);
		
		lenient().when(categoryServiceImpl.getById(product1.getCategory().getId(), true)).thenReturn(product1.getCategory());
		
		product2.setDescription(productDTO1.getDescription());
		product2.setName(productDTO1.getName());
		product2.setPrice(productDTO1.getPrice());
		product2.getCategory().setDescription(productDTO1.getCategoryDescription());
		product2.getCategory().setName(productDTO1.getCategoryName());
		product2.getStock().setLocationCode(productDTO1.getStockLocationCode());
		product2.getStock().setQuantity(productDTO1.getStockQuantity());
		
		when(productDTOConverter.toEntity(productDTO1, product1)).thenReturn(product2);
		
		when(productRepository.save(product2)).thenReturn(product2);
		
		assertEquals(product2, productService.update(productDTO1, product1));
		

	}
	
	@Test
	@DisplayName("test update productDTO == null")
	void testCase_10() {
		
		productDTO1.setCategoryId(null);
		
		when(categoryServiceImpl.getById(product1.getCategory().getId(), true)).thenReturn(product1.getCategory());
		
		product2.setDescription(productDTO1.getDescription());
		product2.setName(productDTO1.getName());
		product2.setPrice(productDTO1.getPrice());
		product2.getCategory().setDescription(productDTO1.getCategoryDescription());
		product2.getCategory().setName(productDTO1.getCategoryName());
		product2.getStock().setLocationCode(productDTO1.getStockLocationCode());
		product2.getStock().setQuantity(productDTO1.getStockQuantity());
		
		when(productDTOConverter.toEntity(productDTO1, product1)).thenReturn(product2);
		
		when(productRepository.save(product2)).thenReturn(product2);
		
		assertEquals(product2, productService.update(productDTO1, product1));
		

	}
	
	@Test
	@DisplayName("softDelete id = null")
	void testCase_11() {
		assertNull(productService.softDelete(null));
	}
	
	@Test
	@DisplayName("softDelete id != null setEnabled = true")
	void testCase_12() {
		
		product1.setEnabled(true);
		oProduct1 = Optional.of(product1);
		
		when(productRepository.findById(1l)).thenReturn(oProduct1);
		
		when(productRepository.save(product1)).thenReturn(product1);
		
		assertFalse(productService.softDelete(1l).getEnabled());
		
	}
	
	@Test
	@DisplayName("softDelete id != null setEnabled = false")
	void testCase_13() {
		
		product1.setEnabled(false);
		oProduct1 = Optional.of(product1);
		
		when(productRepository.findById(1l)).thenReturn(oProduct1);
		
		when(productRepository.save(product1)).thenReturn(product1);
		
		assertTrue(productService.softDelete(1l).getEnabled());
		
	}
	
	@Test
	@DisplayName("getByName")
	void testCase_14() {
		
		String name = product1.getName();
		
		when(productRepository.findByName(name)).thenReturn(oProduct1);
		
		assertEquals(product1, productService.getByName(name));
		
		
	}
	
	@Test
	@DisplayName("findCategoryById")
	void testCase_15() {
		
		Product[] theProducts = {product1, product2};
		
		product1.getCategory().setId(1l);
		
		product2.getCategory().setId(2l);
		
		Product[] theProductsWithSameId = {product1};	
		
		
		when(productService.findAll()).thenReturn(Arrays.asList(theProducts));
		
		assertArrayEquals(theProductsWithSameId, productService.findCategoryById(1l).toArray());
		
	}
	
	@Test
	@DisplayName("delete id != null")
	void testCase_16() {
		
		when(productRepository.findById(1l)).thenReturn(oProduct1);
		
		productService.delete(1l);
		
		verify(productRepository).delete(product1);
		
	}
	
	@Test
	@DisplayName("delete id != null")
	void testCase_17() {
		
		productService.delete(null);
		
		verifyNoInteractions(productRepository);
		
	}
}
