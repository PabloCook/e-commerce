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
import ar.com.gl.shop.product.repository.impl.CategoryRepositoryImpl;
import ar.com.gl.shop.product.service.impl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class CategoryServicesImplTest {

	
	@InjectMocks
	CategoryServiceImpl categoryService ;
	
	@Mock
	CategoryRepositoryImpl categoryRepositoryImpl;
	
	Category category1,category2,category3;
	
	
	
	
	@BeforeEach
	void setUp() throws ItemNotFound {
		
		categoryService.create("category1","descripcion");
		categoryService.create("category2" , "descripcion2");
		categoryService.create("category3", "descripcion3");
		category1= new Category("category1","descripcion");
		category2= new Category("category2" , "descripcion2");
		category3= new Category("category3", "descripcion3");
		category3.setId(3L);
		category2.setId(2L);
		category1.setId(1L);
		lenient().when(categoryRepositoryImpl.getById(1l)).thenReturn(category1);
		lenient().when(categoryRepositoryImpl.getById(2l)).thenReturn(category2);
		lenient().when(categoryRepositoryImpl.getById(3l)).thenReturn(category3);
		
				
		
		
	}
	@Test
	@DisplayName("testFindAll")
	void testCase_1() throws ItemNotFound {
		Category[] theCategories = {
				
				categoryService.getById(1l, true),
				categoryService.getById(2l, true),
				categoryService.getById(3l, true)
				
		};
		
		lenient().when(categoryRepositoryImpl.findAll()).thenReturn(Arrays.asList(theCategories));
		assertArrayEquals(categoryService.findAll().toArray(), theCategories);
	}
	


	
	@Test
	@DisplayName("testFindByIdNotNull")
	void testCase_2() throws ItemNotFound
	{	
		when(categoryRepositoryImpl.getById(3L)).thenReturn(category3);
		assertNotNull(categoryService.getById(3L, true));
	}

	@Test
	@DisplayName("testFindByIdNull")
	void testCase_3() throws ItemNotFound
	{	when(categoryRepositoryImpl.getById(3L)).thenReturn(null);
		assertNull(categoryService.getById(3L, true));
	}
	
		
	@Test
	@DisplayName("testUpdateById")
	void testCase_6()
	{
		category1.setName("Consumables");
		
		Category categoryUpdated = categoryService.update(category1);
		
		assertEquals(category1, categoryUpdated);
	}
		
	@Test
	@DisplayName("testDeleteById")
	void testCase_4() throws ItemNotFound
	{	categoryService.softDelete(category1.getId());
		when(categoryRepositoryImpl.getById(1l)).thenReturn(null);
		assertNull(categoryService.getById(1l, true));
		assertNull(categoryService.getById(1l, false));
	}

	@Test
	@DisplayName("test FindAllDisabled")
	void testCase_5() throws ItemNotFound {
		
		category1.setEnabled(false);
		category2.setEnabled(false);
		category3.setEnabled(false);
		
		Category[] theCategories = {category1, category2, category3};
		
		
		when(categoryRepositoryImpl.findAll()).thenReturn(Arrays.asList(theCategories));		
		assertTrue(categoryService.findAllDisabled().size() ==3 );
	}

	
	@Test
	@DisplayName("test recover category")
	void testCase_7() throws ItemNotFound
	{
		category1.setEnabled(false);
		lenient().when(categoryRepositoryImpl.getById(category1.getId())).thenReturn(category1);
		lenient().when(categoryRepositoryImpl.update(category1)).thenReturn(category1);
		Category  recoveredCategory = categoryService.softDelete(category1.getId());
		
		assertNotNull(recoveredCategory);
	}
	
	@Test
	@DisplayName("test ForceDelete")
	void testCase_8() throws ItemNotFound
	{
		
		categoryService.delete(category1.getId());
		when(categoryRepositoryImpl.getById(category1.getId())).thenReturn(null);
		assertNull(categoryService.getById(1l, true));
		assertNull(categoryService.getById(1l, false));
	}
	
	
	
	
}
