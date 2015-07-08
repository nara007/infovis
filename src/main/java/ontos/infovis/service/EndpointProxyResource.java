package ontos.infovis.service;

import ontos.infovis.util.ApplicationManager;
import org.aksw.jena_sparql_api.cache.core.QueryExecutionFactoryCacheEx;
import org.aksw.jena_sparql_api.cache.extra.CacheBackend;
import org.aksw.jena_sparql_api.cache.extra.CacheFrontend;
import org.aksw.jena_sparql_api.cache.extra.CacheFrontendImpl;
import org.aksw.jena_sparql_api.cache.h2.CacheCoreH2;
import org.aksw.jena_sparql_api.core.QueryExecutionFactory;
import org.aksw.jena_sparql_api.delay.core.QueryExecutionFactoryDelay;
import org.aksw.jena_sparql_api.http.QueryExecutionFactoryHttp;
import org.aksw.jena_sparql_api.pagination.core.QueryExecutionFactoryPaginated;
import org.aksw.jena_sparql_api.retry.core.QueryExecutionFactoryRetry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class EndpointProxyResource provides a unified interface for accessing remote SPARQL endpoint.
 * 
 * @author Ye Song
 * @version 1.0
 * @date 15-6-29
 * @copyright infovis@tu-dresden.de
 */
@Path("/")
public class EndpointProxyResource {

  static {
    long timeToLive = 24l * 60l * 60l * 1000l;
    QueryExecutionFactory response =
        (QueryExecutionFactory) ApplicationManager.appManager.getSpringContext().getBean(
            "queryExecutionFactoryPaginated");
    CacheBackend cacheBackend = null;
    try {
//      cacheBackend = CacheCoreH2.create("sparql", timeToLive, true);
      cacheBackend = CacheCoreH2.create(true,"./cache","sparql", timeToLive, true);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    CacheFrontend cacheFrontend = new CacheFrontendImpl(cacheBackend);
    response = new QueryExecutionFactoryCacheEx(response, cacheFrontend);
  }


  @GET
  @Path("endpoint")
  @Produces(MediaType.TEXT_PLAIN)
  public String queryMySPARQL(@QueryParam("endpoint") String endpoint,
      @QueryParam("query") String query, @QueryParam("format") String format) {
    String service = null;
    String defaultGraphName = null;


    QueryExecutionFactory response =
        (QueryExecutionFactory) ApplicationManager.appManager.getSpringContext().getBean(
            "queryExecutionFactoryPaginated");
    return null;
  }
}
