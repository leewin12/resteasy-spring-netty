package org.greg.resteasy.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.greg.resteasy.controller.HomeController;
import org.greg.resteasy.pojo.response.Helloworld;
import org.greg.resteasy.server.NettyServer;
import org.greg.resteasy.test.testHomeController.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class testHomeController {

	@Configuration
	public static class TestConfig {

		@Bean
		public NettyServer nettyServer() {
			return new NettyServer();
		}
		
		@Bean
		public HomeController homeController() {
			return new HomeController();
		}

		@Bean
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}
	}

	@Autowired
	NettyServer		server;
	@Autowired
	RestOperations	restOps;

	@Before
	public void init() {
		server.start();
	}

	@Test
	public void testHelloworld() {
		Helloworld resp = restOps.getForObject(buildUrl("hello/world"), Helloworld.class);
		assertNotNull(resp);
		assertTrue(StringUtils.hasText(resp.getMessage()));
	}

	public String buildUrl(String target) {
		return String.format("http://localhost:%d/%s/%s",
				server.getPort(), server.getRootResourcePath(), target);
	}

}
