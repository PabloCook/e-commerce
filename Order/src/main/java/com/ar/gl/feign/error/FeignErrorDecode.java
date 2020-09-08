package com.ar.gl.feign.error;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignErrorDecode implements ErrorDecoder {
    
    @Override
    public Exception decode(String methodKey, Response response) {
 
        	Gson json = new Gson();
        	
        	ErrorDTO error = json.fromJson(response.body().toString(), ErrorDTO.class);
        	
        	return new ResponseStatusException(HttpStatus.valueOf(response.status()), error.getErrorMessage()); 
            	
    }
    
}
