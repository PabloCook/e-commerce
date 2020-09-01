package com.ar.gl.customer.shop.util;

import java.lang.reflect.Type;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class Converter {

		ModelMapper modelMapper = new ModelMapper();

		public Converter() {
			modelMapper.getConfiguration().setSkipNullEnabled(true);
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		}

		protected Object fromTo(Object object, Type objectClass) {
			return modelMapper.map(object, objectClass);
		}

		protected Object fromTo(Object objectDTO, Object object) {
			modelMapper.map(objectDTO, object);
			return object;
		}

}
