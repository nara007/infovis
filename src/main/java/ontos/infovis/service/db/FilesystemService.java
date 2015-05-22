package ontos.infovis.service.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;
import org.apache.jena.riot.system.stream.StreamManager;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.util.FileManager;

public class FilesystemService implements IPersistenceService {
	private Model readModel(URL fileURL) {		
		try {
			// create an empty RDF model
			Model model = ModelFactory.createDefaultModel();

			// read all resources from the local TTL file into the model
			InputStream inStream = FileManager.get().open("ontology/local.ttl");
			if (inStream == null) {
				throw new IllegalArgumentException("File: " + fileURL + " not found");
			}
			model.read(inStream, null, "TTL");
			
			// TODO: Close stream?
			inStream.close();
			
			return model;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private boolean saveModel(URL fileURL, Model model) {
		try {
			// get the reference to the output file
			File outFile = new File(fileURL.toURI());
		
			// create an output stream and write to it
			FileOutputStream outStream = new FileOutputStream(outFile);
			model.write(outStream, "TTL");
			
			// TODO: Close stream?
			outStream.close();
			
		} catch (URISyntaxException e) {
			// TODO Handle URISyntaxException.
			return false;
		} catch (FileNotFoundException e) {
			// TODO Handle FileNotFoundException.
			return false;
		} catch (IOException e) {
			// TODO Handle IOException
			return false;
		}
		
		return true;
	}
	
	@Override
	public JsonArray loadComponent(URL sourceURL, Query searchQuery) {
		// read an existing or empty model from the file 
		Model model = readModel(sourceURL);
			
		// create a result array
		JsonArray jsonResults = new JsonArray();
		
		// execute query on the loaded model, execSelect() returns multiple results
		try (QueryExecution queryExec = QueryExecutionFactory.create(searchQuery, model)) {
			
			ResultSet results = queryExec.execSelect();
			
			// iterate through all results
			while(results.hasNext()) {
				QuerySolution querySoln = results.nextSolution();
				System.out.println("querySoln: "+querySoln);
				// try to convert the results into JSON // TODO: Try ResultParser instead.
				JsonObject jsonResult = new JsonObject();
				
				// TODO: Remove this and get the correct component values.
				//jsonResult.put("title", querySoln.getLiteral("title").getString());
				/*
				RDFNode x = querySoln.get("varName") ;       // Get a result variable by name.
				Resource r = querySoln.getResource("VarR") ; // Get a result variable - must be a resource
		      	Literal l = querySoln.getLiteral("VarL") ;   // Get a result variable - must be a literal
		      	*/
		      	
		      	// add add all results to the return object
		      	jsonResults.add(jsonResult);
		    }
		}
		// TODO: Catch errors.
		
		return jsonResults;
	}

	@Override
	public JsonArray loadComposition(URL sourceURL, Query query) {
		return null;
	}

	// TODO: Remove this.
	private static final String BASE_URL = "http://example.org/"; 
	
	@Override
	public boolean saveComponent(URL targetURL, JsonObject component) {
		// read an existing or empty model from the file
		Model model = readModel(targetURL);
		
		// add the new resource
		Resource res = model.createResource();
		res.addLiteral(ResourceFactory.createProperty(BASE_URL, "title"), component.get("title"));
		
		// save back to the file
		saveModel(targetURL, model);
		
		return false;
	}

	@Override
	public boolean saveComposition(URL targetURL, JsonObject composition) {
		return false;
	}

}
