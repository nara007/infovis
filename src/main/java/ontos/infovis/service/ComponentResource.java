package ontos.infovis.service;

import ontos.infovis.pojo.Bool;
import ontos.infovis.serviceImpl.ComponentManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("component")
public class ComponentResource {

    /**web.xml
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Bool registerComponent(URI componentURI) throws URISyntaxException {

        ComponentManager cm=new ComponentManager();

        return cm.registerComponent(new URI("http://www.123.com"));
    }
}
