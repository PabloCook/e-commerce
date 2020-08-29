package ar.com.gl.shop.order.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gl.shop.order.dto.OrderDTO;
import ar.com.gl.shop.order.service.OrderService;
import ar.com.gl.shop.order.utils.OrderDTOConverter;

@RestController
public class OrderController {

	private OrderService orderService;
	private OrderDTOConverter orderDTOConverter;
	
	public OrderController(OrderService orderService, OrderDTOConverter orderDTOConverter) {
		this.orderService = orderService;
		this.orderDTOConverter = orderDTOConverter;
	}
	
	
	@PostMapping(name = "/orders")
	public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO){
		return new ResponseEntity<OrderDTO>(orderDTOConverter.toDTO(orderService.create(orderDTOConverter.toEntity(orderDTO))), HttpStatus.CREATED);
	}
	
	@GetMapping(name = "/orders")
	public ResponseEntity<List<OrderDTO>> findAll(){
		return new ResponseEntity<List<OrderDTO>>(orderDTOConverter.toDTOList(orderService.getAll()), HttpStatus.OK);
	}
	
	@GetMapping(name = "/orders/{id}")
	public ResponseEntity<OrderDTO> getById(@RequestParam("id") Long id){
		return new ResponseEntity<OrderDTO>(orderDTOConverter.toDTO(orderService.getById(id)), HttpStatus.OK);
	}
	
	@GetMapping(name = "/orders/customer/{id}")
	public ResponseEntity<List<OrderDTO>> getByCustomerId(@RequestParam("id") Long id){
		return new ResponseEntity<List<OrderDTO>>(orderDTOConverter.toDTOList(orderService.getOrdersByCustomer(id)), HttpStatus.OK);
	}
	
	@GetMapping(name = "/orders/customer/{id}")
	public ResponseEntity<List<OrderDTO>> getByProductId(@RequestParam("id") Long id){
		return new ResponseEntity<List<OrderDTO>>(orderDTOConverter.toDTOList(orderService.getOrdersByProduct(id)), HttpStatus.OK);
	}
	
	@PutMapping(name = "/orders/{id}")
	public ResponseEntity<OrderDTO> update(@RequestParam("id") Long id, @RequestBody OrderDTO orderDTO){
		return new ResponseEntity<OrderDTO>(orderDTOConverter.toDTO(orderService.update(orderDTO, id)), HttpStatus.OK);
	}
	
	@DeleteMapping(name = "/orders/{id}")
	public ResponseEntity<String> delete(@RequestParam("id") Long id){
		return new ResponseEntity<String>("Orden eliminada", HttpStatus.OK);
	}
	
	@PatchMapping(name = "/orders/{id}")
	public ResponseEntity<OrderDTO> doftDelete(@RequestParam("id") Long id){
		return new ResponseEntity<OrderDTO>(orderDTOConverter.toDTO(orderService.softDelete(id)), HttpStatus.OK);
	}
}
