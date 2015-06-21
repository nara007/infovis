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
import ontos.infovis.serviceimpl.EntryException.EntryAlreadyExistsException;
import ontos.infovis.serviceimpl.EntryException.EntryNotFoundException;
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
 * @throws EntryNotFoundException 
   */
  @POST
  @Path("components")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response registerComponent(Component cmp) throws EntryAlreadyExistsException, EntryNotFoundException {
    Response response = (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
  
    try {
		boolean registeredComponent = EntryManager.getInstance().registerComponent(cmp);
	    response.setBool(registeredComponent);
	    response.setError("Registration of Component was successfully. No errors.");
	    response.setException("Registration of Component was successfully. No exceptions.");
	}
	catch (EntryAlreadyExistsException ex) {
		System.out.println("Couldn't register Component, because an entry for Component: " + cmp.getId() + " with the name: " + cmp.getDescription() + " and version: " + cmp.getVersion() + " already exists.");
		
		response.setError("Couldn't register Component!");
	    response.setException("Entry for Component: " + cmp.getId() + " with the name: " + cmp.getDescription() + " and version: " + cmp.getVersion() + " already exists.");
	   
	}

    return response;
  }

  /**
   * Method handling HTTP PUT requests. The returned object will be sent to the client as "json"
   * media type.Method updates a component.
   *
   * @param Component POJO (converted from json automatically)
   * @return Response object(converted to json automatically)
   * @throws EntryNotFoundException 
   */
  @PUT
  @Path("components")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateComponent(Component cmp) throws EntryNotFoundException {
	Response response = (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
	  
	try {
		boolean updatedComponent = EntryManager.getInstance().updateComponent(cmp);
	    response.setBool(updatedComponent);
	    response.setError("Updated Component successfully. No error occured.");
	    response.setException("Updated Component successfully. No exception occured.");
	}
	catch (EntryNotFoundException ex) {
		System.out.println("Couldn't update Component, because an entry for Component: " + cmp.getId() + " with the name: " + cmp.getDescription() + " and version: " + cmp.getVersion() + " was not found.");

		response.setError("Couldn't update Component");
	    response.setException("Entry for Component: " + cmp.getId() + " with the name: " + cmp.getDescription() + " and version: " + cmp.getVersion() + " was not found."); 	
	}
    
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
    Component cmp = null;
    //Response response = (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
	  
	try {
    	cmp = EntryManager.getInstance().getComponent(uri, version);
    }
	catch (EntryNotFoundException ex) {
		System.out.println("Couldn't find requested Component with uri: " + uri + " and version: " + version );
	}
    
	return cmp;
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

  //TODO Is this needed?
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
    return Arrays.asList(EntryManager.getInstance().getAllComponents());
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
	  Response response = (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
	  
	  try {
		  boolean deletedComponent = EntryManager.getInstance().deleteComponent(param);
	      response.setBool(deletedComponent);
	      response.setError("Deleted Component successfully. No errors occured.");
	      response.setException("Deleted Component successfully. No excpetion occured.");
	  }
	  catch (EntryNotFoundException ex) {
		  System.out.println("Couldn't find Component with requested uri: " + param.getVersion() + " and version: " + param.getUri() + " was found.");
		  response.setError("Couldn't delete Component.");
	      response.setException("No entry with requested uri: " + param.getVersion() + " and version: " + param.getUri() +" was found");
	  }
	  
      return response;
  }
}
