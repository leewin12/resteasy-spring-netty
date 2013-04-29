package org.greg.resteasy.server;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PreDestroy;

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

		// extract only controller annotated beans
		Collection<Object> beans = ac.getBeansWithAnnotation(Controller.class).values();
		Assert.notEmpty(beans);

		ResteasyDeployment dp = new ResteasyDeployment();
		dp.setResources(new ArrayList<Object>(beans));

		netty = new ConfigurableNettyJaxrsServer();
		netty.initBootstrap().setOption("reuseAddress", true);
		netty.setDeployment(dp);
		netty.setPort(8082);
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
