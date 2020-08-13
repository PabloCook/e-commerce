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
import ar.com.gl.shop.product.repository.impl.RepositoryImpl;
import ar.com.gl.shop.product.service.impl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class CategoryServicesImplTest {

	
	@InjectMocks
	CategoryServiceImpl categoryService;
	
	
	
	@Mock
	RepositoryImpl repositoryImpl;
	
	
	Category category1,category2,category3;
	
	
	
	
	@BeforeEach
	void setUp() {
		
		categoryService.create(1l,"category1","descripcion");
		categoryService.create(2l,"category2" , "descripcion2");
		categoryService.create(3l, "category3", "descripcion3");
		category1= new Category(1l,"category1","descripcion");
		category2= new Category(2l,"category2" , "descripcion2");
		category3= new Category(3l,"category3", "descripcion3");
		
		
		lenient().when(repositoryImpl.findCategoryById(1l)).thenReturn(category1);
		lenient().when(repositoryImpl.findCategoryById(2l)).thenReturn(category2);
		lenient().when(repositoryImpl.findCategoryById(3l)).thenReturn(category3);
		
				
		categoryService.agregarPrimerosObjetos();
		
	}
	@Test
	@DisplayName("testFindAll")
	void testCase_1() {
		Category[] theCategories = {
				
				categoryService.findById(1l, true),
				categoryService.findById(2l, true),
				categoryService.findById(3l, true)
				
		};
		
		lenient().when(repositoryImpl.findAllCategory()).thenReturn(Arrays.asList(theCategories));
		assertArrayEquals(categoryService.findAll().toArray(), theCategories);
	}
	


	
	@Test
	@DisplayName("testFindByIdNotNull")
	void testCase_2()
	{	
		when(repositoryImpl.findCategoryById(3L)).thenReturn(category3);
		assertNotNull(categoryService.findById(3L, true));
	}

	@Test
	@DisplayName("testFindByIdNull")
	void testCase_3()
	{	when(repositoryImpl.findCategoryById(3L)).thenReturn(null);
		assertNull(categoryService.findById(3L, true));
	}
	
		
	@Test
	@DisplayName("testUpdateById")
	void testCase_6()
	{
		category1.setName("Consumables");
		
		Category categoryUpdated = categoryService.updateById(category1);
		
		assertEquals(category1, categoryUpdated);
	}
		
	@Test
	@DisplayName("testDeleteById")
	void testCase_4()
	{	categoryService.deleteById(category1);
		when(repositoryImpl.findCategoryById(1l)).thenReturn(null);
		assertNull(categoryService.findById(1l, true));
		assertNull(categoryService.findById(1l, false));
	}

	@Test
	@DisplayName("test FindAllDisabled")
	void testCase_5() {
		
		category1.setEnabled(false);
		category2.setEnabled(false);
		category3.setEnabled(false);
		
		Category[] theCategories = {category1, category2, category3};
		
		
		when(repositoryImpl.findAllCategory()).thenReturn(Arrays.asList(theCategories));		
		assertTrue(categoryService.findAllDisabled().size() ==3 );
	}

	
	@Test
	@DisplayName("test recover category")
	void testCase_7()
	{
		Category  category = categoryService.findById(3L, true);
		category.setEnabled(false);
		categoryService.deleteById(category);
		Category  recoveredCategory = categoryService.findById(3L, true);
		assertNotNull(recoveredCategory);
	}
	
	@Test
	@DisplayName("test ForceDelete")
	void testCase_8()
	{
		
		categoryService.forceDeleteById(category1);
		when(repositoryImpl.findCategoryById(category1.getId())).thenReturn(null);
		assertNull(categoryService.findById(1l, true));
		assertNull(categoryService.findById(1l, false));
	}
	
	
	
	
}
