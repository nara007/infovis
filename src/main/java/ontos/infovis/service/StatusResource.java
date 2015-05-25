package ontos.infovis.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("status")
public class StatusResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getStatus() {
    	return "Server is running!";
    }
}