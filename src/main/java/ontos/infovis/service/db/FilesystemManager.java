package ontos.infovis.service.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class FilesystemManager {
	static public Model readModel(URL fileURL) {		
		try {
			// create an empty RDF model
			Model model = ModelFactory.createDefaultModel();

			// read all resources from the local TTL file into the model
			InputStream inStream = FileManager.get().open("ontology/local.ttl");
			if (inStream == null) {
				throw new IllegalArgumentException("File: " + fileURL + " not found");
			}
			model.read(inStream, null, "TTL");
			
			// close stream
			inStream.close();
			
			return model;
		} 
		catch (IOException e) {
			// TODO: Handle IOException.
		}
		
		return null;
	}
	
	static public boolean saveModel(URL fileURL, Model model) {
		try {
			// get the reference to the output file
			File outFile = new File(fileURL.toURI());
		
			// create an output stream and write to it
			FileOutputStream outStream = new FileOutputStream(outFile);
			model.write(outStream, "TTL");
			
			// close stream
			outStream.close();	
		}
		catch (URISyntaxException e) {
			// TODO Handle URISyntaxException.
			return false;
		}
		catch (FileNotFoundException e) {
			// TODO Handle FileNotFoundException.
			return false;
		}
		catch (IOException e) {
			// TODO Handle IOException.
			return false;
		}
		
		return true;
	}
}
