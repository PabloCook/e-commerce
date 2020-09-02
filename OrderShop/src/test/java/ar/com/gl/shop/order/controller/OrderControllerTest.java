package ar.com.gl.shop.order.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.gl.shop.order.dto.OrderDTO;
import ar.com.gl.shop.order.model.Order;
import ar.com.gl.shop.order.service.impl.OrderServiceImpl;
import ar.com.gl.shop.order.utils.OrderDTOConverter;

//@WebMvcTest(controllers = OrderController.class)
//@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

	//@Autowired
	//private MockMvc mockMvc;

	@Mock
	OrderServiceImpl orderService;

	@Mock
	OrderDTOConverter orderDTOConverter;

	@InjectMocks
	OrderController orderController;

	Order order;
	Order order2;
	List<Order> orders = new ArrayList<>();
	List<OrderDTO> ordersDTO = new ArrayList<>();
	OrderDTO orderDTO;
	Pageable pageable;
	Page<Order> pageOrder;

	@BeforeEach
	void setUp() {
		order = new Order(4L, 2L, 10);
		order.setId(1L);
		order2 = order;
		order2.setDisable(false);
		order2.setTotalPrice(20.0);
		orders.add(order2);
		orders.add(order);
		orderDTO = new OrderDTO(1L, 4L, 2L, 10, 20.0, false);
		ordersDTO.add(orderDTO);
		ordersDTO.add(orderDTO);
		pageable = PageRequest.of(1, 10);
		pageOrder = new PageImpl<>(orders);
	}

	@Test
	@DisplayName("post")
	void testCase_1() {
		when(orderDTOConverter.toEntity(orderDTO)).thenReturn(order);
		when(orderService.create(order)).thenReturn(order2);
		when(orderDTOConverter.toDTO(order2)).thenReturn(orderDTO);
		ResponseEntity<OrderDTO> response = new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
		assertEquals(response, orderController.create(orderDTO));
	}

	@Test
	@DisplayName("getAll")
	void testCase_2() {
		when(orderDTOConverter.toDTOList(orders)).thenReturn(ordersDTO);
		when(orderService.getAll()).thenReturn(orders);
		ResponseEntity<List<OrderDTO>> response = new ResponseEntity<>(ordersDTO, HttpStatus.OK);
		assertEquals(response, orderController.getAll());
	}

	@Test
	@DisplayName("getAll pageable")
	void testCase_3() {
		when(orderDTOConverter.toDTOList(orders)).thenReturn(ordersDTO);
		when(orderService.getAll(pageable)).thenReturn(orders);
		ResponseEntity<List<OrderDTO>> response = new ResponseEntity<>(ordersDTO, HttpStatus.OK);
		assertEquals(response, orderController.getAll(pageable));
	}

	@Test
	@DisplayName("getById")
	void testCase_4() {
		lenient().when(orderDTOConverter.toDTO(order2)).thenReturn(orderDTO);
		when(orderService.getById(1L)).thenReturn(order2);
		ResponseEntity<OrderDTO> response = new ResponseEntity<>(orderDTO, HttpStatus.OK);
		assertEquals(response, orderController.getById(1L));
	}

	@Test
	@DisplayName("getOrdersByCustomer")
	void testCase_5() {
		when(orderDTOConverter.toDTOList(orders)).thenReturn(ordersDTO);
		when(orderService.getOrdersByCustomer(2L)).thenReturn(orders);
		ResponseEntity<List<OrderDTO>> response = new ResponseEntity<>(ordersDTO, HttpStatus.OK);
		assertEquals(response, orderController.getOrdersByCustomer(2L));
	}

	@Test
	@DisplayName("getOrdersByProduct")
	void testCase_6() {
		when(orderDTOConverter.toDTOList(orders)).thenReturn(ordersDTO);
		when(orderService.getOrdersByProduct(4L)).thenReturn(orders);
		ResponseEntity<List<OrderDTO>> response = new ResponseEntity<>(ordersDTO, HttpStatus.OK);
		assertEquals(response, orderController.getOrdersByProduct(4L));
	}

	@Test
	@DisplayName("update")
	void testCase_7() {
		when(orderDTOConverter.toDTO(order2)).thenReturn(orderDTO);
		when(orderService.update(orderDTO, 1L)).thenReturn(order2);
		ResponseEntity<OrderDTO> response = new ResponseEntity<>(orderDTO, HttpStatus.OK);
		assertEquals(response, orderController.update(1L, orderDTO));
	}

	@Test
	@DisplayName("delete")
	void testCase_8() {
		orderController.delete(1L);
		verify(orderService).delete(1L);
		ResponseEntity<String> response = new ResponseEntity<>("Orden eliminada", HttpStatus.OK);
		assertEquals(response, orderController.delete(1L));
	}
	
	@Test
	@DisplayName("softDelete")
	void testCase_9() {
		when(orderDTOConverter.toDTO(order2)).thenReturn(orderDTO);
		when(orderService.softDelete(1L)).thenReturn(order2);
		ResponseEntity<OrderDTO> response = new ResponseEntity<>(orderDTO, HttpStatus.OK);
		assertEquals(response, orderController.softDelete(1L));
	}


}
