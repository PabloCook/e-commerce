package ar.com.gl.shop.product.servicesimpl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.repository.CategoryRepository;
import ar.com.gl.shop.product.service.impl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class CategoryServicesImplTest {

	@InjectMocks
	CategoryServiceImpl categoryService;

	@Mock
	CategoryRepository categoryRepositoryMock;

	Category category1, category2, category3, category4;
	Optional<Category> oCategory3;

	List<Category> theCategoriesMock = new ArrayList<>();

	@BeforeEach
	void setUp() throws ItemNotFound {

		category1 = new Category("category1", "descripcion");
		category2 = new Category("category2", "descripcion2");
		category3 = new Category("category3", "descripcion3");
		category4 = new Category("category4", "description4");
		category4.setEnabled(false);
		oCategory3 = Optional.of(category3);
		category1.setId(1L);
		category2.setId(2L);
		category3.setId(3L);
		category4.setId(4L);

		theCategoriesMock.add(category1);
		theCategoriesMock.add(category2);
		theCategoriesMock.add(category3);
		theCategoriesMock.add(category4);
	}

	@Test
	@DisplayName("testCreate")
	void testCase_1() {

		when(categoryRepositoryMock.save(category1)).thenReturn(category1);
		assertTrue(categoryService.create(category1).equals(category1));

	}

	@Test
	@DisplayName("testFindAll")
	void testCase_2() {
		theCategoriesMock.remove(category4);
		when(categoryRepositoryMock.findAll()).thenReturn((theCategoriesMock));
		assertEquals(theCategoriesMock, categoryService.findAll());
	}

	@Test
	@DisplayName("testFindByIdNotNull")
	void testCase_3() {

		when(categoryRepositoryMock.findById(3L)).thenReturn(oCategory3);
		assertNotNull(categoryService.getById(3L, true));
		assertTrue(categoryService.getById(3L, true).equals(category3));
	}

	@Test
	@DisplayName("testUpdateById")
	void testCase_4() {
		category1.setName("Consumables");

		when(categoryRepositoryMock.save(category1)).thenReturn(category1);

		assertEquals(category1, categoryService.update(category1));
	}

	@Test
	@DisplayName("testDeleteById")
	void testCase_5() {
		when(categoryRepositoryMock.findById(3L)).thenReturn(oCategory3);
		categoryService.softDelete(category3.getId());
		oCategory3.get().setEnabled(false);
		when(categoryRepositoryMock.findById(3L)).thenReturn(oCategory3);
		assertNull(categoryService.getById(3L, true));
		assertNotNull(categoryService.getById(3L, false));
	}

	@Test
	@DisplayName("test FindAllDisabled")
	void testCase_6() {

		when(categoryRepositoryMock.findAll()).thenReturn(theCategoriesMock);
		assertTrue(categoryService.findAllDisabled().size() == 4);
		assertEquals(theCategoriesMock, categoryService.findAllDisabled());
	}

	@Test
	@DisplayName("test recover category")
	void testCase_7() {
		oCategory3.get().setEnabled(false);
		when(categoryRepositoryMock.findById(oCategory3.get().getId())).thenReturn(oCategory3);
		oCategory3.get().setEnabled(true);
		when(categoryRepositoryMock.save(oCategory3.get())).thenReturn(oCategory3.get());

		assertEquals(oCategory3.get(), categoryService.softDelete(oCategory3.get().getId()));
		
	}

	@Test
	@DisplayName("test ForceDelete")
	void testCase_8() {
		when(categoryRepositoryMock.findById(oCategory3.get().getId())).thenReturn(oCategory3);
		categoryService.delete(oCategory3.get().getId());
		
		when(categoryRepositoryMock.findById(oCategory3.get().getId())).thenReturn(Optional.ofNullable(null));
		assertNull(categoryService.getById(oCategory3.get().getId(), false));
	}
	
}
