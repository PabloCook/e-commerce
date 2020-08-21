package ar.com.gl.shop.product.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorDTO {

	private String errorMessage;
	private int statusCode;
}
