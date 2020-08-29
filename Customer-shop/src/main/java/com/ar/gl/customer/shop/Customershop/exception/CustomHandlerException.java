package com.ar.gl.customer.shop.Customershop.exception;


import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ar.gl.customer.shop.Customershop.DTO.ErrorDTO;



@ControllerAdvice
public class CustomHandlerException extends ResponseEntityExceptionHandler {
	
	@Override
	public ResponseEntity<Object>handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders head, HttpStatus status , WebRequest request){
		ErrorDTO responseError = new ErrorDTO(e.getBindingResult().getFieldError().getField() +" not valid",HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<Object>(responseError, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ NoSuchElementException.class})
	public ResponseEntity<ErrorDTO> handleNoSuchElementException(NoSuchElementException e) {
		ErrorDTO responseError = new ErrorDTO(e.getMessage(), HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
	}

}
