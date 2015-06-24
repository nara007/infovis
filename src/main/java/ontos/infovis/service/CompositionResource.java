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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ontos.infovis.pojo.Composition;
import ontos.infovis.pojo.Response;
import ontos.infovis.serviceimpl.EntryException.EntryAlreadyExistsException;
import ontos.infovis.serviceimpl.EntryException.EntryNotFoundException;
import ontos.infovis.serviceimpl.EntryManager;
import ontos.infovis.util.ApplicationManager;

/**
 * composition resources rest service
 *
 * @author Ye Song
 * @version 1.0
 * @date 15-6-2
 * @copyright infovis@tu-dresden.de
 */

@Path("/")
public class CompositionResource {

  /**
   * Method handling HTTP post request. The returned object will be sent to the client as "json"
   * media type.Method creates a composition.
   *
   * @param Composition POJO (converted from json automatically)
   * @return Response object(converted to json automatically)
   */
  @POST
  @Path("compositions")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createComposition(Composition composition) {
	Response response = (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
	
	try {
		boolean registeredComposition = EntryManager.getInstance().registerComposition(composition);
	    response.setBool(registeredComposition);
	    response.setError("Creation of Component was successfully. No errors.");
	    response.setException("Creation of Component was successfully. No exception");
	}
	catch (EntryAlreadyExistsException ex) {
		System.out.println("Couldn't create Composition, because an entry for Composition: " + composition.getId() + " with the name: " + composition.getDescription() + " and version: " + composition.getVersion() + " already exists.");
		
		response.setError("Couldn't creat Composition!");
	    response.setException("Entry for Composition: " + composition.getId() + " with the name: " + composition.getDescription() + " and version: " + composition.getVersion() + " already exists.");
	   
	}

    return response;
  }

  /**
   * Method handling HTTP PUT request. The returned object will be sent to the client as "json"
   * media type.Method updates a composition.
   *
   * @param Composition POJO (converted from json automatically)
   * @return Response object(converted to json automatically)
   */
  @PUT
  @Path("compositions")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateComposition(Composition composition) {
	Response response = (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
	  
	try {
		boolean updatedComposition = EntryManager.getInstance().updateComposition(composition);
	    response.setBool(updatedComposition);
	    response.setError("Updated Composition successfully. No Error occured.");
	    response.setException("Updated Composition successfully. No Exception occured.");
	}
	catch (EntryNotFoundException ex) {
		System.out.println("Couldn't update Composition, because an entry for Composition: " + composition.getId() + " with the name: " + composition.getDescription() + " and version: " + composition.getVersion() + " was not found.");

		response.setError("Couldn't update Composition");
	    response.setException("Entry for Composition: " + composition.getId() + " with the name: " + composition.getDescription() + " and version: " + composition.getVersion() + " was not found."); 	

	}

    return response;
  }

  /**
   * Method handling HTTP GET request. The returned object will be sent to the client as "json"
   * media type.Method return a composition.
   *
   * @param uri String
   * @param version String
   * @return Composition if succeed otherwise null
   */
  @GET
  @Path("compositions")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public Composition getComposition(@QueryParam("uri") String uri, @DefaultValue("1.0.0") @QueryParam("version") String version) {
    Composition composition = null;
	
    try {
    	composition = EntryManager.getInstance().getComposition(uri, version);
    }
    catch (EntryNotFoundException ex) {
    	System.out.println("Couldn't find requested Composition with uri: " + uri + " and version: " + version );
    }
    
	return composition;
  }

  /**
   * Method handling HTTP DELETE requests. Method deletes a specific composition.
   *
   * @param uri
   * @param version
   * @return Bool indicates if a specific composition has been deleted successfully.
   */

  @DELETE
  @Path("compositions/{uri}/{version}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteComposition(@PathParam("uri") String uri, @PathParam("version") String version) {
	Response response = (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
	  
	try {
		boolean deletedComposition = EntryManager.getInstance().deleteComposition(uri, version);
    	response.setBool(deletedComposition);
    	response.setError("Deleted Composition successfully. No errors occured.");
    	response.setException("Deleted Composition successfully. No exception occured.");
	}
	catch (EntryNotFoundException ex) {
		System.out.println("Couldn't find Composition with requested uri: " + version + " and version: " + uri + " was found.");
		response.setError("Couldn't delete Composition.");
	   	response.setException("No entry with requested uri: " + version + " and version: " + uri +" was found");
	}
	
    return response;
  }

  /**
   * Method handling HTTP GET requests. The returned object will be sent to the client as
   * "array of json" media type.Method returns all available compositions.
   *
   * @return List<Composition>
   */
  @GET
  @Path("allcompositions")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Composition> getAllCompositions() {
    return Arrays.asList(EntryManager.getInstance().getAllCompositions());
  }
}

