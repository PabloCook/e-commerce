package ar.com.gl.shop.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
	private Long id;
	private String name;
	private String description;
	private Boolean enabled; 
}
