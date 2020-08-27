package com.ar.gl.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.ar.gl.feign.shop.product")
@EnableDiscoveryClient
public class FeignApplication {

	public static void main(final String[] args) {
		SpringApplication.run(FeignApplication.class);
	}

}
