package com.guudint.clickargo.controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Date;

public class CustomSerializer extends JsonSerializer<Object> {
	public static final JsonSerializer<Object> EMPTY_STRING_SERIALIZER_INSTANCE = new EmptyStringSerializer();
	public static final JsonSerializer<Object> NULL_NUMBER_SERIALIZER_INSTANCE = new NullNumberSerializer();
	public static final JsonSerializer<Object> NULL_DECIMAL_SERIALIZER_INSTANCE = new NullDecimalSerializer();
	public static final JsonSerializer<Object> NULL_CHARACTER_SERIALIZER_INSTANCE = new NullCharacterSerializer();
	public static final JsonSerializer<Object> NULL_DATE_SERIALIZER_INSTANCE = new NullDateSerializer();

	// Constructor
	///////////////
	public CustomSerializer() {
	}

	// Override Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind@serialize
	 * 
	 */
	@Override
	public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		jsonGenerator.writeString("");
	}

	// Empty String JSON Serializer
	//////////////////////////////
	/**
	 * @author user
	 *
	 */
	private static class EmptyStringSerializer extends JsonSerializer<Object> {

		// Constructor
		//////////////
		public EmptyStringSerializer() {
		}

		// Override Methods
		///////////////////
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.fasterxml.jackson.databind@serialize
		 * 
		 */
		@Override
		public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
				throws IOException, JsonProcessingException {
			jsonGenerator.writeString("");
		}
	}

	// Null NumberJSON Serializer
	//////////////////////////////
	private static class NullNumberSerializer extends JsonSerializer<Object> {

		// Constructor
		//////////////
		public NullNumberSerializer() {
		}

		// Override Methods
		///////////////////
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.fasterxml.jackson.databind@serialize
		 * 
		 */
		@Override
		public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
				throws IOException, JsonProcessingException {
			jsonGenerator.writeNumber(0);
		}
	}

	// Null Decimal JSON Serializer
	//////////////////////////////
	private static class NullDecimalSerializer extends JsonSerializer<Object> {
		// Constructor
		//////////////
		public NullDecimalSerializer() {
		}

		// Override Methods
		///////////////////
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.fasterxml.jackson.databind@serialize
		 * 
		 */
		@Override
		public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
				throws IOException, JsonProcessingException {
			jsonGenerator.writeNumber(0.00);

		}
	}

	// Null Character JSON Serializer
	//////////////////////////////
	private static class NullCharacterSerializer extends JsonSerializer<Object> {
		// Constructor
		//////////////
		public NullCharacterSerializer() {
		}

		// Override Methods
		///////////////////
		/**
		 *
		 */
		@Override
		public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
				throws IOException, JsonProcessingException {
			jsonGenerator.writeString("");

		}

	}

	// Null Date JSON Serializer
	//////////////////////////////
	private static class NullDateSerializer extends JsonSerializer<Object> {
		// Constructor
		//////////////
		public NullDateSerializer() {
		}

		// Override Methods
		///////////////////
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.fasterxml.jackson.databind@serialize
		 * 
		 */
		@Override
		public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
				throws IOException, JsonProcessingException {
			if (isIgnoreDefaultDate(jsonGenerator.getOutputContext().getCurrentName())) {
				jsonGenerator.writeObject(null);
			} else {
				jsonGenerator.writeObject(new Date());
			}
		}

		// Helper Methods
		//////////////////
		/**
		 * @param field
		 * @return
		 */
		private boolean isIgnoreDefaultDate(String field) {
			String[] fields = { "srExpDate", "vdclCrewDob", "vdplDob", "vcdsExpireDate", "dvlYfVccnExpDate", "vsclEta",
					"vsclEtd", "vsclApobDt", "vsclAta", "vsclAtd", "vsclAtaAnchor", "vsclEdb", "vsclAnchorPortArea",
					"epEstdEtdDate", "vsclActualPobDt", "visiteDate", "issueDate", "pasIsscDtIssue", "pasIsscDtExpire",
					"appnRecoveryActionDate" };
			for (String temp : fields) {
				if (field.equals(temp)) {
					return true;
				}
			}
			return false;
		}
	}
}
