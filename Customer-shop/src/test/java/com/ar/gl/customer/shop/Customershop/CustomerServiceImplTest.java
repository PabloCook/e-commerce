package com.ar.gl.customer.shop.Customershop;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ar.gl.customer.shop.dto.CustomerDTO;
import com.ar.gl.customer.shop.model.Customer;
import com.ar.gl.customer.shop.repository.CustomerRepository;
import com.ar.gl.customer.shop.service.impl.CustomerServiceImpl;
import com.ar.gl.customer.shop.util.CustomerConverter;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
	@InjectMocks
	CustomerServiceImpl customerService;

	@Mock
	CustomerRepository customerRepositoryMock;
	@Mock
	CustomerConverter customerConverte;

	Customer customer, customer2;
	CustomerDTO customerDTO, customerDTO2;
	Optional<Customer> customerO;

	@BeforeEach
	void setUp() throws Exception {
		customer = new Customer(1L, "customerName", "customerSN", "123");
		customer2 = new Customer(2L, "customerName2", "customerSN2", "1234");
		customerDTO = new CustomerDTO(1L, "customerName", "customerSN", "123", true);
		customerDTO2 = new CustomerDTO(2L, "customerName2", "customerSN2", "1234", true);
		customerO = Optional.of(customer);

		lenient().when(customerConverte.toDTO(customer)).thenReturn(customerDTO);
		lenient().when(customerConverte.toEntity(customerDTO)).thenReturn(customer);
		lenient().when(customerRepositoryMock.findById(customer.getId())).thenReturn(customerO);

	}

	@Test
	@DisplayName("testSave")
	void testCase_1() {
		when(customerRepositoryMock.save(customer)).thenReturn(customer);
		assertEquals(customerDTO, customerService.save(customerDTO));
	}

	@Test
	@DisplayName("testFindAll")
	void testCase_2() {
		List<Customer> customers = new ArrayList<>();
		List<CustomerDTO> customerDTOList = new ArrayList<>();
		customers.add(customer);
		customers.add(customer2);
		customerDTOList.add(customerDTO);
		customerDTOList.add(customerDTO2);
		when(customerRepositoryMock.findAll()).thenReturn(customers);
		when(customerConverte.toDTOList(customers)).thenReturn(customerDTOList);
		
		assertEquals(customerDTOList, customerService.findAll());
		
	}


	@Test
	@DisplayName("testDelete")
	void testCase_3() {

		when(customerRepositoryMock.findById(customer.getId())).thenReturn(customerO);
		customerService.delete(customer.getId());
		verify(customerRepositoryMock).delete(customerO.get());
	}

	@Test
	@DisplayName("testFindById")
	void testCase_4() {

		assertEquals(customerDTO, customerService.findById(customer.getId()));
	}

	@Test
	@DisplayName("TestUpdate")
	void testCase_5() {
		customerDTO.setName("customerNameAltered");
		when(customerConverte.toEntity(customerDTO, customerO.get())).thenReturn(customer);

		when(customerRepositoryMock.save(customer)).thenReturn(customer);
		assertEquals(customerDTO, customerService.update(customer.getId(), customerDTO));
	}

	@Test
	@DisplayName("Test-soft-Delete")
	void testCase_6() {

		Customer customerAlt = customer;
		customerAlt.setEnabled(false);
		when(customerRepositoryMock.save(customerAlt)).thenReturn(customerAlt);
		customerService.softDelete(customer.getId());
		assertEquals(customer, customerAlt);
	}

	@Test
	@DisplayName("TestRestore")
	void testCase_7() {

		Customer customerAlt = customer;
		customerAlt.setEnabled(false);
		when(customerRepositoryMock.save(customerAlt)).thenReturn(customerAlt);
		customerService.softDelete(customer.getId());
		customerAlt.setEnabled(true);
		when(customerConverte.toDTO(customerRepositoryMock.save(customerAlt))).thenReturn(customerDTO);
		customerService.restore(customer.getId());
		assertTrue(customer.getEnabled());

	}
	
	
	
	

}
