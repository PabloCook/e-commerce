package ar.com.gl.shop.order.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

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

import ar.com.gl.shop.order.dto.OrderDTO;
import ar.com.gl.shop.order.model.Order;
import ar.com.gl.shop.order.utils.Converter;
import ar.com.gl.shop.order.utils.OrderDTOConverter;

@ExtendWith(MockitoExtension.class)
public class ConverterDTOTest {

	@Mock
	ModelMapper modelMapperMock;

	@InjectMocks
	Converter converter;
	
	
	OrderDTOConverter orderDTOConverter;

	OrderDTO orderDTO;
	Order order;
	Order order2;

	List<Order> orders = new ArrayList<>();
	List<OrderDTO> ordersDTO = new ArrayList<>();;

	@BeforeEach
	void setUp() {
		orderDTOConverter = new OrderDTOConverter();
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
	}
	
	
	
	@Test
	@DisplayName("toEntity")
	void testCase_1(){
		lenient().when(modelMapperMock.map(orderDTO, Order.class)).thenReturn(order);
		
		assertEquals(order, orderDTOConverter.toEntity(orderDTO));
	}
	
	@Test
	@DisplayName("toDto")
	void testCase_2(){
		lenient().when(modelMapperMock.map(order, OrderDTO.class)).thenReturn(orderDTO);
		
		assertEquals(orderDTO, 	orderDTOConverter.toDTO(order));
	}
	
	@Test
	@DisplayName("toDtoList")
	void testCase_3(){
		lenient().when(modelMapperMock.map(order, OrderDTO.class)).thenReturn(orderDTO);
		
		assertEquals(ordersDTO, orderDTOConverter.toDTOList(orders));
	}
	
	@Test
	@DisplayName("toEntity") //Para update
	void testCase_4(){
		lenient().when(modelMapperMock.map(orderDTO, Order.class)).thenReturn(order);
		
		orderDTO.setCustomerId(2L);
		Order order2 = order;
		order2.setCustomerId(2L);
		
		assertEquals(order2, orderDTOConverter.toEntity(orderDTO, order));
	}

}
