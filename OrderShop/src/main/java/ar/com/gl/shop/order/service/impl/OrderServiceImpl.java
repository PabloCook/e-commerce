package ar.com.gl.shop.order.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

import ar.com.gl.shop.order.dto.OrderDTO;
import ar.com.gl.shop.order.model.Order;
import ar.com.gl.shop.order.repository.OrderRepository;
import ar.com.gl.shop.order.service.OrderService;
import ar.com.gl.shop.order.utils.OrderDTOConverter;

@Service
public class OrderServiceImpl implements OrderService{

	private OrderRepository orderRepository;
	private OrderDTOConverter orderDTOConverter;
	
	public OrderServiceImpl(OrderRepository orderRepository, OrderDTOConverter orderDTOConverter) {
		this.orderRepository = orderRepository;
		this.orderDTOConverter = orderDTOConverter;
	}
	
	@Override
	public Order create(Order order) {
		order.setDisable(false);
		return orderRepository.save(order);
	}

	@Override
	public Order getById(Long id) {
		Optional<Order> order = orderRepository.findById(id);
		if(order.isPresent()) return order.get();
		return null;
	}
	
	@Override
	public List<Order> getAll() {
		return orderRepository.findAll();
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
	public Order update(OrderDTO orderDTO, Long id) {
		Order order = getById(id);
		if(nonNull(order)) {
			order = orderDTOConverter.toEntity(orderDTO, order);
			return orderRepository.save(order);
		}
		return order;
	}

	@Override
	public void delete(Long id) {
		Order order = getById(id);
		if(nonNull(order)) orderRepository.delete(order);
	}

	@Override
	public Order softDelete(Long id) {
		Order order = getById(id);
		if(nonNull(order)) {
			order.setDisable(!order.getDisable());
			order = orderRepository.save(order);
		}
		return order;
	}	
	
}
