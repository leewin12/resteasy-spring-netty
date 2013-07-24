package org.greg.resteasy.server;

import java.util.Collection;

import javax.annotation.PreDestroy;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.ResteasyDeployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

@Component
public class NettyServer {

	@Autowired
	ApplicationContext				ac;

	String							rootResourcePath	= "/resteasy";
	int								port				= 8082;

	ConfigurableNettyJaxrsServer	netty;

	public void start() {

		ResteasyDeployment dp = new ResteasyDeployment();

		Collection<Object> providers = ac.getBeansWithAnnotation(Provider.class).values();
		Collection<Object> controllers = ac.getBeansWithAnnotation(Controller.class).values();

		Assert.notEmpty(controllers);

		// extract providers
		if (providers != null) {
			dp.getProviders().addAll(providers);
		}
		// extract only controller annotated beans
		dp.getResources().addAll(controllers);
		
		netty = new ConfigurableNettyJaxrsServer();
		netty.initBootstrap().setOption("reuseAddress", true);
		netty.setDeployment(dp);
		netty.setPort(port);
		netty.setRootResourcePath("/resteasy");
		netty.setSecurityDomain(null);
		netty.start();
	}

	@PreDestroy
	public void cleanUp() {
		netty.stop();
	}

	public String getRootResourcePath() {
		return rootResourcePath;
	}

	public int getPort() {
		return port;
	}

}
