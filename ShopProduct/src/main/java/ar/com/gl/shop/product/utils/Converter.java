package ar.com.gl.shop.product.utils;

import java.lang.reflect.Type;

import org.modelmapper.ModelMapper;


public class Converter {
	
	ModelMapper modelMapper = new ModelMapper();
	
	public Object fromTo(Object object, Type objectClass) {
		return modelMapper.map(object, objectClass);
	}

}
