package ontos.infovis.service.db;

import java.net.URL;

import ontos.infovis.pojo.Component;
import ontos.infovis.pojo.Composition;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.update.GraphStore;
import com.hp.hpl.jena.update.GraphStoreFactory;
import com.hp.hpl.jena.update.UpdateAction;
import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateProcessor;
import com.hp.hpl.jena.update.UpdateRequest;

// TODO remove redundant code 

/**
 * This class handles all load/save calls for components and compositions from/into the filesystem.
 * All calls address a local TTL file - it is intended to use only one file for each ontology.
 * @author Franz
 */
public class FilesystemService implements IPersistenceService {	
	/**
	 * @param sourceURL the {@link URL} to the local TTL file from which the components are read
	 * @param searchQuery a SPARQL {@link Query} to find components by ID and version, will be used as a CONSTRUCT query
	 * @return {@link Component} Array containing all found components
	 */
	@Override
	public Component[] loadComponents(URL sourceURL, Query searchQuery) {
		// read an existing or empty model from the file 
		Model model = FilesystemManager.readModel(sourceURL);
		
		// execute as construct query on the loaded model, returns all results as a model
		searchQuery.setQueryConstructType();
		QueryExecution queryExec = QueryExecutionFactory.create(searchQuery, model);
		Model m = queryExec.execConstruct();

		// parse the result model into Component Beans
		return PojoModelParser.parseAsComponents(m);
	}

	
	/**
	 * @param sourceURL the {@link URL} to the local TTL file from which the compositions are read
	 * @param searchQuery a SPARQL {@link Query} to find composition by ID and version, will be used as a CONSTRUCT query
	 * @return {@link Composition} Array containing all found compositions
	 */
	@Override
	public Composition[] loadCompositions(URL sourceURL, Query searchQuery) {
		// read an existing or empty model from the file 
		Model model = FilesystemManager.readModel(sourceURL);
		
		// execute as construct query on the loaded model, returns all results as a model
		searchQuery.setQueryConstructType();
		QueryExecution queryExec = QueryExecutionFactory.create(searchQuery, model);
		Model m = queryExec.execConstruct();

		// parse the result model into Component Beans
		return PojoModelParser.parseAsCompositions(m);
	}

	/**
	 * @param targetURL the {@link URL} to the local TTL file into which the components are saved
	 * @param components a {@link Component} Array containing one or more components
	 * @return boolean true if the components have been saved
	 */
	@Override
	public boolean saveComponents(URL targetURL, Component[] components) {
		// read an existing or empty model from the file
		Model model = FilesystemManager.readModel(targetURL);
		
		// read JSON object and add it to the model		
		Model componentModel = PojoModelParser.parseAsModel(components);
		model = model.union(componentModel);
		
		// save back to the file		
		return FilesystemManager.saveModel(targetURL, model);
	}

	/**
	 * @param targetURL the {@link URL} to the local TTL file into which the compositions are saved
	 * @param compositions a {@link Composition} Array containing one or more compositions
	 * @return boolean true if the compositions have been saved
	 */
	@Override
	public boolean saveCompositions(URL targetURL, Composition[] compositions) {
		// read an existing or empty model from the file
		Model model = FilesystemManager.readModel(targetURL);
		
		// read JSON object and add it to the model		
		Model compositionModel = PojoModelParser.parseAsModel(compositions);
		model = model.union(compositionModel);
		
		// save back to the file		
		return FilesystemManager.saveModel(targetURL, model);
	}

	/**
	 * @param targetURL the {@link URL} to the TTL file from which the components are deleted
	 * @param searchQuery a SPARQL {@link UpdateRequest} to delete components by ID and version
	 * @return boolean true if the components have been deleted, false if not
	 */
	@Override
	public boolean deleteComponents(URL targetURL, UpdateRequest deleteUpdateRequest) {
		// read an existing or empty model from the file
		Model model = FilesystemManager.readModel(targetURL);

		// execute the update
		UpdateAction.execute(deleteUpdateRequest, model);
		
		// save back to the file		
		return FilesystemManager.saveModel(targetURL, model);
	}

	/**
	 * @param targetURL the {@link URL} to the TTL file from which the compositions are deleted
	 * @param searchQuery a SPARQL {@link UpdateRequest} to delete compositions by ID and version
	 * @return boolean true if the compositions have been deleted, false if not
	 */
	@Override
	public boolean deleteCompositions(URL targetURL, UpdateRequest deleteUpdateRequest) {
		// read an existing or empty model from the file
		Model model = FilesystemManager.readModel(targetURL);

		// execute the update
		UpdateAction.execute(deleteUpdateRequest, model);
		
		// save back to the file		
		return FilesystemManager.saveModel(targetURL, model);
	}
}
