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
	/**
	 * @param fileURL the {@link URL} to the local file, from which the model is read
	 * @return {@link Model} the whole ontology saved at the given file 
	 */
	static public Model readModel(URL fileURL) {		
		// create an empty RDF model
		Model model = ModelFactory.createDefaultModel();
		
		try {
			// read all resources from the local TTL file into the model
			InputStream inStream = FileManager.get().open("ontology/local.ttl");
			if (inStream == null) {
				throw new IllegalArgumentException("File: " + fileURL + " not found");
			}
			model.read(inStream, null, "TTL");
			
			// close stream
			inStream.close();
		} 
		catch (IOException e) {
			// TODO handle IOException
		}
		
		return model;
	}
	
	/**
	 * @param fileURL the {@link URL} to the local file, into which the model is saved
	 * @param model the {@link Model} the whole ontology which will be written into the given file 
	 * @return boolean true if the model has been saved
	 */
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
			// TODO handle URISyntaxException
			return false;
		}
		catch (FileNotFoundException e) {
			// TODO handle FileNotFoundException
			return false;
		}
		catch (IOException e) {
			// TODO handle IOException
			return false;
		}
		
		return true;
	}
}
