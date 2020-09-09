package ar.com.gl.shop.order.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.com.gl.shop.order.dto.OrderDTO;
import ar.com.gl.shop.order.model.Order;


@Component
public class OrderDTOConverter extends Converter{

	public Order toEntity(OrderDTO orderDTO) {
		return (Order) super.fromTo(orderDTO, Order.class);
	}

	
	public OrderDTO toDTO(Order order) {
		return (OrderDTO) super.fromTo(order, OrderDTO.class);
	}
	
	
	public List<OrderDTO> toDTOList(List<Order> orders) {
		return orders.stream().map(o -> toDTO(o)).collect(Collectors.toList());
	}
	
	public Order toEntity(OrderDTO orderDTO, Order prod) {
		return (Order) super.fromTo(orderDTO, prod);
	}
}
