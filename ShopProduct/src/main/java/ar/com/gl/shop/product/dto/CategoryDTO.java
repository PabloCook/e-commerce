package ar.com.gl.shop.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
	private Long id;
	private String name;
	private String description;
	private Boolean enabled; 
}
