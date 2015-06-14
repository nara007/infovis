package ontos.infovis.service;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ontos.infovis.pojo.Component;
import ontos.infovis.pojo.Param;
import ontos.infovis.pojo.Response;
import ontos.infovis.serviceimpl.EntryManager;
import ontos.infovis.util.ApplicationManager;


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
  public Response registerComponent(Component cmp) { cmp.setVersion("0.0.1c");
	boolean registeredComponent = EntryManager.getInstance().registerComponent(cmp);
    
    Response response = (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
    response.setBool(registeredComponent);
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
	boolean updatedComponent = EntryManager.getInstance().updateComponent(cmp);
	  
	Response response = (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
    response.setBool(updatedComponent);
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
   * @return Component if succeed otherwise null
   */
  @GET
  @Path("components")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public Component getComponent(@QueryParam("uri") String uri, @DefaultValue("1.0.0") @QueryParam("version") String version) {
    return  EntryManager.getInstance().getComponent(uri, version);
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
    return Arrays.asList(EntryManager.getInstance().getAllComponents());
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
    return Arrays.asList(EntryManager.getInstance().searchComponent(conditions));
  }

  /**
   * Method handling HTTP DELETE requests.
   * Method deletes a specific component.
   *
   * @param Param POJO
   * @return Bool indicates if a specific component has been deleted successfully.
   */
  @DELETE
  @Path("components")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
//  public Response deleteComponent(@QueryParam("uri") String uri, @QueryParam("version") String version) {
      public Response deleteComponent(Param param){
	  boolean deletedComponent = EntryManager.getInstance().deleteComponent(param);
	  
      Response response = (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
      response.setBool(deletedComponent);
      response.setError("deleteComponent no errors");
      response.setException("deleteComponent no exceptions");
      return response;
  }
}
