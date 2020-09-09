package ar.com.gl.shop.order.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

	private Long id;
	private Long productId;
	private Long customerId;
	private Integer quantity;
	private Double totalPrice;
	private Boolean disable;
	private LocalDate date;
}
