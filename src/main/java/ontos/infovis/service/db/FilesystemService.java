package ontos.infovis.service.db;

import java.net.URL;

import org.apache.jena.atlas.json.JsonObject;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * @author Franz
 * This class handles all load/save calls for components and compositions from/into the filesystem.
 * All calls address a local TTL file - it is intended to use only one file for each ontology.
 */
public class FilesystemService implements IPersistenceService {	
	/**
	 * @param sourceURL the {@link URL} to the local TTL file from which the components are read
	 * @param searchQuery a SPARQL {@link Query} to find an component by ID and version, will be used as a DESCRIBE query
	 * @return {@link JsonObject} containing all found components in RDFJSON format
	 */
	public JsonObject loadComponent(URL sourceURL, Query searchQuery) {
		// read an existing or empty model from the file 
		Model model = FilesystemManager.readModel(sourceURL);
		
		// create a JSON result object
		JsonObject jsonResult = new JsonObject();
		
		// execute as describe query on the loaded model, execSelect() returns all results as a model
		searchQuery.setQueryDescribeType();
		QueryExecution queryExec = QueryExecutionFactory.create(searchQuery, model);
		Model m = queryExec.execDescribe();
			
		// parse the result model into a JSON object
		jsonResult = JSONModelParser.parseAsJsonObject(m); 
		
		return jsonResult;
	}

	/**
	 * @param sourceURL the {@link URL} to the local TTL file from which the compositions are read
	 * @param searchQuery a SPARQL {@link Query} to find an composition by ID and version, will be used as a DESCRIBE query
	 * @return {@link JsonObject} containing all found compositions in RDFJSON format
	 */
	public JsonObject loadComposition(URL sourceURL, Query searchQuery) {
		// this delegates to loadComponent
		return loadComponent(sourceURL, searchQuery);
	}
	
	/**
	 * @param targetURL the {@link URL} to the local TTL file into which the components are saved
	 * @param component a {@link JsonObject} containing one or more components in RDFJSON format
	 * @return boolean true if the components have been saved
	 */
	public boolean saveComponent(URL targetURL, JsonObject component) {
		// read an existing or empty model from the file
		Model model = FilesystemManager.readModel(targetURL);
		
		// read JSON object and add it to the model		
		Model componentModel = JSONModelParser.parseAsModel(component);
		model = model.union(componentModel);
		
		// save back to the file		
		return FilesystemManager.saveModel(targetURL, model);
	}

	/**
	 * @param targetURL the {@link URL} to the local TTL file into which the compositions are saved
	 * @param composition a {@link JsonObject} containing one or more compositions in RDFJSON format
	 * @return boolean true if the compositions have been saved
	 */
	public boolean saveComposition(URL targetURL, JsonObject composition) {
		// this delegates to saveComponent
		return saveComponent(targetURL, composition);
	}
}
