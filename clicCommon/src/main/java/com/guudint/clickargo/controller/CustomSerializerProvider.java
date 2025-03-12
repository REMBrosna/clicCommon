package com.guudint.clickargo.controller;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.poi.hpsf.Decimal;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

public class CustomSerializerProvider extends DefaultSerializerProvider {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -6638005862094372031L;

	// Constructors
	///////////////
	public CustomSerializerProvider() {
		super();
	}

	/**
	 * @param provider
	 * @param config
	 * @param jsf
	 */
	public CustomSerializerProvider(CustomSerializerProvider provider, SerializationConfig config,
			SerializerFactory jsf) {
		super(provider, config, jsf);
	}

	// Override Methods
	//////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.ser.DefaultSerializerProvider#createInstance
	 * 
	 */	
	@Override
	public CustomSerializerProvider createInstance(SerializationConfig config, SerializerFactory jsf) {
		return new CustomSerializerProvider(this, config, jsf);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.SerializerProvider#findNullValueSerializer
	 * 
	 */		
	@Override
	public JsonSerializer<Object> findNullValueSerializer(BeanProperty property) throws JsonMappingException {
		if (property.getType().getRawClass().equals(String.class))
			return CustomSerializer.EMPTY_STRING_SERIALIZER_INSTANCE;
		else if (property.getType().getRawClass().equals(Float.class)
				|| property.getType().getRawClass().equals(Integer.class))
			return CustomSerializer.NULL_NUMBER_SERIALIZER_INSTANCE;
		else if(property.getType().getRawClass().equals(Decimal.class) 
				|| property.getType().getRawClass().equals(BigDecimal.class))
			return CustomSerializer.NULL_DECIMAL_SERIALIZER_INSTANCE;
		else if(property.getType().getRawClass().equals(Character.class))
			return CustomSerializer.NULL_CHARACTER_SERIALIZER_INSTANCE;
		else if(property.getType().getRawClass().equals(Date.class) && !property.getName().toLowerCase().contains("time"))
			return CustomSerializer.NULL_DATE_SERIALIZER_INSTANCE;
		else
			return super.findNullValueSerializer(property);
	}
}