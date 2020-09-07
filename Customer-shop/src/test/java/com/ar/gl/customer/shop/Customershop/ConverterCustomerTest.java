package com.ar.gl.customer.shop.Customershop;

import static org.junit.jupiter.api.Assertions.*;
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

import com.ar.gl.customer.shop.dto.CustomerDTO;
import com.ar.gl.customer.shop.model.Customer;
import com.ar.gl.customer.shop.util.Converter;
import com.ar.gl.customer.shop.util.CustomerConverter;





@ExtendWith(MockitoExtension.class)
class ConverterCustomerTest {
	
	@InjectMocks
	Converter converter;
	
	@Mock
	ModelMapper modelMapperMock;
	
	@Mock
	CustomerDTO customerDTO;
	
	Customer customer;
	CustomerConverter customerConverter;
	List<Customer> customerList;
	List<CustomerDTO> customerDTOList;
	
	@BeforeEach
	void setUp() {
		customerConverter = new CustomerConverter();
		customer = new Customer(1L, "CustomerName", "CustomerSN", "123");
		customerDTO= new CustomerDTO(1L, "CustomerName", "CustomerSN", "123", true);
		customerList = new ArrayList<Customer>();
		customerDTOList = new ArrayList<CustomerDTO>();
		customerList.add(customer);
		customerList.add(customer);
		customerDTOList.add(customerDTO);
		customerDTOList.add(customerDTO);
	}
	
	@Test
	@DisplayName("toEntity")
	void testCase_1() {

		lenient().when(modelMapperMock.map(customerDTO, Customer.class)).thenReturn(customer);

		assertEquals(customer, customerConverter.toEntity(customerDTO));
	}
	
	@Test
	@DisplayName("toDto")
	void testCase_2() {

		lenient().when(modelMapperMock.map(customer, CustomerDTO.class)).thenReturn(customerDTO);

		assertEquals(customerDTO, customerConverter.toDTO(customer));
	}
	
	@Test
	@DisplayName("toDtoList")
	void testCase_3() {
		lenient().when(modelMapperMock.map(customer, CustomerDTO.class)).thenReturn(customerDTO);

		assertEquals(customerDTOList, customerConverter.toDTOList(customerList));
	}


}
