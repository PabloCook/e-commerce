package ar.com.gl.shop.order.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

	private Long id;
	//@NotEmpty
	private Long productId;
	//@NotEmpty
	private Long customerId;
	//@NotEmpty
	private Integer quantity;
	//@NotEmpty
	private Double totalPrice;
	private Boolean disable;
}
