package ar.com.gl.shop.product.exceptions;

public class ItemNotFound extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final String message ;

	@Override
	public String getMessage() {
		return message;
	}

	public ItemNotFound(String message) {
		this.message = message;
	}
	
	public ItemNotFound() {
		this.message = "Item no encontrado";
	}


}
