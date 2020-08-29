package ar.com.gl.shop.order.service;

import java.util.List;

import ar.com.gl.shop.order.model.Order;

public interface OrderService {

	public Order create(Order order);
	
	public Order getById(Long id);
	
	public List<Order> getOrdersByCustomer(Long customerId);

	public List<Order> getOrdersByProduct(Long productId);
	
	public Order update(Order order);
	
	public void delete(Long id);
}
