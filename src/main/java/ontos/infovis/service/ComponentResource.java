package ontos.infovis.service;

import ontos.infovis.pojo.Bool;
import ontos.infovis.pojo.Component;
import ontos.infovis.pojo.POJOFactory;
import ontos.infovis.serviceimpl.ComponentManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


/**
 * @author Ye Song
 * @description Root resource (exposed at "myresource" path).
 * @date 15-5-23
 * @copyright infovis@tu-dresden.de
 */
@Path("component")
public class ComponentResource {

  /**
   * Method handling HTTP POST requests. The returned object will be sent to the client as "json"
   * media type.
   * 
   * @param componentURI String
   * @return Bool that will be returned as a json response.
   */
  @POST
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public Bool registerComponent(String componentURI) {

    ComponentManager cm =
        (ComponentManager) POJOFactory.POJOContext.getSpringContext().getBean("componentManager");
      System.out.println("param: "+componentURI);
    return cm.registerComponent(componentURI);
  }

    /**
     * Method handling HTTP PUT requests. The returned object will be sent to the client as "json"
     * media type.
     *
     * @param componentURI String
     * @return Bool that will be returned as a json response.
     */
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Bool updateComponent(String componentURI){
        ComponentManager cm =
                (ComponentManager) POJOFactory.POJOContext.getSpringContext().getBean("componentManager");
        System.out.println("param2: "+componentURI);
        return cm.registerComponent(componentURI);
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent to the client as "json"
     * media type.
     *
     * @param identity String
     * @param version  String
     * @return Component that will be returned as a json response.
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Component getComponent(@QueryParam("id") String identity, @QueryParam("version") String version){
        Component component=(Component)POJOFactory.POJOContext.getSpringContext().getBean("component");
        component.setIdentity("xiaojun");
        component.setVersion("1.0.0");
        System.out.println(identity + " " + version);
        return component;
    }

//    public List<Component> getAllComponents(){
//
//    }
}
