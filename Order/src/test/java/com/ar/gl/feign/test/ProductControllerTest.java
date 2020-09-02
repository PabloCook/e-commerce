package com.ar.gl.feign.test;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.ar.gl.feign.controller.ProductController;
import com.ar.gl.feign.dto.ProductDTO;
import com.ar.gl.feign.shop.product.FeignProduct;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(controllers = ProductController.class)
@ActiveProfiles("test")
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	FeignProduct product;
	
	ProductDTO productDTO1,productDTO2,productDTO3;
	List<ProductDTO> listProductDTO = new ArrayList<>();
	List<ProductDTO> listProductDTOWithCategoryOne = new ArrayList<>();	
	
	@BeforeEach
	void setup() {
		productDTO1 = ProductDTO.builder()
				  .id(1l)
				  .name("product1")
				  .description("description1")
				  .price(1.0)
				  .enabled(true)
				  .categoryId(1l)
				  .categoryName("category1")
				  .categoryDescription("descriptionCategory1")
				  .categoryEnabled(true)
				  .stockId(1l)
				  .stockQuantity(1)
				  .stockLocationCode("location1")
				  .build();
		productDTO2 = ProductDTO.builder()
				  .id(2l)
				  .name("product2")
				  .description("description2")
				  .price(2.0)
				  .enabled(true)
				  .categoryId(2l)
				  .categoryName("category2")
				  .categoryDescription("descriptionCategory2")
				  .categoryEnabled(true)
				  .stockId(2l)
				  .stockQuantity(2)
				  .stockLocationCode("location2")
				  .build();
		
		productDTO3 = ProductDTO.builder()
				  .id(3l)
				  .name("product3")
				  .description("description3")
				  .price(3.0)
				  .enabled(true)
				  .categoryId(1l)
				  .categoryName("category1")
				  .categoryDescription("descriptionCategory1")
				  .categoryEnabled(true)
				  .stockId(3l)
				  .stockQuantity(3)
				  .stockLocationCode("location3")
				  .build();
		
		listProductDTO.add(productDTO1);
		listProductDTO.add(productDTO2);
		listProductDTO.add(productDTO3);
		
		listProductDTOWithCategoryOne.add(productDTO1);
		listProductDTOWithCategoryOne.add(productDTO3);
	}
	
	@Test
	@DisplayName(value = "getAll success")
	void all_products_when_success() throws Exception {
		when(product.findAll()).thenReturn(new ResponseEntity<List<ProductDTO>>(listProductDTO, HttpStatus.OK));
		this.mockMvc.perform(get("/products"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()", is(listProductDTO.size())));
	}

	@Test
	@DisplayName(value = "product by id")
	void products_by_id_success() throws Exception{
		when(product.getById(1L)).thenReturn(new ResponseEntity<ProductDTO>(productDTO1, HttpStatus.OK));
		this.mockMvc.perform(get("/products/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(productDTO1.getName())))
				.andExpect(jsonPath("$.description", is(productDTO1.getDescription())));
	}
	
	@Test
	@DisplayName(value = "product by name success")
	void products_by_name_success() throws Exception{
		when(product.getByName(productDTO1.getName())).thenReturn(new ResponseEntity<ProductDTO>(productDTO1, HttpStatus.OK));
		this.mockMvc.perform(get("/products/name/product1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(productDTO1.getName())))
				.andExpect(jsonPath("$.description", is(productDTO1.getDescription())));
	}
	
	@Test
	@DisplayName(value = "get products by category id success")
	void all_products_by_category_success() throws Exception {
		when(product.getByCategoryId(1L)).thenReturn(new ResponseEntity<List<ProductDTO>>(listProductDTOWithCategoryOne, HttpStatus.OK));
		this.mockMvc.perform(get("/products/category/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()", is(listProductDTOWithCategoryOne.size())));
	}
	
	@Test
	@DisplayName(value = "create product succes")
	void create_a_new_product_success() throws Exception{
		when(product.create(productDTO1)).thenReturn(new ResponseEntity<ProductDTO>(productDTO1, HttpStatus.CREATED));
		this.mockMvc.perform(post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapToJson(productDTO1)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(productDTO1.getId().intValue())))
				.andExpect(jsonPath("$.name", is(productDTO1.getName())))
				.andExpect(jsonPath("$.description", is(productDTO1.getDescription())));
	}
	
	@Test
	@DisplayName(value = "update product")
	void change_fields_product_success() throws Exception{
		when(product.update(productDTO1.getId(),productDTO1)).thenReturn(new ResponseEntity<ProductDTO>(productDTO1, HttpStatus.OK));
		this.mockMvc.perform(put("/products/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapToJson(productDTO1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(productDTO1.getId().intValue())))
				.andExpect(jsonPath("$.name", is(productDTO1.getName())));
	}
	
	@Test
	@DisplayName(value = "partial update product")
	void change_partial_fields_product_success() throws Exception{
		when(product.patch(productDTO1.getId(),productDTO1)).thenReturn(new ResponseEntity<ProductDTO>(productDTO1, HttpStatus.OK));
		this.mockMvc.perform(patch("/products/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapToJson(productDTO1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(productDTO1.getId().intValue())))
				.andExpect(jsonPath("$.name", is(productDTO1.getName())));
	}
	
	@Test
	@DisplayName(value = "delete product by id")
	void delete_product_by_id_success() throws Exception{
		when(product.delete(1L)).thenReturn(new ResponseEntity<String>("Deleted Successful", HttpStatus.OK));
		this.mockMvc.perform(delete("/products/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is("Deleted Successful")));
	}
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	
	
}
