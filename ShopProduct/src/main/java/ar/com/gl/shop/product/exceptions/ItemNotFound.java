package ar.com.gl.shop.product.exceptions;

public class ItemNotFound extends Exception {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public ItemNotFound(String message) {
		this.message = message;
	}
	
	


}
