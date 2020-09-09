package ar.com.gl.shop.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import ar.com.gl.shop.order.dto.OrderDTO;
import ar.com.gl.shop.order.model.Order;
import ar.com.gl.shop.order.repository.OrderRepository;
import ar.com.gl.shop.order.service.impl.OrderServiceImpl;
import ar.com.gl.shop.order.utils.OrderDTOConverter;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
	
	@Mock
	OrderRepository orderRepository;
	
	@Mock
	OrderDTOConverter orderDTOConverter;
	
	@InjectMocks
	OrderServiceImpl orderService;
	
	Order order;
	Order order2;
	List<Order> orders = new ArrayList<>();
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
		orderDTO = new OrderDTO(1L, 4L, 2L, 10, 20.0, false,LocalDate.now());
		pageable = PageRequest.of(1, 10);
		pageOrder = new PageImpl<>(orders);
	}
	
	@Test
	@DisplayName("create")
	void testCase_1() {
		when(orderRepository.save(order)).thenReturn(order2);
		assertEquals(order2, orderService.create(order));
	}
	
	@Test
	@DisplayName("getById")
	void testCase_2() {
		when(orderRepository.findById(1L)).thenReturn(Optional.of(order2));
		assertEquals(order2, orderService.getById(1L));
	}
	
	@Test
	@DisplayName("getById Error")
	void testCase_3() {
		when(orderRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(NoSuchElementException.class, ()-> orderService.getById(1L));
	}
	
	@Test
	@DisplayName("getAll")
	void testCase_4() {
		when(orderRepository.findAll()).thenReturn(orders);
		assertEquals(orders, orderService.getAll());
	}
	
	@Test
	@DisplayName("getOrdersByCustomer")
	void testCase_5() {
		when(orderRepository.findByCustomerId(2L)).thenReturn(orders);
		assertEquals(orders, orderService.getOrdersByCustomer(2L));
	}
	
	@Test
	@DisplayName("getOrdersByProduct")
	void testCase_6() {
		when(orderRepository.findByProductId(4L)).thenReturn(orders);
		assertEquals(orders, orderService.getOrdersByProduct(4L));
	}
	
	@Test
	@DisplayName("update")
	void testCase_7() {
		when(orderDTOConverter.toEntity(orderDTO, order2)).thenReturn(order2);
		when(orderRepository.findById(1L)).thenReturn(Optional.of(order2));
		when(orderRepository.save(order2)).thenReturn(order2);
		assertEquals(order2, orderService.update(orderDTO, 1L));
	}
	
	@Test
	@DisplayName("update error")
	void testCase_8() {
		when(orderRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(NoSuchElementException.class, ()-> orderService.update(orderDTO, 1L));
	}
	
	@Test
	@DisplayName("delete")
	void testCase_9() {
		when(orderRepository.findById(1L)).thenReturn(Optional.of(order2));
		orderService.delete(1L);
		verify(orderRepository).delete(order2);
	}
	
	@Test
	@DisplayName("delete id=null")
	void testCase_10() {
		when(orderRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(NoSuchElementException.class, ()-> orderService.delete(1L));
	}
	
	@Test
	@DisplayName("softDelete")
	void testCase_11() {
		when(orderRepository.findById(1L)).thenReturn(Optional.of(order2));
		when(orderRepository.save(order2)).thenReturn(order2);
		order2.setDisable(true);
		assertEquals(order2, orderService.softDelete(1L));
	}
	
	@Test
	@DisplayName("softDelete error")
	void testCase_12() {
		order2.setDisable(true);
		when(orderRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(NoSuchElementException.class, ()-> orderService.softDelete(1L));
	}
	
	@Test
	@DisplayName("update id=null")
	void testCase_13() {
		assertNull(orderService.update(orderDTO, null));
	}
	
	@Test
	@DisplayName("softDelete id=null")
	void testCase_14() {
		assertNull(orderService.softDelete(null));
	}
	
	@Test
	@DisplayName("getAll pageable")
	void testCase_15() {
		when(orderRepository.findAll(pageable)).thenReturn(pageOrder);
		assertEquals(pageOrder.toList(), orderService.getAll(pageable));
	}
	
}
