package ontos.infovis.service.db;

import java.net.URL;

import org.apache.jena.atlas.json.JsonObject;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.rdf.model.Model;

public class FilesystemService implements IPersistenceService {		
	@Override
	public JsonObject loadComponent(URL sourceURL, Query searchQuery) {
		// read an existing or empty model from the file 
		Model model = FilesystemManager.readModel(sourceURL);
		
		// create a JSON result object
		JsonObject jsonResult = new JsonObject();
		
		// execute query on the loaded model, execSelect() returns all results as a model
		try (QueryExecution queryExec = QueryExecutionFactory.create(searchQuery, model)) {
			Model m = queryExec.execDescribe();
			
			// parse the result model into a JSON object
			jsonResult = JSONModelParser.parseAsJsonObject(m); 
		}
		// TODO: Catch errors.
		
		return jsonResult;
	}

	@Override
	public JsonObject loadComposition(URL sourceURL, Query searchQuery) {
		// this delegates to loadComponent
		return loadComponent(sourceURL, searchQuery);
	}
	
	@Override
	public boolean saveComponent(URL targetURL, JsonObject component) {
		// read an existing or empty model from the file
		Model model = FilesystemManager.readModel(targetURL);
		
		// read JSON object and add it to the model		
		Model componentModel = JSONModelParser.parseAsModel(component);
		model = model.union(componentModel);
		
		// save back to the file		
		return FilesystemManager.saveModel(targetURL, model);
	}

	@Override
	public boolean saveComposition(URL targetURL, JsonObject composition) {
		// this delegates to saveComponent
		return saveComponent(targetURL, composition);
	}
}
