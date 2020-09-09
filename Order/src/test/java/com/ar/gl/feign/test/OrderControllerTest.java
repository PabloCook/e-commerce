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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.ar.gl.feign.controller.OrderController;
import com.ar.gl.feign.dto.OrderDTO;
import com.ar.gl.feign.dto.ResponseOrderDTO;
import com.ar.gl.feign.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = OrderController.class)
@ActiveProfiles("test")
public class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	OrderServiceImpl orderService;
	
	OrderDTO orderDTO1,orderDTO2,orderDTO3;
	ResponseOrderDTO responseOrderDTO1,responseOrderDTO2,responseOrderDTO3;
	
	List<ResponseOrderDTO> listResponseOrderDTO = new ArrayList<>();
	List<ResponseOrderDTO> listResponseOrderDTOWithCustomerOne = new ArrayList<>();
	List<ResponseOrderDTO> listResponseOrderDTOWithProductTwo = new ArrayList<>();	
	List<ResponseOrderDTO> listResponseOrderDTOWithTwoElements = new ArrayList<>();
	
	@BeforeEach
	void setup() {
		orderDTO1 = OrderDTO.builder().customerId(1L).disable(false).id(1L).productId(1L).quantity(1).totalPrice(200D).build();
		orderDTO2 = OrderDTO.builder().customerId(1L).disable(false).id(2L).productId(2L).quantity(2).totalPrice(100D).build();
		orderDTO3 = OrderDTO.builder().customerId(2L).disable(false).id(3L).productId(2L).quantity(2).totalPrice(100D).build();
		
		responseOrderDTO1 = ResponseOrderDTO.builder()
				.id(orderDTO1.getId())
				.quantity(orderDTO1.getQuantity())
				.totalPrice(orderDTO1.getTotalPrice())
				.disable(orderDTO1.getDisable())
				.productId(orderDTO1.getProductId())
				.productName("product1")
				.productDescription("descriptionProduct1")
				.productPrice(200D)
				.categoryName("categoryName1")
				.categoryDescription("categoryDescription1")
				.customerId(orderDTO1.getCustomerId())
				.customerName("customerName1")
				.customerSurname("customerSurname1")
				.customerDni("customerDni1")
				.build();
		responseOrderDTO2 = ResponseOrderDTO.builder()
				.id(orderDTO2.getId())
				.quantity(orderDTO2.getQuantity())
				.totalPrice(orderDTO2.getTotalPrice())
				.disable(orderDTO2.getDisable())
				.productId(orderDTO2.getProductId())
				.productName("product2")
				.productDescription("descriptionProduct2")
				.productPrice(50D)
				.categoryName("categoryName2")
				.categoryDescription("categoryDescription2")
				.customerId(orderDTO2.getCustomerId())
				.customerName("customerName1")
				.customerSurname("customerSurname1")
				.customerDni("customerDni1")
				.build();
		responseOrderDTO3 = ResponseOrderDTO.builder()
				.id(orderDTO3.getId())
				.quantity(orderDTO3.getQuantity())
				.totalPrice(orderDTO3.getTotalPrice())
				.disable(orderDTO3.getDisable())
				.productId(orderDTO3.getProductId())
				.productName("product2")
				.productDescription("descriptionProduct2")
				.productPrice(50D)
				.categoryName("categoryName2")
				.categoryDescription("categoryDescription2")
				.customerId(orderDTO3.getCustomerId())
				.customerName("customerName2")
				.customerSurname("customerSurname2")
				.customerDni("customerDni2")
				.build();
		
		listResponseOrderDTOWithCustomerOne.add(responseOrderDTO1);
		listResponseOrderDTOWithCustomerOne.add(responseOrderDTO2);
		listResponseOrderDTOWithProductTwo.add(responseOrderDTO2);
		listResponseOrderDTOWithProductTwo.add(responseOrderDTO3);
		listResponseOrderDTOWithTwoElements.add(responseOrderDTO1);
		listResponseOrderDTOWithTwoElements.add(responseOrderDTO2);
		listResponseOrderDTO.add(responseOrderDTO1);
		listResponseOrderDTO.add(responseOrderDTO2);
		listResponseOrderDTO.add(responseOrderDTO3);
	}
	
	@Test
	@DisplayName(value = "getAll Success")
	void all_orders_when_success() throws Exception {
		when(orderService.getAllOrders()).thenReturn(new ResponseEntity<List<ResponseOrderDTO>>(listResponseOrderDTO, HttpStatus.OK));
		this.mockMvc.perform(get("/orders"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()", is(listResponseOrderDTO.size())));
	}
	@Test
	@DisplayName(value = "get page Order Success")
	void all_orders_peggable_when_success() throws Exception {
		Pageable pageable = PageRequest.of(0,2);
		when(orderService.getAllOrders(pageable)).thenReturn(new ResponseEntity<List<ResponseOrderDTO>>(listResponseOrderDTOWithTwoElements, HttpStatus.OK));
		this.mockMvc.perform(get("/orders/pageable?page=0&size=2"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()", is(listResponseOrderDTOWithTwoElements.size())));
	}
	
	@Test
	@DisplayName(value = "create order succes")
	void create_a_new_order_success() throws Exception{
		when(orderService.create(orderDTO1)).thenReturn(new ResponseEntity<ResponseOrderDTO>(responseOrderDTO1, HttpStatus.CREATED));
		this.mockMvc.perform(post("/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapToJson(orderDTO1)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(responseOrderDTO1.getId().intValue())))
				.andExpect(jsonPath("$.customerName", is(responseOrderDTO1.getCustomerName())))
				.andExpect(jsonPath("$.productName", is(responseOrderDTO1.getProductName())));
	}
	
	@Test
	@DisplayName(value = "order by id")
	void order_by_id_success() throws Exception{
		when(orderService.get(1L)).thenReturn(new ResponseEntity<ResponseOrderDTO>(responseOrderDTO1, HttpStatus.OK));
		this.mockMvc.perform(get("/orders/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(responseOrderDTO1.getId().intValue())));
	}
	
	@Test
	@DisplayName(value = "order by product id")
	void order_by_product_id_success() throws Exception{
		when(orderService.getOrdersByProduct(2L)).thenReturn(new ResponseEntity<List<ResponseOrderDTO>>(listResponseOrderDTOWithProductTwo, HttpStatus.OK));
		this.mockMvc.perform(get("/orders/product/2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(listResponseOrderDTOWithProductTwo.size())));
	}
	
	@Test
	@DisplayName(value = "order by customer id")
	void order_by_customer_id_success() throws Exception{
		when(orderService.getOrdersByCustomer(1L)).thenReturn(new ResponseEntity<List<ResponseOrderDTO>>(listResponseOrderDTOWithCustomerOne, HttpStatus.OK));
		this.mockMvc.perform(get("/orders/customer/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(listResponseOrderDTOWithCustomerOne.size())));
	}
	
	@Test
	@DisplayName(value = "update order")
	void update_fields_order_success() throws Exception{
		when(orderService.update(orderDTO1.getId(),orderDTO1)).thenReturn(new ResponseEntity<ResponseOrderDTO>(responseOrderDTO1, HttpStatus.OK));
		this.mockMvc.perform(put("/orders/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapToJson(orderDTO1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(responseOrderDTO1.getId().intValue())));
	}
	
	@Test
	@DisplayName(value = "delete order by id")
	void delete_order_by_id_success() throws Exception{
		when(orderService.delete(1L)).thenReturn(new ResponseEntity<ResponseOrderDTO>(responseOrderDTO1, HttpStatus.OK));
		this.mockMvc.perform(delete("/orders/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(responseOrderDTO1.getId().intValue())));
	}
	
	@Test
	@DisplayName(value = "softdelete order by id")
	void softdelete_order_by_id_success() throws Exception{
		when(orderService.softDelete(1L)).thenReturn(new ResponseEntity<ResponseOrderDTO>(responseOrderDTO1, HttpStatus.OK));
		this.mockMvc.perform(patch("/orders/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(responseOrderDTO1.getId().intValue())));
	}
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	

}
