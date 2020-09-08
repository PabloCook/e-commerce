package com.ar.gl.customer.shop.Customershop;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;
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

import com.ar.gl.customer.shop.controller.CustomerController;
import com.ar.gl.customer.shop.dto.CustomerDTO;
import com.ar.gl.customer.shop.model.Customer;
import com.ar.gl.customer.shop.service.impl.CustomerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(controllers = CustomerController.class)
@ActiveProfiles("test")
class CustomerControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CustomerServiceImpl customerServiceMock;

	
	Customer customer;
	CustomerDTO customerDTO;
	

	List<Customer> customerList;
	List<CustomerDTO> customerDTOList;

	@BeforeEach
	public void setUp() {
		customer = new Customer(1L, "CustomerName", "CustomerSN", "123");
		customerDTO = new CustomerDTO(1L, "CustomerName", "CustomerSN", "123", true);
		customerList = new ArrayList<Customer>();
		customerDTOList = new ArrayList<CustomerDTO>();
		customerList.add(customer);
		customerDTOList.add(customerDTO);
		
	}
	
	@Test
	@DisplayName("findAll_Successful")
	void testCase_0() throws Exception {
		when(customerServiceMock.findAll()).thenReturn(customerDTOList);
		this.mockMvc.perform(get("/customers"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()",is(customerDTOList.size())));

	}
	
	@Test
	@DisplayName("get_by_id_successful")
	void testCase_1() throws Exception{
		when(customerServiceMock.findById(customer.getId())).thenReturn(customerDTO);
		
		this.mockMvc.perform(get("/customers/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name",is(customer.getName())))
		.andExpect(jsonPath("$.surname",is(customer.getSurname())))
		.andExpect(jsonPath("$.dni",is(customer.getDni())));
		
	}
	
	@Test 
	  @DisplayName("Put_by_id_When_product_NOT_Exist_Successful") 
	  void testCase_2() throws Exception{
	  
	  
	  when(customerServiceMock.save(customerDTO)).thenReturn(customerDTO);
	  
	  this.mockMvc.perform(post("/customers")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(this.mapToJson(customerDTO))) 
	  	.andExpect(status().isCreated())
		.andExpect(jsonPath("$.name",is(customerDTO.getName())))
		.andExpect(jsonPath("$.surname",is(customerDTO.getSurname())))
		.andExpect(jsonPath("$.dni",is(customerDTO.getDni())));
	  
	  }
	
	@Test 
	  @DisplayName("Put_by_id_When_product_Exist_Successful") 
	  void testCase_3() throws Exception{
	  when(customerServiceMock.update(customer.getId(),customerDTO)).thenReturn(customerDTO);
	  
	  this.mockMvc.perform(put("/customers/1")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(this.mapToJson(customerDTO))) 
	  	.andExpect(status().isOk())
		.andExpect(jsonPath("$.name",is(customerDTO.getName())))
		.andExpect(jsonPath("$.surname",is(customerDTO.getSurname())))
		.andExpect(jsonPath("$.dni",is(customerDTO.getDni())));
	  
	  }
	
	@Test 
	  @DisplayName("update patch") 
	  void testCase_4() throws Exception{
	  when(customerServiceMock.update(customer.getId(),customerDTO)).thenReturn(customerDTO);
	  
	  this.mockMvc.perform(patch("/customers/1")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(this.mapToJson(customerDTO))) 
	  	.andExpect(status().isOk())
		.andExpect(jsonPath("$.name",is(customerDTO.getName())))
		.andExpect(jsonPath("$.surname",is(customerDTO.getSurname())))
		.andExpect(jsonPath("$.dni",is(customerDTO.getDni())));
	  
	  }
	@Test
	@DisplayName(value = "restore")
	void CustomerRestore() throws Exception {
		when(customerServiceMock.restore(1L)).thenReturn(customerDTO);
		this.mockMvc.perform(patch("/customers/restore/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", is(customerDTO.getName())));
	}
	@Test
	@DisplayName(value="softDelete")
	void softDelete() throws Exception {
		this.mockMvc.perform(delete("/customers/softdelete/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is("soft deleted customer")));
		verify(customerServiceMock).softDelete(1L);
	}
	
	 private String mapToJson(Object object) throws JsonProcessingException {
		  ObjectMapper objectMapper = new ObjectMapper();
		  return objectMapper.writeValueAsString(object);
	  }
	 
	 @Test
		@DisplayName(value="delete")
		void Customerdelete() throws Exception{
			this.mockMvc.perform(delete("/customers/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", is("deleted")));
			verify(customerServiceMock).delete(1L);
		}
}
