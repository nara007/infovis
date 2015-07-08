package ontos.infovis.service;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
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

import java.sql.SQLException;

/**
 * @author Ye Song
 * @version 1.0
 * @date 15-6-29
 * @copyright infovis@tu-dresden.de
 */
public class Test {
  public static void main(String[] args) {

    QueryExecutionFactory qef =
        new QueryExecutionFactoryHttp("http://dbpedia.org/sparql", "http://dbpedia.org");

    qef = new QueryExecutionFactoryRetry(qef, 5, 10000);

    // Add delay in order to be nice to the remote server (delay in milli seconds)
    qef = new QueryExecutionFactoryDelay(qef, 5000);

    // Set up a cache
    // Cache entries are valid for 1 day
    long timeToLive = 24l * 60l * 60l * 1000l;

    // This creates a 'cache' folder, with a database file named 'sparql.db'
    // Technical note: the cacheBackend's purpose is to only deal with streams,
    // whereas the frontend interfaces with higher level classes - i.e. ResultSet and Model

      CacheBackend cacheBackend = null;
      try {
//          cacheBackend = CacheCoreH2.create("./sparql", timeToLive, true);
          cacheBackend = CacheCoreH2.create(true,"~/cache","sparql", timeToLive, true);
      } catch (ClassNotFoundException e) {
          e.printStackTrace();
      } catch (SQLException e) {
          e.printStackTrace();
      }
      CacheFrontend cacheFrontend = new CacheFrontendImpl(cacheBackend);
    qef = new QueryExecutionFactoryCacheEx(qef, cacheFrontend);



    QueryExecutionFactoryHttp foo = qef.unwrap(QueryExecutionFactoryHttp.class);
    System.out.println(foo);

    // Add pagination
//    qef = new QueryExecutionFactoryPaginated(qef, 900);

    // Create a QueryExecution object from a query string ...
    QueryExecution qe =
        qef.createQueryExecution("select distinct ?Concept where {[] a ?Concept} LIMIT 100");


    // and run it.
    ResultSet rs = qe.execSelect();
    System.out.println(ResultSetFormatter.asText(rs));
  }
}
