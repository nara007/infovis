package ontos.infovis.service;

import ontos.infovis.pojo.Component;
import ontos.infovis.pojo.Response;
import ontos.infovis.util.ApplicationManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Ye Song
 * @description component resources
 * @date 15-5-23
 * @copyright infovis@tu-dresden.de
 */
@Path("/")
public class ComponentResource {

  /**
   * Method handling HTTP POST requests. The returned object will be sent to the client as "json"
   * media type.Method registers a component.
   * 
   * @param Component POJO (converted from json automatically)
   * @return Response object(converted to json automatically)
   */
  @POST
  @Path("components")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response registerComponent(Component cmp) {

    System.out.println(cmp);
    Response response =
        (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
    response.setBool(true);
    response.setError("registerComponent no error");
    response.setException("registerComponent no exception");

    return response;
  }

  /**
   * Method handling HTTP PUT requests. The returned object will be sent to the client as "json"
   * media type.Method updates a component.
   *
   * @param Component POJO (converted from json automatically)
   * @return Response object(converted to json automatically)
   */
  @PUT
  @Path("components")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateComponent(Component cmp) {
    System.out.println(cmp);
    Response response =
        (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
    response.setBool(true);
    response.setError("updateComponent no error");
    response.setException("updateComponent no exception");
    return response;
  }

  /**
   * Method handling HTTP GET requests. The returned object will be sent to the client as "json"
   * media type.Method returns a specific component.
   *
   * @param uri String
   * @param version String
   * @return Component
   */
  @GET
  @Path("components")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public Component getComponent(@QueryParam("uri") String uri,
      @DefaultValue("1.0.0") @QueryParam("version") String version) {
    // Component component
    // =(Component)ApplicationManager.appManager.getSpringContext().getBean("component");
    Component component =
        (Component) ApplicationManager.appManager.getSpringContext().getBean("component");
    System.out.println(uri + " " + version);
    return component;
  }

  /**
   * Method handling HTTP GET requests. The returned object will be sent to the client as
   * "array of json" media type.Method returns all available components.
   *
   * @return List<Component>
   */
  @GET
  @Path("allcomponents")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Component> getAllComponents() {

    Component component1 = null;
    Component component2 = null;
    Component component3 = null;
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
    Component component1 = null;
    List<Component> list = new ArrayList<Component>();
    list.add(component1);
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
  public void deleteComponent(String identifier, String version) {
    // Bool bool = (Bool) ApplicationManager.appManager.getSpringContext().getBean("bool");
    // return bool;
  }
}
