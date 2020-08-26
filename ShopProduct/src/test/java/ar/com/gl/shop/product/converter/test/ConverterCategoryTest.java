package ar.com.gl.shop.product.converter.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import ar.com.gl.shop.product.dto.CategoryDTO;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.utils.CategoryDTOConverter;
import ar.com.gl.shop.product.utils.Converter;

import static org.mockito.Mockito.lenient;


@ExtendWith(MockitoExtension.class)
public class ConverterCategoryTest {

	@Mock
	ModelMapper modelMapperMock;

	@InjectMocks
	Converter converter;

	CategoryDTOConverter categoryDTOConverter;

	CategoryDTO categoryDTO;

	Category category1;

	List<Category> categoryList;

	List<CategoryDTO> categoryDTOList;

	@BeforeEach
	void setUp() {
		categoryDTOConverter = new CategoryDTOConverter();
		category1 = new Category("category1", "descCategory1");
		category1.setId(1L);
		category1.setEnabled(true);

		categoryDTO = new CategoryDTO(category1.getId(), category1.getName(), category1.getDescription(),
				category1.getEnabled());

		categoryList = new ArrayList<Category>();
		categoryList.add(category1);
		categoryList.add(category1);

		categoryDTOList = new ArrayList<CategoryDTO>();
		categoryDTOList.add(categoryDTO);
		categoryDTOList.add(categoryDTO);
	}

	@Test

	@DisplayName("toEntity")
	void testCase_1() {

		lenient().when(modelMapperMock.map(categoryDTO, Category.class)).thenReturn(category1);

		assertEquals(category1, categoryDTOConverter.toEntity(categoryDTO));
	}

	@Test

	@DisplayName("toDto")
	void testCase_2() {

		lenient().when(modelMapperMock.map(category1, CategoryDTO.class)).thenReturn(categoryDTO);

		assertEquals(categoryDTO, categoryDTOConverter.toDTO(category1));
	}

	@Test

	@DisplayName("toDtoList")
	void testCase_3() {
		lenient().when(modelMapperMock.map(category1, CategoryDTO.class)).thenReturn(categoryDTO);

		assertEquals(categoryDTOList, categoryDTOConverter.toDTOList(categoryList));
	}

}
