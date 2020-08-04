package ar.com.gl.shop.product.servicesimpl.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import ar.com.gl.shop.product.servicesimpl.CategoryServiceImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CategoryServicesImplTest {

	private static CategoryServiceImpl categoryService;
	
	@BeforeAll
	static void setUp() throws Exception {
	
		categoryService = new CategoryServiceImpl();
		categoryService.agregarPrimerosObjetos();
		categoryService.create(4L, "Test Category", "Category for testing");
	}
	
	
	@ParameterizedTest
	@MethodSource("provideStringsForFindAll")
	@DisplayName("testFindAll")
	void testCase_1(String expected, String outcome) {
		
		assertEquals(expected, outcome);
	}
	
	private static Stream<Arguments> provideStringsForFindAll() {
		
		List<Category> outcome = categoryService.findAll();
		
		List<Category> expected = new ArrayList<Category>();
		
		expected.add(new Category(1l, "Consumibles", "Para comer"));
		expected.add(new Category(2l,"Limpieza", "Para limpiar"));
		expected.add(new Category(3l,"Ropa", "Para vestir"));
		expected.add(new Category(4L, "Test Category", "Category for testing"));
		
		return Stream.of(Arguments.of(expected.get(0).toString(), outcome.get(0).toString()), Arguments.of(expected.get(1).toString(), outcome.get(1).toString()), Arguments.of(expected.get(2).toString(), outcome.get(2).toString()),
				Arguments.of(expected.get(3).toString(), outcome.get(3).toString()));
	}
	
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
	
	@ParameterizedTest
	@MethodSource("provideStringsForFindAllDisabled")
	@DisplayName("test FindAllDisabled")
	void testCase_5(String expected, String outcome) {
		
		assertEquals(expected, outcome);
	}
	
	private static Stream<Arguments> provideStringsForFindAllDisabled() {
		
		List<Category> outcome = categoryService.findAllDisabled();
		
		List<Category> expected = new ArrayList<Category>();
		
		expected.add(new Category(1l, "Consumibles", "Para comer"));
		expected.add(new Category(2l,"Limpieza", "Para limpiar"));
		expected.add(new Category(3l,"Ropa", "Para vestir"));
		expected.add(new Category(4L, "Test Category", "Category for testing"));
		
		return Stream.of(Arguments.of(expected.get(0).toString(), outcome.get(0).toString()), Arguments.of(expected.get(1).toString(), outcome.get(1).toString()), Arguments.of(expected.get(2).toString(), outcome.get(2).toString()),
				Arguments.of(expected.get(3).toString(), outcome.get(3).toString()));
	}
	
	@Test
	@DisplayName("test recover category")
	void testCase_7()
	{
		Category  category = categoryService.findById(3L, false);
		categoryService.deleteById(category);
		Category  recoveredCategory = categoryService.findById(3L, true);
		assertEquals(category.toString(), recoveredCategory.toString());
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
}
