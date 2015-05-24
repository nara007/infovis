package ontos.infovis.service;

import ontos.infovis.pojo.Bool;
import ontos.infovis.pojo.Component;
import ontos.infovis.pojo.POJOFactory;
import ontos.infovis.serviceimpl.ComponentManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Ye Song
 * @description Root resource (exposed at "myresource" path).
 * @date 15-5-23
 * @copyright infovis@tu-dresden.de
 */
@Path("/")
public class ComponentResource {

  /**
   * Method handling HTTP POST requests. The returned object will be sent to the client as "json"
   * media type.Method registers a component.
   * 
   * @param componentURI String
   * @return Bool indicates if a component has been registered successfully.
   */
  @POST
  @Path("component")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public Bool registerComponent(String componentURI) {

    ComponentManager cm =
        (ComponentManager) POJOFactory.POJOContext.getSpringContext().getBean("componentManager");
    System.out.println("param: " + componentURI);
    return cm.registerComponent(componentURI);
  }

  /**
   * Method handling HTTP PUT requests. The returned object will be sent to the client as "json"
   * media type.Method updates a component.
   *
   * @param componentURI String
   * @return Bool indicates if a specific component has been updated successfully.
   */
  @PUT
  @Path("component")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public Bool updateComponent(String componentURI) {
    ComponentManager cm =
        (ComponentManager) POJOFactory.POJOContext.getSpringContext().getBean("componentManager");
    System.out.println("param2: " + componentURI);
    return cm.registerComponent(componentURI);
  }

  /**
   * Method handling HTTP GET requests. The returned object will be sent to the client as "json"
   * media type.Method returns a specific component.
   *
   * @param identity String
   * @param version String
   * @return Component
   */
  @GET
  @Path("component")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Component getComponent(@QueryParam("id") String identity,
      @QueryParam("version") String version) {
    Component component =
        (Component) POJOFactory.POJOContext.getSpringContext().getBean("component");
    component.setIdentity("xiaojun");
    component.setVersion("1.0.0");
    System.out.println(identity + " " + version);
    return component;
  }

  /**
   * Method handling HTTP GET requests. The returned object will be sent to the client as
   * "array of json" media type.Method returns all available components.
   *
   * @return List<Component>
   */
  @GET
  @Path("allComponents")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Component> getAllComponents() {

    Component component1 =
        (Component) POJOFactory.POJOContext.getSpringContext().getBean("component");
    component1.setVersion("1.0");
    component1.setIdentity("max");
    Component component2 =
        (Component) POJOFactory.POJOContext.getSpringContext().getBean("component");
    component2.setVersion("2.0");
    component2.setIdentity("makus");
    Component component3 =
        (Component) POJOFactory.POJOContext.getSpringContext().getBean("component");
    component3.setVersion("3.0");
    component3.setIdentity("peter");
    List<Component> list = new ArrayList<Component>();
    list.add(component1);
    list.add(component2);
    list.add(component3);
    return list;
  }

  /**
   * Method handling HTTP GET requests. The returned object will be sent to the client as
   * "array of json" media type.Method returns all components that respect specific conditions.
   *
   * @param conditions String
   * @return List of Components
   */
  @GET
  @Path("searchedComponents")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Component> searchComponent(String conditions) {
    Component component1 =
        (Component) POJOFactory.POJOContext.getSpringContext().getBean("component");
    component1.setVersion("4.0");
    component1.setIdentity("Bill");
    Component component2 =
        (Component) POJOFactory.POJOContext.getSpringContext().getBean("component");
    component2.setVersion("5.0");
    component2.setIdentity("Willy");
    List<Component> list = new ArrayList<Component>();
    list.add(component1);
    list.add(component2);
    return list;
  }

  /**
   * Method handling HTTP GET requests. The returned object will be sent to the client as "json"
   * media type.Method deletes a specific component.
   *
   * @param identifier String
   * @param version String
   * @return Bool indicates if a specific component has been deleted successfully.
   */
  public Bool deleteComponent(String identifier, String version) {
    Bool bool = (Bool) POJOFactory.POJOContext.getSpringContext().getBean("bool");
    return bool;
  }
}
