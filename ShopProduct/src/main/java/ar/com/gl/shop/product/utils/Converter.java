package ar.com.gl.shop.product.utils;

import java.lang.reflect.Type;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;


public class Converter {
	
	ModelMapper modelMapper = new ModelMapper();
	
	public Converter() {
		modelMapper.getConfiguration().setSkipNullEnabled(true);
		modelMapper.getConfiguration()
		  .setMatchingStrategy(MatchingStrategies.LOOSE);
	}
	
	
	public Object fromTo(Object object, Type objectClass) {
		return modelMapper.map(object, objectClass);
	}
	
	public Object fromTo(Object objectDTO, Object object) {
		modelMapper.map(objectDTO, object);
		return object;
	}

}
