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

import com.ar.gl.feign.controller.CustomerController;
import com.ar.gl.feign.dto.CustomerDTO;
import com.ar.gl.feign.shop.product.FeignCustomer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = CustomerController.class)
@ActiveProfiles("test")
public class CustomerControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	FeignCustomer customer;
	
	CustomerDTO customerDTO1,customerDTO2,customerDTO3;
	List<CustomerDTO> listCustomerDTO = new ArrayList<>();
	
	@BeforeEach
	void setup() {
		customerDTO1 = CustomerDTO.builder()
			.id(1L)
			.name("name1")
			.dni("1")
			.surname("surname1")
			.build();
		customerDTO2 = CustomerDTO.builder()
			.id(2L)
			.name("name2")
			.dni("2")
			.surname("surname2")
			.build();
		customerDTO3 = CustomerDTO.builder()
			.id(3L)
			.name("name3")
			.dni("3")
			.surname("surname3")
			.build();
			
		listCustomerDTO.add(customerDTO1);
		listCustomerDTO.add(customerDTO2);
		listCustomerDTO.add(customerDTO3);
	}

	@Test
	@DisplayName(value = "getAllSuccess")
	void all_customers_when_success() throws Exception {
		when(customer.findAll()).thenReturn(new ResponseEntity<List<CustomerDTO>>(listCustomerDTO, HttpStatus.OK));
		this.mockMvc.perform(get("/customers"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()", is(listCustomerDTO.size())));
	}
	
	@Test
	@DisplayName(value = "customer by id")
	void customer_by_id_success() throws Exception{
		when(customer.getById(1L)).thenReturn(new ResponseEntity<CustomerDTO>(customerDTO1, HttpStatus.OK));
		this.mockMvc.perform(get("/customers/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(customerDTO1.getName())));
	}
	
	@Test
	@DisplayName(value = "create customer succes")
	void create_a_new_customer_success() throws Exception{
		when(customer.save(customerDTO1)).thenReturn(new ResponseEntity<CustomerDTO>(customerDTO1, HttpStatus.CREATED));
		this.mockMvc.perform(post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapToJson(customerDTO1)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(customerDTO1.getId().intValue())))
				.andExpect(jsonPath("$.name", is(customerDTO1.getName())));
	}
	
	@Test
	@DisplayName(value = "update customer")
	void change_fields_customer_success() throws Exception{
		when(customer.update(customerDTO1.getId(),customerDTO1)).thenReturn(new ResponseEntity<CustomerDTO>(customerDTO1, HttpStatus.OK));
		this.mockMvc.perform(put("/customers/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapToJson(customerDTO1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(customerDTO1.getId().intValue())))
				.andExpect(jsonPath("$.name", is(customerDTO1.getName())));
	}
	
	@Test
	@DisplayName(value = "partial update customer")
	void change_partial_fields_customer_success() throws Exception{
		when(customer.partialUpdate(customerDTO1.getId(),customerDTO1)).thenReturn(new ResponseEntity<CustomerDTO>(customerDTO1, HttpStatus.OK));
		this.mockMvc.perform(patch("/customers/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapToJson(customerDTO1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(customerDTO1.getId().intValue())))
				.andExpect(jsonPath("$.name", is(customerDTO1.getName())));
	}
	
	@Test
	@DisplayName(value = "restore customer")
	void restore_customer_success() throws Exception{
		when(customer.restore(customerDTO1.getId())).thenReturn(new ResponseEntity<CustomerDTO>(customerDTO1, HttpStatus.OK));
		this.mockMvc.perform(patch("/customersrestore/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(customerDTO1.getId().intValue())))
				.andExpect(jsonPath("$.name", is(customerDTO1.getName())));
	}
	
	@Test
	@DisplayName(value = "delete customer by id")
	void delete_customer_by_id_success() throws Exception{
		when(customer.delete(1L)).thenReturn(new ResponseEntity<String>("Deleted Successful", HttpStatus.OK));
		this.mockMvc.perform(delete("/customers/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is("Deleted Successful")));
	}
	
	@Test
	@DisplayName(value = "soft delete customer")
	void soft_delete_customer_success() throws Exception{
		when(customer.softDelete(customerDTO1.getId())).thenReturn(new ResponseEntity<String>("soft delete", HttpStatus.OK));
		this.mockMvc.perform(delete("/customers/softdelete/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is("soft delete")));
	}
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	
}
