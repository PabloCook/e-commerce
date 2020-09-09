package ar.com.gl.shop.order.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;

import ar.com.gl.shop.order.dto.OrderDTO;
import ar.com.gl.shop.order.model.Order;

public interface OrderService {

	public Order create(Order order);
	
	public Order getById(Long id);
	
	public List<Order> getAll(Pageable pageable);
	
	public List<Order> getAll();
	
	public List<Order> getOrdersByCustomer(Long customerId);

	public List<Order> getOrdersByProduct(Long productId);
	
	public Order update(OrderDTO orderDTO, Long id);
	
	public void delete(Long id);
	
	public Order softDelete(Long id);
	
	public default LocalDate getCurrentLocalDate()
	{
		return LocalDate.now();
	}
}
