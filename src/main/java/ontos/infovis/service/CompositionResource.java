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

import ontos.infovis.pojo.Composition;
import ontos.infovis.pojo.Param;
import ontos.infovis.pojo.Response;
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
	boolean registeredComposition = EntryManager.getInstance().registerComposition(composition);

    Response response = (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
    response.setBool(registeredComposition);
    response.setError("createComposition  no error");
    response.setException("createComposition  no exception");

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
    boolean updatedComposition = EntryManager.getInstance().updateComposition(composition);

    Response response = (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
    response.setBool(updatedComposition);
    response.setError("updateComposition no error");
    response.setException("updateComposition no error");

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
    return EntryManager.getInstance().getComposition(uri, version);
  }

  /**
   * Method handling HTTP DELETE requests. Method deletes a specific composition.
   *
   * @param Param POJO
   * @return Bool indicates if a specific composition has been deleted successfully.
   */

  @DELETE
  @Path("compositions")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteComposition(Param param) {
    boolean deletedComposition = EntryManager.getInstance().deleteComposition(param);
	  
    Response response = (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
    response.setBool(deletedComposition);
    response.setError("deleteComposition no errors");
    response.setException("deleteComposition no errors");
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
