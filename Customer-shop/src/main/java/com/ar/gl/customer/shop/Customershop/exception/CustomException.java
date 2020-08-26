package com.ar.gl.customer.shop.Customershop.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ar.gl.customer.shop.Customershop.DTO.ErrorDTO;


@ControllerAdvice
public class CustomException {
	
	@ExceptionHandler({NoSuchElementException.class})
	@ResponseBody
	public ResponseEntity<ErrorDTO> handleCustomException(NoSuchElementException e) 
	{
		ErrorDTO responseError = new ErrorDTO(e.getMessage(),HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(responseError,HttpStatus.NOT_FOUND);
	}
}
