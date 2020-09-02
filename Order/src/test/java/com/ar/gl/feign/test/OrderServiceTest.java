package com.ar.gl.feign.test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ar.gl.feign.service.impl.OrderServiceImpl;
import com.ar.gl.feign.shop.product.FeignCustomer;
import com.ar.gl.feign.shop.product.FeignOrder;
import com.ar.gl.feign.shop.product.FeignProduct;

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
	
	

}
