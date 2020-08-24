package ar.com.gl.shop.product.servicesimpl.test;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.gl.shop.product.controller.CatalogController;
import ar.com.gl.shop.product.dto.CategoryDTO;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.service.impl.CategoryServiceImpl;
import ar.com.gl.shop.product.utils.CategoryDTOConverter;

@WebMvcTest(controllers = CatalogController.class)
@ActiveProfiles("test")
public class CatalogControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CategoryServiceImpl categoryService;

	@MockBean
	CategoryDTOConverter productConverter;

	CategoryDTO categoryDTO1;
	Category category1;
	Optional<Category> oCategory1;

	List<Category> listCategory = new ArrayList<Category>();
	List<Category> listCategoryEnabled = new ArrayList<Category>();
	List<CategoryDTO> listcategoryDTO = new ArrayList<CategoryDTO>();

	@BeforeEach
	public void setUp() {
		category1 = new Category("category1", "descCategory1");
		category1.setId(1L);

		listCategory.add(category1);

		categoryDTO1 = new CategoryDTO(1l, "category1", "descCategory1", true);
		listcategoryDTO.add(categoryDTO1);

		oCategory1 = Optional.of(category1);

	}

	@Test
	@DisplayName("findAll_Successful")
	void testCase_0() throws Exception {
		when(categoryService.findAll()).thenReturn(listCategory);
		when(productConverter.toDTOList(categoryService.findAll())).thenReturn(listcategoryDTO);
		this.mockMvc.perform(get("/categories")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(listcategoryDTO.size())));

	}

	@Test
	@DisplayName("find_ByName_succesfull")
	void testCase_1() throws Exception {
		when(categoryService.getByName("category1")).thenReturn(category1);
		when(productConverter.toDTO(category1)).thenReturn(categoryDTO1);
		this.mockMvc.perform(get("/categories/name/category1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(categoryDTO1.getName())));
	}

	@Test
	@DisplayName("get_by_id_successful")
	void testCase_2() throws Exception {
		when(categoryService.getById(category1.getId(), true)).thenReturn(category1);
		when(productConverter.toDTO(category1)).thenReturn(categoryDTO1);

		this.mockMvc.perform(get("/categories/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(categoryDTO1.getName())))
				.andExpect(jsonPath("$.description", is(categoryDTO1.getDescription())));
	}

	@Test
	@DisplayName("create category")
	void testCase_3() throws Exception {

		categoryDTO1.setId(null);

		when(productConverter.toEntity(categoryDTO1)).thenReturn(category1);
		when(categoryService.create(category1)).thenReturn(category1);
		categoryDTO1.setId(category1.getId());
		when(productConverter.toDTO(category1)).thenReturn(categoryDTO1);

		this.mockMvc
				.perform(post("/categories").contentType(MediaType.APPLICATION_JSON)
						.content(this.mapToJson(categoryDTO1)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.name", is(categoryDTO1.getName())))
				.andExpect(jsonPath("$.description", is(categoryDTO1.getDescription())));
	}

	@Test
	@DisplayName("patch_successful")
	void testCase_4() throws Exception {

		when(categoryService.getById(category1.getId(), true)).thenReturn(category1);
		categoryDTO1.setDescription("nuevaDescription");
		when(categoryService.update(category1)).thenReturn(category1);

		when(productConverter.toDTO(category1)).thenReturn(categoryDTO1);

		this.mockMvc.perform(patch("/categories/1/description?description=nuevaDescription")).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(categoryDTO1.getName())))
				.andExpect(jsonPath("$.description", is(categoryDTO1.getDescription())));

	}
	
	  @Test 
	  @DisplayName("delete_successful") 
	  void testCase_5() throws Exception{

	  this.mockMvc.perform(delete("/categories/1")) 
	  	.andExpect(status().isOk())
		.andExpect(jsonPath("$",is("Deleted Successful")));
	  
	  }
	

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
