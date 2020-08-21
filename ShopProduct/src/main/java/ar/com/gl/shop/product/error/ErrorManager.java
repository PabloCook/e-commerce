package ar.com.gl.shop.product.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.gl.shop.product.exceptions.ItemNotFound;

@ControllerAdvice
public class ErrorManager {

	@ExceptionHandler({Exception.class})
	@ResponseBody
	public ResponseErrorDTO handleGeneralException(final Exception exception)
	{
		ResponseErrorDTO responseError = new ResponseErrorDTO();
		responseError.setErrorMessage(exception.getMessage());
		responseError.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return responseError;
	}

	@ExceptionHandler({ItemNotFound.class})
	@ResponseBody
	public ResponseErrorDTO handleCustomException(ItemNotFound INFexception) 
	{
		ResponseErrorDTO responseError = new ResponseErrorDTO();
		responseError.setErrorMessage(INFexception.getMessage());
		responseError.setStatusCode(HttpStatus.NO_CONTENT.value());
		return responseError;
	}
}