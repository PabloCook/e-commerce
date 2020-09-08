package com.ar.gl.feign.test;

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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.ar.gl.feign.controller.CatalogController;
import com.ar.gl.feign.dto.CategoryDTO;
import com.ar.gl.feign.shop.product.FeignCatalog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(controllers = CatalogController.class)
@ActiveProfiles("test")
public class CatalogControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	FeignCatalog catalog;
	
	CategoryDTO categoryDTO1,categoryDTO2,categoryDTO3;
	List<CategoryDTO> listCategoryDTO = new ArrayList<>();
	
	@BeforeEach
	void setup() {
		categoryDTO1 = CategoryDTO.builder()
			.id(1L)
			.description("description1")
			.enabled(true)
			.name("category1")
			.build();
		categoryDTO2 = CategoryDTO.builder()
			.id(2L)
			.description("description2")
			.enabled(true)
			.name("category2")
			.build();
		categoryDTO3 = CategoryDTO.builder()
			.id(3L)
			.description("description3")
			.enabled(true)
			.name("category3")
			.build();
			
		listCategoryDTO.add(categoryDTO1);
		listCategoryDTO.add(categoryDTO2);
		listCategoryDTO.add(categoryDTO3);
	}
	
	@Test
	@DisplayName(value = "getAll success")
	void all_category_when_success() throws Exception {
		when(catalog.findAll()).thenReturn(new ResponseEntity<List<CategoryDTO>>(listCategoryDTO, HttpStatus.OK));
		this.mockMvc.perform(get("/categories"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()", is(listCategoryDTO.size())));
	}
	
	@Test
	@DisplayName(value = "category by name success")
	void categories_by_name_success() throws Exception{
		when(catalog.findbyName("category1")).thenReturn(new ResponseEntity<CategoryDTO>(categoryDTO1, HttpStatus.OK));
		this.mockMvc.perform(get("/categories/name/category1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(categoryDTO1.getName())))
				.andExpect(jsonPath("$.description", is(categoryDTO1.getDescription())));
	}
	
	@Test
	@DisplayName(value = "category by id")
	void categories_by_id_success() throws Exception{
		when(catalog.getById(1L)).thenReturn(new ResponseEntity<CategoryDTO>(categoryDTO1, HttpStatus.OK));
		this.mockMvc.perform(get("/categories/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(categoryDTO1.getName())))
				.andExpect(jsonPath("$.description", is(categoryDTO1.getDescription())));
	}
	
	@Test
	@DisplayName(value = "create category succes")
	void create_a_new_category_success() throws Exception{
		when(catalog.create(categoryDTO1)).thenReturn(new ResponseEntity<CategoryDTO>(categoryDTO1, HttpStatus.CREATED));
		this.mockMvc.perform(post("/categories")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapToJson(categoryDTO1)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(categoryDTO1.getId().intValue())))
				.andExpect(jsonPath("$.name", is(categoryDTO1.getName())))
				.andExpect(jsonPath("$.description", is(categoryDTO1.getDescription())));
	}
	
	@Test
	@DisplayName(value = "patch description")
	void change_description_category_success() throws Exception{
		categoryDTO1.setDescription("MOD");
		when(catalog.patch(1L, "MOD")).thenReturn(new ResponseEntity<CategoryDTO>(categoryDTO1, HttpStatus.OK));
		this.mockMvc.perform(patch("/categories/1/description?description=MOD"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(categoryDTO1.getId().intValue())))
				.andExpect(jsonPath("$.name", is(categoryDTO1.getName())))
				.andExpect(jsonPath("$.description", is(categoryDTO1.getDescription())));
	}
	
	@Test
	@DisplayName(value = "delete category by id")
	void delete_categories_by_id_success() throws Exception{
		when(catalog.delete(1L)).thenReturn(new ResponseEntity<String>("Deleted Successful", HttpStatus.OK));
		this.mockMvc.perform(delete("/categories/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is("Deleted Successful")));
	}
	
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
}
