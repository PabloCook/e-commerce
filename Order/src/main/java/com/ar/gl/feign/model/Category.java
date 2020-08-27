package com.ar.gl.feign.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

	private Long id;

	private String name;

	private String description;

	private Boolean enabled;

	private List<Product> products;

}
