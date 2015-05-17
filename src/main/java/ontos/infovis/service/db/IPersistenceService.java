package ontos.infovis.service.db;

import java.net.URL;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

import com.hp.hpl.jena.query.Query;

/**
 * @author Franz
 * This interface represents all load/save calls for components and compositions.
 * Depending on the concrete implementation, the calls will either address a local file or a SPARQL endpoint.
 */
public interface IPersistenceService {
	public JsonArray loadComponent(URL sourceURL, Query searchQuery);
	public JsonArray loadComposition(URL sourceURL, Query searchQuery);
	
	public boolean saveComponent(URL targetURL, JsonObject component);
	public boolean saveComposition(URL targetURL, JsonObject composition);
}
