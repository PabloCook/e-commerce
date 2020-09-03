package com.ar.gl.customer.shop.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

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
	
	@NotBlank
	@NotNull
	private String surname;
	
	@NotBlank
	@NotNull
	private String dni;
	
}
