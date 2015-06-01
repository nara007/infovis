package ontos.infovis.service.db;

import java.net.URL;

import org.apache.jena.atlas.json.JsonObject;

import com.hp.hpl.jena.query.Query;

/**
 * @author Franz
 * This interface represents all load/save calls for components and compositions.
 * Depending on the concrete implementation, the calls will either address a local file or a SPARQL endpoint.
 */
public interface IPersistenceService {
	/**
	 * @param sourceURL the {@link URL} to the TTL file or SPARQL endpoint from which the components are read
	 * @param searchQuery a SPARQL {@link Query} to find an component by ID and version
	 * @return {@link JsonObject} containing all found components in RDFJSON format
	 */
	public JsonObject loadComponent(URL sourceURL, Query searchQuery);
	
	/**
	 * @param sourceURL the {@link URL} to the TTL file or SPARQL endpoint from which the compositions are read
	 * @param searchQuery a SPARQL {@link Query} to find an composition by ID and version
	 * @return {@link JsonObject} containing all found compositions in RDFJSON format
	 */
	public JsonObject loadComposition(URL sourceURL, Query searchQuery);
	
	/**
	 * @param targetURL the {@link URL} to the TTL file or SPARQL endpoint into which the components are saved
	 * @param component a {@link JsonObject} containing one or more components in RDFJSON format
	 * @return boolean true if the components have been saved, false if not 
	 */
	public boolean saveComponent(URL targetURL, JsonObject component);
	
	/**
	 * @param targetURL the {@link URL} to the TTL file or SPARQL endpoint into which the compositions are saved
	 * @param composition a {@link JsonObject} containing one or more compositions in RDFJSON format
	 * @return boolean true if the compositions have been saved, false if not 
	 */
	public boolean saveComposition(URL targetURL, JsonObject composition);
}
