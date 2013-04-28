package org.greg.resteasy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.greg.resteasy.pojo.response.Helloworld;
import org.springframework.stereotype.Controller;

@Controller
@Path("/hello")
public class HomeController {
	
	@GET
	@Path("/world")
	@Produces("application/json")
	public Helloworld home() {
		return new Helloworld("Welcome, HelloWorld");
	}
	
}
