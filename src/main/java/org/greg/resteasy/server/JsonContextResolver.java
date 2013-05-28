package org.greg.resteasy.server;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Provider
public class JsonContextResolver implements ContextResolver<ObjectMapper> {

	final ObjectMapper	mapper	= (new ObjectMapper())
										.configure(
												DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
												false);

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return mapper;
	}
}
