package com.ar.gl.feign.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {
	private Long id;
	
	private String name;
	
	private String surname;
	
	private String dni;
	
}
