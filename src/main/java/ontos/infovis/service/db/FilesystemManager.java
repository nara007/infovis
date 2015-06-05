package ontos.infovis.service.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * This class provides helper functions for reading/writing models into TTL files.
 * @author Franz
 */
public class FilesystemManager {
	// format of the written an read files
	static public Lang fileLang = Lang.TTL;
	
	/**
	 * @param fileURL the {@link URL} to the local file, from which the model is read
	 * @return {@link Model} the whole ontology saved at the given file 
	 */
	static public Model readModel(URL fileURL) {		
		// create an empty RDF model
		Model model = ModelFactory.createDefaultModel();
		
		try {
			// read all resources from the local TTL file into the model
			model = RDFDataMgr.loadModel(fileURL.toURI().getPath(), fileLang) ;
		}
		catch (URISyntaxException uriSynEx) {
			// TODO handle URISyntaxException
			uriSynEx.printStackTrace(System.out);
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
			RDFDataMgr.write(outStream, model, fileLang);
			
			// close stream
			outStream.close();
		}
		catch (URISyntaxException uriSynEx) {
			// TODO handle URISyntaxException
			uriSynEx.printStackTrace(System.out);
			return false;
		}
		catch (FileNotFoundException fileEx) {
			// TODO handle FileNotFoundException
			fileEx.printStackTrace(System.out);
			return false;
		}
		catch (IOException ioEx) {
			// TODO handle IOException
			ioEx.printStackTrace(System.out);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param fileURL the {@link URL} to the local file which shall be cleared
	 * @return boolean true if an empty model has been saved to this file
	 */
	static public boolean clearFile(URL fileURL) {
		// create an empty model an save it into the file
		Model model = ModelFactory.createDefaultModel();
		
		return saveModel(fileURL, model);
	}
}
