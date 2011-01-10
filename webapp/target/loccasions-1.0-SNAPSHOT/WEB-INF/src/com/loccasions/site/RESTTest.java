package com.loccasions.site;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/helloworld")
public class RESTTest {
	@GET
	@Path("/name/{name}")
	@Produces("text/plain")
	public String apply(@PathParam("name") String user) {
		return "Hi " + user + "!";
	}
}
