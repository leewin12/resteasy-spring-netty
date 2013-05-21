package org.greg.resteasy.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.greg.resteasy.controller.request.Article;
import org.greg.resteasy.pojo.response.Helloworld;
import org.springframework.stereotype.Controller;

@Controller
@Path("/hello")
public class HomeController {

	@GET
	@Path("/world")
	@Produces("application/json")
	public Helloworld helloworld() throws Exception {
		return new Helloworld("Welcome, HelloWorld");
	}

	@GET
	@Path("/auth")
	@Produces("application/json")
	public Helloworld auth(@Context SecurityContext context) {
		return new Helloworld(context.getUserPrincipal().getName());
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Article save(Article article) {
		return article;
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Article> save(
			@QueryParam("multi") boolean isMulti,
			List<Article> articles) {
		return articles;
	}

}
