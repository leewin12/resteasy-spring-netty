package org.greg.resteasy.request;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Provider
public class AuthenticationFilter
		implements ContainerRequestFilter {

	protected final Logger	logger	= LoggerFactory.getLogger(AuthenticationFilter.class);

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// Just for showing it works well
		logger.info("==AuthenticationFilter==");
	}

}
