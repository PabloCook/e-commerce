package ar.com.gl.shop.product.error;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ar.com.gl.shop.product.exceptions.CannotDelete;
import ar.com.gl.shop.product.exceptions.ItemNotFound;

@ControllerAdvice
public class ErrorManager extends ResponseEntityExceptionHandler{

	
	@ExceptionHandler({Exception.class})
	@ResponseBody
	public ResponseEntity<ErrorDTO> handleGeneralException(final Exception exception)
	{
		ErrorDTO responseError = new ErrorDTO(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(responseError,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ItemNotFound.class})
	@ResponseBody
	public ResponseEntity<ErrorDTO> handleCustomException(ItemNotFound exception) 
	{
		ErrorDTO responseError = new ErrorDTO(exception.getMessage(),HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(responseError,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({CannotDelete.class})
	@ResponseBody
	public ResponseEntity<ErrorDTO> handleCustomException(CannotDelete exception) 
	{
		ErrorDTO responseError = new ErrorDTO(exception.getMessage(),HttpStatus.FAILED_DEPENDENCY.value());
		return new ResponseEntity<>(responseError,HttpStatus.FAILED_DEPENDENCY);
	}
	
	@ExceptionHandler({IOException.class})
	@ResponseBody
	public ResponseEntity<ErrorDTO> handleUrlException(IOException exception) 
	{
		ErrorDTO responseError = new ErrorDTO(exception.getMessage(),HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(responseError,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({SQLException.class})
	@ResponseBody
	public ResponseEntity<ErrorDTO> handleSqlException(SQLException exception) 
	{
		ErrorDTO responseError = new ErrorDTO(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(responseError,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDTO responseError = new ErrorDTO(exception.getMessage(),HttpStatus.NO_CONTENT.value());
		return new ResponseEntity<>(responseError,HttpStatus.NO_CONTENT);
    }
	
	
}