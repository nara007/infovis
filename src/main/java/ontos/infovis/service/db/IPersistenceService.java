package ontos.infovis.service.db;

import java.net.URL;

import ontos.infovis.pojo.Component;
import ontos.infovis.pojo.Composition;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.update.UpdateRequest;


/**
 * This interface represents all load/save calls for components and compositions.
 * Depending on the concrete implementation, the calls will either address a local file or a SPARQL endpoint.
 * @author Franz
 */
public interface IPersistenceService {
	/**
	 * @param sourceURL the {@link URL} to the TTL file or SPARQL endpoint from which the components are read
	 * @param searchQuery a SPARQL {@link Query} to find components by ID and version
	 * @return {@link Component} Array containing all found components
	 */
	public Component[] loadComponents(URL sourceURL, Query searchQuery);
	
	/**
	 * @param sourceURL the {@link URL} to the TTL file or SPARQL endpoint from which the compositions are read
	 * @param searchQuery a SPARQL {@link Query} to find compositions by ID and version
	 * @return {@link Composition} Array containing all found compositions
	 */
	public Composition[] loadCompositions(URL sourceURL, Query searchQuery);
	
	/**
	 * @param targetURL the {@link URL} to the TTL file or SPARQL endpoint into which the components are saved
	 * @param components a {@link Component} Array containing one or more components
	 * @return boolean true if the components have been saved, false if not
	 */
	public boolean saveComponents(URL targetURL, Component[] components);
	
	/**
	 * @param targetURL the {@link URL} to the TTL file or SPARQL endpoint into which the compositions are saved
	 * @param compositions a {@link Composition} Array containing one or more compositions
	 * @return boolean true if the compositions have been saved, false if not
	 */
	public boolean saveCompositions(URL targetURL, Composition[] compositions);
	
	/**
	 * @param sourceURL the {@link URL} to the TTL file or SPARQL endpoint from which the components are deleted
	 * @param searchQuery a SPARQL {@link UpdateRequest} to delete components by ID and version
	 * @return boolean true if the components have been deleted, false if not
	 */
	public boolean deleteComponents(URL sourceURL, UpdateRequest deleteUpdateRequest);
	
	/**
	 * @param sourceURL the {@link URL} to the TTL file or SPARQL endpoint from which the compositions are deleted
	 * @param searchQuery a SPARQL {@link UpdateRequest} to delete compositions by ID and version
	 * @return boolean true if the compositions have been deleted, false if not
	 */
	public boolean deleteCompositions(URL sourceURL, UpdateRequest deleteUpdateRequest);
}
