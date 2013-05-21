package org.greg.resteasy.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.greg.resteasy.controller.HomeController;
import org.greg.resteasy.controller.request.Article;
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

	@Test
	public void testPOST() {
		int id = 44;
		String name = "NAME";
		Article resp = restOps.postForObject(
				buildUrl("hello"),
				new Article(id, name),
				Article.class);
		assertNotNull(resp);
		assertTrue(resp.getId().equals(id));
		assertTrue(resp.getName().equals(name));
	}

	@Test
	public void testPOSTbyList() {
		int id = 44;
		String name = "NAME";
		
		@SuppressWarnings("unchecked")
		List<Object> resp = restOps.postForObject(
				buildUrl("hello?multi=true"),
				Collections.singletonList(new Article(id, name)),
				List.class);

		assertEquals(1, resp.size());
	}

	public String buildUrl(String target) {
		return String.format("http://localhost:%d/%s/%s",
				server.getPort(), server.getRootResourcePath(), target);
	}

}
