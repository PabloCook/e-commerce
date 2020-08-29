package ar.com.gl.shop.order.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

import ar.com.gl.shop.order.model.Order;
import ar.com.gl.shop.order.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{

	private OrderRepository orderRepository;
	
	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@Override
	public Order create(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public Order getById(Long id) {
		Optional<Order> order = orderRepository.findById(id);
		if(order.isPresent()) return order.get();
		return null;
	}

	@Override
	public List<Order> getOrdersByCustomer(Long customerId) {
		return orderRepository.findByCustomerId(customerId);
	}

	@Override
	public List<Order> getOrdersByProduct(Long productId) {
		return orderRepository.findByProductId(productId);
	}

	@Override
	public Order update(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public void delete(Long id) {
		Order order = getById(id);
		if(nonNull(order)) orderRepository.delete(order);
	}
	
	
}
