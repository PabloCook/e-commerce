package ar.com.gl.shop.product.exceptions;

public class CannotDelete extends Exception {
	
	private static final long serialVersionUID = 2L;
	
	private final String message ;

	@Override
	public String getMessage() {
		return message;
	}

	public CannotDelete(String message) {
		this.message = message;
	}
	
	public CannotDelete() {
		this.message = "No se puede eliminar";
	}


}
