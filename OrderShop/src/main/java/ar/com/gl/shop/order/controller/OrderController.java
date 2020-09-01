package ar.com.gl.shop.order.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ar.com.gl.shop.order.dto.OrderDTO;
import ar.com.gl.shop.order.service.OrderService;
import ar.com.gl.shop.order.utils.OrderDTOConverter;

@Controller
public class OrderController {

	private OrderService orderService;
	private OrderDTOConverter orderDTOConverter;
	
	public OrderController(OrderService orderService, OrderDTOConverter orderDTOConverter) {
		this.orderService = orderService;
		this.orderDTOConverter = orderDTOConverter;
	}
	
	
	@PostMapping(value = "/orders")
	public ResponseEntity<OrderDTO> create(@Valid @RequestBody OrderDTO orderDTO){
		return new ResponseEntity<OrderDTO>(orderDTOConverter.toDTO(orderService.create(orderDTOConverter.toEntity(orderDTO))), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/orders")
	public ResponseEntity<List<OrderDTO>> findPage(@PageableDefault(page=0, size=100) Pageable pageable){
		return new ResponseEntity<List<OrderDTO>>(orderDTOConverter.toDTOList(orderService.getAll(pageable)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/orders/{id}")
	public ResponseEntity<OrderDTO> getById(@PathVariable("id") Long id){
		return new ResponseEntity<OrderDTO>(orderDTOConverter.toDTO(orderService.getById(id)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/orders/customer/{id}")
	public ResponseEntity<List<OrderDTO>> getOrdersByCustomer(@PathVariable("id") Long id){
		return new ResponseEntity<List<OrderDTO>>(orderDTOConverter.toDTOList(orderService.getOrdersByCustomer(id)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/orders/product/{id}")
	public ResponseEntity<List<OrderDTO>> getOrdersByProduct(@PathVariable("id") Long id){
		return new ResponseEntity<List<OrderDTO>>(orderDTOConverter.toDTOList(orderService.getOrdersByProduct(id)), HttpStatus.OK);
	}
	
	@PutMapping(value = "/orders/{id}")
	public ResponseEntity<OrderDTO> update( @PathVariable("id") Long id, @Valid @RequestBody OrderDTO orderDTO){
		return new ResponseEntity<OrderDTO>(orderDTOConverter.toDTO(orderService.update(orderDTO, id)), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/orders/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id){
		orderService.delete(id);
		return new ResponseEntity<String>("Orden eliminada", HttpStatus.OK);
	}
	
	@PatchMapping(value = "/orders/{id}")
	public ResponseEntity<OrderDTO> softDelete(@PathVariable("id") Long id){
		return new ResponseEntity<OrderDTO>(orderDTOConverter.toDTO(orderService.softDelete(id)), HttpStatus.OK);
	}
}
