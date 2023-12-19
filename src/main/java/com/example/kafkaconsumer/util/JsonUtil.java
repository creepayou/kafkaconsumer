package com.example.kafkaconsumer.util;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {
	private JsonUtil() {
		throw new IllegalStateException("Utility Class");
	}

	public static String toJsonString(Object obj) throws JsonProcessingException {
		String result = null;
		ObjectMapper mapper = buildMapper();
		result = mapper.writeValueAsString(obj);

		return result;
	}

	public static ObjectMapper buildMapper() {
		ObjectMapper result = new ObjectMapper();
		result.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		result.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		result.configure(Feature.AUTO_CLOSE_SOURCE, true);
		result.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		result.setDateFormat(new SimpleDateFormat(DateUtil.DATETIMETZ_PATTERN));
		return result;
	}

	public static <T> T fromJson(String jsonString, Class<T> valueType) throws JsonProcessingException {
		return buildMapper().readValue(jsonString, valueType);
	}

	public static <T> T fromJson(String jsonString, TypeReference<T> valueTypeRef) throws JsonProcessingException {
		return buildMapper().readValue(jsonString, valueTypeRef);
	}

}
