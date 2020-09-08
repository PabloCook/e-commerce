package ar.com.gl.shop.product.servicesimpl.test;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.gl.shop.product.controller.ProductController;
import ar.com.gl.shop.product.dto.ProductDTO;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.service.impl.ProductServiceImpl;
import ar.com.gl.shop.product.utils.ProductDTOConverter;


//@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ProductController.class)
@ActiveProfiles("test")
public class ProductControllerTest {
	
	@Autowired                           
    private MockMvc mockMvc; 
	
	@MockBean
	ProductServiceImpl productService;
	
	@MockBean
	ProductDTOConverter productConverter;
	
	ProductDTO productDTO1;
	Product product1;
	Category category1;
	Stock stock1;
	
	
	List<Product> listProduct = new ArrayList<Product>();
	List<Product> listProductEnabled = new ArrayList<Product>();
	List<ProductDTO> listProductDTO = new ArrayList<ProductDTO>();
	
	@BeforeEach
	public void setUp() {
		category1 = new Category(
				"category1",
				"descCategory1");
		category1.setId(1L);
		
		stock1 = new Stock(30, "SJ");
		stock1.setId(1L);
		
		product1 = new Product(
				"Test product",
				"Product for testing",
				500.0,category1);
		product1.setId(1L);
		
		product1.setStock(stock1);
		productDTO1 = new ProductDTO(
				product1.getId(),
				product1.getName(),
				product1.getDescription(),
				product1.getPrice(),
				product1.getEnabled(),
				product1.getDate(),
				product1.getCategory().getId(),
				product1.getCategory().getName(),
				product1.getCategory().getDescription(),
				product1.getCategory().getEnabled(),
				product1.getStock().getId(),
				product1.getStock().getQuantity(),
				product1.getStock().getLocationCode()
		);
		
		listProduct.add(product1);
		listProductEnabled.add(product1);
		listProductDTO.add(productDTO1);
	}
	
	@Test
	@DisplayName("findAll_Successful")
	void testCase_0() throws Exception {
		when(productService.findAll()).thenReturn(listProductEnabled);
		when(productConverter.toDTOList(productService.findAll())).thenReturn(listProductDTO);
		this.mockMvc.perform(get("/products"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()",is(listProductDTO.size())));

	}
	
	@Test
	@DisplayName("get_by_id_successful")
	void testCase_1() throws Exception{
		when(productService.getById(product1.getId(),true)).thenReturn(product1);
		when(productConverter.toDTO(product1)).thenReturn(productDTO1);
		
		this.mockMvc.perform(get("/products/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name",is(productDTO1.getName())))
		.andExpect(jsonPath("$.description",is(productDTO1.getDescription())))
		.andExpect(jsonPath("$.price",is(productDTO1.getPrice())));
		//etc
	}
	
	
	  @Test 
	  @DisplayName("Put_by_id_When_product_Exist_Successful") 
	  void testCase_2() throws Exception{
	  
	  when(productService.getById(product1.getId(),false)).thenReturn(product1);
	  when(productConverter.toDTO(product1)).thenReturn(productDTO1);
	  when(productService.update(productDTO1, product1)).thenReturn(product1);
	  
	  this.mockMvc.perform(put("/products/1")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(this.mapToJson(productDTO1))) 
	  	.andExpect(status().isOk())
		.andExpect(jsonPath("$.name",is(productDTO1.getName())))
		.andExpect(jsonPath("$.description",is(productDTO1.getDescription())))
		.andExpect(jsonPath("$.price",is(productDTO1.getPrice())));
	  
	  }
	  
	  @Test 
	  @DisplayName("Put_by_id_When_product_NOT_Exist_Successful") 
	  void testCase_2_5() throws Exception{
	  
	  when(productService.getById(product1.getId(),false)).thenReturn(null);
	  when(productConverter.toEntity(productDTO1)).thenReturn(product1);
	  when(productService.create(product1)).thenReturn(product1);
	  when(productConverter.toDTO(product1)).thenReturn(productDTO1);
	  
	  this.mockMvc.perform(put("/products/1")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(this.mapToJson(productDTO1))) 
	  	.andExpect(status().isOk())
		.andExpect(jsonPath("$.name",is(productDTO1.getName())))
		.andExpect(jsonPath("$.description",is(productDTO1.getDescription())))
		.andExpect(jsonPath("$.price",is(productDTO1.getPrice())));
	  
	  }
	  
	  @Test 
	  @DisplayName("patch_successful") 
	  void testCase_3() throws Exception{
	  
	  when(productService.getById(product1.getId(),false)).thenReturn(product1);
	  when(productService.update(productDTO1, product1)).thenReturn(product1);
	  when(productConverter.toDTO(productService.update(productDTO1, product1))).thenReturn(productDTO1);
	  
	  
	  this.mockMvc.perform(
			  patch("/products/1")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(this.mapToJson(productDTO1))) 
	  	.andExpect(status().isOk())
		.andExpect(jsonPath("$.name",is(productDTO1.getName())))
		.andExpect(jsonPath("$.description",is(productDTO1.getDescription())))
		.andExpect(jsonPath("$.price",is(productDTO1.getPrice())));
	  
	  }
	  
	  @Test 
	  @DisplayName("delete_successful") 
	  void testCase_4() throws Exception{
  
	  this.mockMvc.perform(delete("/products/1")) 
	  	.andExpect(status().isOk())
		.andExpect(jsonPath("$",is("Producto Eliminado")));
	  
	  }
	  
	  @Test 
	  @DisplayName("patch_successful") 
	  void testCase_5() throws Exception{
	  
	  when(productConverter.toEntity(productDTO1)).thenReturn(product1);
	  when(productService.create(product1)).thenReturn(product1);
	  when(productConverter.toDTO(product1)).thenReturn(productDTO1);
	  
	  this.mockMvc.perform(post("/products")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(this.mapToJson(productDTO1))) 
	  	.andExpect(status().isCreated())
		.andExpect(jsonPath("$.name",is(productDTO1.getName())))
		.andExpect(jsonPath("$.description",is(productDTO1.getDescription())))
		.andExpect(jsonPath("$.price",is(productDTO1.getPrice())));
	  
	  }
	  
	  @Test
		@DisplayName("get_by_name_successful")
		void testCase_6() throws Exception{
			when(productService.getByName("TestProduct")).thenReturn(product1);
			when(productConverter.toDTO(product1)).thenReturn(productDTO1);
			
			this.mockMvc.perform(get("/products/name/TestProduct"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name",is(productDTO1.getName())))
			.andExpect(jsonPath("$.description",is(productDTO1.getDescription())))
			.andExpect(jsonPath("$.price",is(productDTO1.getPrice())));
			//etc
		}
	  
	  @Test
		@DisplayName("all_prducts_with_category1_successful")
		void testCase_7() throws Exception{
			when(productService.findCategoryById(category1.getId())).thenReturn(listProduct);
			when(productConverter.toDTOList(listProduct)).thenReturn(listProductDTO);
			
			this.mockMvc.perform(get("/products/category/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()",is(listProductDTO.size())));
		}

	  
	  private String mapToJson(Object object) throws JsonProcessingException {
		  ObjectMapper objectMapper = new ObjectMapper();
		  return objectMapper.writeValueAsString(object);
	  }
	 
	
}
