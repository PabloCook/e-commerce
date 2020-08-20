package ar.com.gl.shop.product.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
	private Long id;
	private String name;
	private String description;
	private Boolean enabled; 
}
