package org.greg.resteasy.server;

import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@Component
@Provider
public class CustomJacksonJsonProvider extends JacksonJsonProvider {
	public CustomJacksonJsonProvider() {
		super((new ObjectMapper())
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
						false));

	}
}
