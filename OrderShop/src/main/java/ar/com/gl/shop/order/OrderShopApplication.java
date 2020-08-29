package ar.com.gl.shop.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OrderShopApplication {

	public static void main(final String[] args) {
		SpringApplication.run(OrderShopApplication.class);
	}

}
