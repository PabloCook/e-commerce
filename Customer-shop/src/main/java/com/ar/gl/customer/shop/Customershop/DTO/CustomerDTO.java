package com.ar.gl.customer.shop.Customershop.DTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class CustomerDTO {
	private Long id;
	
	@NotBlank
	@NotNull
	private String name;
}
