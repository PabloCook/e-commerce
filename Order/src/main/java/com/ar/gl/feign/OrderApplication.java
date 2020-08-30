package com.ar.gl.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.ar.gl.feign.shop.product")
@EnableDiscoveryClient
//@EnableHystrix
//@EnableHystrixDashboard
public class OrderApplication {

	public static void main(final String[] args) {
		SpringApplication.run(OrderApplication.class);
	}

}
