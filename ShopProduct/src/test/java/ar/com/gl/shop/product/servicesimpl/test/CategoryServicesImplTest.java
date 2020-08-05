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
import ar.com.gl.shop.product.services.CategoryService;
import ar.com.gl.shop.product.servicesimpl.CategoryServiceImpl;


class CategoryServicesImplTest {

	CategoryService categoryService;
	
	@BeforeEach
	void setUp() {
	
		categoryService = new CategoryServiceImpl();
		categoryService.agregarPrimerosObjetos();
		
	}
	
	
	/*@ParameterizedTest
	@MethodSource("provideStringsForFindAll")*/
	@Test
	@DisplayName("testFindAll")
	void testCase_1() {
		
		Category[] theCategories = {
				
				categoryService.findById(1l, true),
				categoryService.findById(2l, true),
				categoryService.findById(3l, true)
				
		};
		
		assertArrayEquals(theCategories, categoryService.findAll().toArray());
	}
	
	/*private static Stream<Arguments> provideStringsForFindAll() {
		
		List<Category> outcome = categoryService.findAll();
		
		List<Category> expected = new ArrayList<Category>();
		
		expected.add(new Category(1l, "Consumibles", "Para comer"));
		expected.add(new Category(2l,"Limpieza", "Para limpiar"));
		expected.add(new Category(3l,"Ropa", "Para vestir"));
		expected.add(new Category(4L, "Test Category", "Category for testing"));
		
		return Stream.of(Arguments.of(expected.get(0).toString(), outcome.get(0).toString()), Arguments.of(expected.get(1).toString(), outcome.get(1).toString()), Arguments.of(expected.get(2).toString(), outcome.get(2).toString()),
				Arguments.of(expected.get(3).toString(), outcome.get(3).toString()));
	}*/
	
	@Test
	@DisplayName("testFindByIdNotNull")
	void testCase_2()
	{
		assertNotNull(categoryService.findById(3L, true));
	}

	@Test
	@DisplayName("testFindByIdNull")
	void testCase_3()
	{
		assertNull(categoryService.findById(78L, true));
	}
	
	@Test
	@DisplayName("testUpdateById")
	void testCase_6()
	{
		Category updateCategory = categoryService.findById(1L, true);
		updateCategory.setName("Consumables");
		categoryService.updateById(updateCategory);
		assertEquals(categoryService.findById(1L, true).toString(),updateCategory.toString());
	}
	
	@Test
	@DisplayName("testDeleteById")
	void testCase_4()
	{
		Category categoryToDelete = categoryService.findById(3L, true);
		categoryService.deleteById(categoryToDelete);
		assertNull(categoryService.findById(3L, true));
	}
	
	/*@ParameterizedTest
	@MethodSource("provideStringsForFindAllDisabled")*/
	@Test
	@DisplayName("test FindAllDisabled")
	void testCase_5() {
		
		categoryService.findById(1l, true).setEnabled(false);
		categoryService.findById(2l, true).setEnabled(false);
		categoryService.findById(3l, true).setEnabled(false);
		
		Boolean sameSize = categoryService.findAllDisabled().size() == 3;
				
		assertTrue(sameSize);
	}
	
	/*private static Stream<Arguments> provideStringsForFindAllDisabled() {
		
		List<Category> outcome = categoryService.findAllDisabled();
		
		List<Category> expected = new ArrayList<Category>();
		
		expected.add(new Category(1l, "Consumibles", "Para comer"));
		expected.add(new Category(2l,"Limpieza", "Para limpiar"));
		expected.add(new Category(3l,"Ropa", "Para vestir"));
		expected.add(new Category(4L, "Test Category", "Category for testing"));
		
		return Stream.of(Arguments.of(expected.get(0).toString(), outcome.get(0).toString()), Arguments.of(expected.get(1).toString(), outcome.get(1).toString()), Arguments.of(expected.get(2).toString(), outcome.get(2).toString()),
				Arguments.of(expected.get(3).toString(), outcome.get(3).toString()));
	}*/
	
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
		Category category = categoryService.findById(4L, true);
		categoryService.forceDeleteById(category);
		assertNull(categoryService.findById(4L, true));
		assertNull(categoryService.findById(4L, false));
	}
	
	@Test
	@DisplayName("test Create")
	void testCase_9() {
		categoryService.create(4L, "Test Category", "Category for testing");
		
		Category category = categoryService.findById(4l, true);
		
		assertEquals(4l + "Test Category" + "Category for testing", category.getId() + category.getName() + category.getDescription());
		
	}
}
