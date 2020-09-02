package com.ar.gl.customer.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CustomerShopApplication {

	public static void main(final String[] args) {
		SpringApplication.run(CustomerShopApplication.class);
	}

}
