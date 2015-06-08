package ontos.infovis.service;

import ontos.infovis.pojo.Composition;
import ontos.infovis.pojo.Param;
import ontos.infovis.pojo.Response;
import ontos.infovis.util.ApplicationManager;
import ontos.infovis.util.DummyData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

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

	    if (!DummyData.dummyData.addComposition(composition)) {
	      Response response =
	          (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
	      response.setBool(false);
	      response.setError("Composition already exists...");
	      response.setException("Composition already exists...");
	      return response;
	    } else {
	      Response response =
	          (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
	      response.setBool(true);
	      response.setError("registerComposition no error");
	      response.setException("registerComposition no exception");
	      return response;

	    }
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

	  if (!DummyData.dummyData.updateComposition(composition)) {
	      Response response =
	          (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
	      response.setBool(false);
	      response.setError("updateComposition failed");
	      response.setException("updateComposition failed");
	      return response;
	    } else {
	      Response response =
	          (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
	      response.setBool(true);
	      response.setError("updateComposition no error");
	      response.setException("updateComposition no exception");
	      return response;
	    }
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
  public Composition getComposition(@QueryParam("uri") String uri,
      @DefaultValue("1.0.0") @QueryParam("version") String version) {

    return DummyData.dummyData.getComposition(uri, version);
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

	  if (!DummyData.dummyData.deleteComposition(param.getUri(), param.getVersion())) {
	      Response response =
	          (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
	      response.setBool(false);
	      response.setError("deleteComposition failed...");
	      response.setException("deleteComposition failed...");
	      return response;
	    } else {
	      Response response =
	          (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
	      response.setBool(true);
	      response.setError("deleteComposition no errors");
	      response.setException("deleteComposition no exceptions");
	      return null;
	      }
	
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
  public List<Composition> getAllComponents() {

	return DummyData.dummyData.getAllCompositions();
  }
}
