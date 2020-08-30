package ar.com.gl.shop.order.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

	private Long id;
	@NotBlank
	private Long productId;
	@NotBlank
	private Long customerId;
	@NotBlank
	private Integer quantity;
	@NotBlank
	private Double totalPrice;
	private Boolean disable;
}
