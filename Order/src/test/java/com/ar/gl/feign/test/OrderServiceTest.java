package com.ar.gl.feign.test;

import static com.ar.gl.feign.util.Utilities.mergeLists;
import static com.ar.gl.feign.util.Utilities.toResponseDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ar.gl.feign.dto.CustomerDTO;
import com.ar.gl.feign.dto.OrderDTO;
import com.ar.gl.feign.dto.ProductDTO;
import com.ar.gl.feign.dto.ResponseOrderDTO;
import com.ar.gl.feign.service.impl.OrderServiceImpl;
import com.ar.gl.feign.shop.product.FeignCustomer;
import com.ar.gl.feign.shop.product.FeignOrder;
import com.ar.gl.feign.shop.product.FeignProduct;;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
	
	@Mock
	private FeignProduct feignProduct;
	
	@Mock
	private FeignCustomer feignCustomer;
	
	@Mock
	private FeignOrder feignOrder;
	
	@InjectMocks
	private OrderServiceImpl orderServiceImpl;
	
	OrderDTO orderDTOMock;
	
	ProductDTO productDTOMock; 
	
	CustomerDTO customerDTOMock;
	
	ResponseEntity<ProductDTO> responseProductDTOMock;
	
	ResponseEntity<CustomerDTO> responseCustomerDTOMock;
	
	ResponseEntity<OrderDTO> responseOrderDTOMock;
	
	ResponseEntity<List<OrderDTO>> responseEntityOrdersDTO;
	
	ResponseEntity<List<CustomerDTO>> responseEntityCustomerDTO;
	
	ResponseEntity<List<ProductDTO>> responseEntityProductDTO;
	
	List<ProductDTO> productsDTO;
	
	List<OrderDTO> ordersDTO;
	
	List<CustomerDTO> customersDTO;
	
	Pageable pageable;
	
	Page<OrderDTO> pageOrder;
	
	@BeforeEach
	public void prepareTests() {
		
	orderDTOMock = OrderDTO.builder()
							.id(1l)
							.productId(1l)
							.customerId(1l)
							.quantity(50)
							.totalPrice(0.0)
							.disable(false)
							.build();
	
	responseOrderDTOMock = new ResponseEntity<>(orderDTOMock, HttpStatus.OK);
	
	productDTOMock = ProductDTO.builder()
					.id(1l)
					.name("nombre")
					.description("description")
					.price(10.0)
					.categoryId(1l)
					.categoryName("category name")
					.categoryDescription("category descrpition")
					.stockId(1l)
					.stockQuantity(50)
					.stockLocationCode("bs as")
					.build();
	
	responseProductDTOMock = new ResponseEntity<>(productDTOMock, HttpStatus.OK);
	
	customerDTOMock =  CustomerDTO.builder()
						.id(1l)
						.name("customer name")
						.surname("customer surname")
						.dni("38168885")
						.build();
	
	responseCustomerDTOMock = new ResponseEntity<>(customerDTOMock, HttpStatus.OK);
	
	
	ordersDTO = new ArrayList<>();
	
	ordersDTO.add(orderDTOMock);
	
	responseEntityOrdersDTO = new ResponseEntity<>(ordersDTO, HttpStatus.OK);
	
	customersDTO = new ArrayList<>();
	
	customersDTO.add(customerDTOMock);
	
	responseEntityCustomerDTO = new ResponseEntity<>(customersDTO, HttpStatus.OK);
	
	productsDTO = new ArrayList<>();
	
	productsDTO.add(productDTOMock);
	
	responseEntityProductDTO = new ResponseEntity<>(productsDTO, HttpStatus.OK);
	
	pageable = PageRequest.of(1, 10);
	
	pageOrder = new PageImpl<>(ordersDTO);

		
	}
	
	@Test
	@DisplayName("create Order cuantity > product stock")
	public void createTest() {
		
		orderDTOMock.setQuantity(50);
		
		productDTOMock.setStockQuantity(40);
		
		ResponseEntity<ResponseOrderDTO> responseOrderDTO = new ResponseEntity<>(new ResponseOrderDTO("No hay stock disponbile"), HttpStatus.OK);
		
		when(feignProduct.getById(orderDTOMock.getProductId())).thenReturn(responseProductDTOMock);
		when(feignCustomer.getById(orderDTOMock.getCustomerId())).thenReturn(responseCustomerDTOMock);
		
		assertEquals(responseOrderDTO, orderServiceImpl.create(orderDTOMock));
		
	}
	
	@Test
	@DisplayName("create Order cuantity < product stock")
	public void createTest1() {
		
		orderDTOMock.setQuantity(50);
		
		productDTOMock.setStockQuantity(60);
		
		ResponseEntity<OrderDTO> responseEntityOrderDTO = 
				new ResponseEntity<>(orderDTOMock, HttpStatus.OK);
		
		ResponseEntity<ProductDTO> responseEntityProductDTO = 
				new ResponseEntity<>(productDTOMock, HttpStatus.OK);
		
		
		
		when(feignProduct.getById(orderDTOMock.getProductId())).thenReturn(responseEntityProductDTO);
		when(feignCustomer.getById(orderDTOMock.getCustomerId())).thenReturn(responseCustomerDTOMock);
		when(feignOrder.create(orderDTOMock)).thenReturn(responseEntityOrderDTO);
		when(feignProduct.update(productDTOMock.getId(), productDTOMock)).thenReturn(responseEntityProductDTO);
		
		orderDTOMock.setTotalPrice(productDTOMock.getPrice() * orderDTOMock.getQuantity());
		
		ResponseOrderDTO responseOrderDTO = toResponseDTO(orderDTOMock, productDTOMock, customerDTOMock);
		
		assertEquals(responseOrderDTO, orderServiceImpl.create(orderDTOMock).getBody());
		
	}
	
	@Test
	@DisplayName("get test")
	public void getTest() {
		
		when(feignOrder.get(1l)).thenReturn(responseOrderDTOMock);
		when(feignProduct.getById(1l)).thenReturn(responseProductDTOMock);
		when(feignCustomer.getById(1l)).thenReturn(responseCustomerDTOMock);
		
		ResponseOrderDTO responseOrderDTO = toResponseDTO(responseOrderDTOMock.getBody(), responseProductDTOMock.getBody(), responseCustomerDTOMock.getBody());
		
		assertEquals(responseOrderDTO, orderServiceImpl.get(1l).getBody());
	}
	
	@Test
	@DisplayName("get all orders test")
	public void getAllOrders() {
		
		ResponseEntity<List<ProductDTO>> responseEntityProductsDTO = new ResponseEntity<>(productsDTO, HttpStatus.OK);
		
		when(feignOrder.getAllOrders()).thenReturn(responseEntityOrdersDTO);
		when(feignCustomer.findAll()).thenReturn(responseEntityCustomerDTO);
		when(feignProduct.findAll()).thenReturn(responseEntityProductsDTO);
		
		List<ResponseOrderDTO> responseOrdersDTO = mergeLists(responseEntityOrdersDTO.getBody(), responseEntityCustomerDTO.getBody(), responseEntityProductsDTO.getBody());
		
		
		assertEquals(responseOrdersDTO, orderServiceImpl.getAllOrders().getBody());
	}
	
	@Test
	@DisplayName("get all orders test pageable")
	public void getAllOrdersPageable() {
		
		ResponseEntity<List<ProductDTO>> responseEntityProductsDTO = new ResponseEntity<>(productsDTO, HttpStatus.OK);
		
		when(feignOrder.getAllOrders(pageable)).thenReturn(responseEntityOrdersDTO);
		when(feignCustomer.findAll()).thenReturn(responseEntityCustomerDTO);
		when(feignProduct.findAll()).thenReturn(responseEntityProductsDTO);
		
		List<ResponseOrderDTO> responseOrdersDTO = mergeLists(responseEntityOrdersDTO.getBody(), responseEntityCustomerDTO.getBody(), responseEntityProductsDTO.getBody());
		
		System.out.println(responseOrdersDTO);
		
		System.out.println(orderServiceImpl.getAllOrders(pageable));
		
		assertEquals(responseOrdersDTO, orderServiceImpl.getAllOrders(pageable).getBody());
		
	}
	
	@Test
	@DisplayName("get all orderes by customers")
	void getAllOrderByCustomer() {
		
		ResponseEntity<List<ProductDTO>> responseEntityProductsDTO = new ResponseEntity<>(productsDTO, HttpStatus.OK);
		
		when(feignOrder.getAllOrders()).thenReturn(responseEntityOrdersDTO);
		when(feignCustomer.findAll()).thenReturn(responseEntityCustomerDTO);
		when(feignProduct.findAll()).thenReturn(responseEntityProductsDTO);

	}
	
}
