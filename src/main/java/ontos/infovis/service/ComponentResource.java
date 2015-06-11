package ontos.infovis.service;

import ontos.infovis.pojo.Component;
import ontos.infovis.pojo.Param;
import ontos.infovis.pojo.Response;
import ontos.infovis.util.ApplicationManager;
import ontos.infovis.util.DummyData;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * component resources rest service
 *
 * @author Ye Song
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


    if (!DummyData.dummyData.addComponent(cmp)) {
      Response response =
          (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
      response.setBool(false);
      response.setError("Component already exists...");
      response.setException("Component already exists...");
      return response;
    } else {
      Response response =
          (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
      response.setBool(true);
      response.setError("registerComponent no error");
      response.setException("registerComponent no exception");
      return response;

    }

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

    if (!DummyData.dummyData.updateComponent(cmp)) {
      Response response =
          (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
      response.setBool(false);
      response.setError("updateComponent failed");
      response.setException("updateComponent failed");
      return response;
    } else {
      Response response =
          (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
      response.setBool(true);
      response.setError("updateComponent no error");
      response.setException("updateComponent no exception");
      return response;
    }
  }

  /**
   * Method handling HTTP GET requests. The returned object will be sent to the client as "json"
   * media type.Method returns a specific component.
   *
   * @param uri String
   * @param version String
   * @return Component if succeed otherwise null
   */
  @GET
  @Path("components")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public Component getComponent(@QueryParam("uri") String uri,
      @DefaultValue("1.0.0") @QueryParam("version") String version) {

    return DummyData.dummyData.getComponent(uri, version);
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

    return DummyData.dummyData.getAllComponents();
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
   * Method handling HTTP DELETE requests. Method deletes a specific component.
   *
   * @param uri
   * @param version
   * @return Response json object.
   */
  @DELETE
  @Path("components/{uri}/{version}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteComponent(@PathParam("uri") String uri, @PathParam("version") String version) {

    if (!DummyData.dummyData.deleteComponent(uri, version)) {
      Response response =
          (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
      response.setBool(false);
      response.setError("deleteComponent failed...");
      response.setException("deleteComponent failed...");
      return response;
    } else {
      Response response =
          (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
      response.setBool(true);
      response.setError("deleteComponent no errors");
      response.setException("deleteComponent no exceptions");
      return response;
    }

  }

}
