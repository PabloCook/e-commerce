package com.ar.gl.feign.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

	private Long id;
	
	private String name;
	
	private String surname;
	
	private String dni;
	
	private Boolean enabled;


}
