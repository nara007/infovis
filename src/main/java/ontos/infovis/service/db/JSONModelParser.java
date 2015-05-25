package ontos.infovis.service.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.jena.atlas.json.JSON;
import org.apache.jena.atlas.json.JsonObject;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class JSONModelParser {
	/**
	 * @param model the {@link Model}, which will be parsed into a {@link JsonObject}
	 * @returns {@link JsonObject} the JSON representation of the model in RDFJSON format
	 */
    static public JsonObject parseAsJsonObject(Model model) {
    	// create an OutputStream and write the model into it
    	ByteArrayOutputStream outStream = new ByteArrayOutputStream() ;
        RDFDataMgr.write(outStream, model, Lang.RDFJSON) ;
        
        // turn the stream into an InputStream and parse JSON from it
        InputStream inStream = new ByteArrayInputStream(outStream.toByteArray());
    	JsonObject jsonObject = JSON.parse(inStream);
    	
    	return jsonObject;
    }
    
	/**
	 * @param jsonObject the {@link JsonObject} in RDFJSON format, which will be parsed into a {@link Model}
	 * @returns {@link Model} the model representation of the JSON
	 */
    static public Model parseAsModel(JsonObject jsonObject) {
    	// create an InputStream from the JSON as String
    	byte[] inBytes = jsonObject.toString().getBytes();
    	InputStream inStream = new ByteArrayInputStream(inBytes);
		
    	// create an empty model and fill it with the read model
		Model model = ModelFactory.createDefaultModel();
		RDFDataMgr.read(model, inStream, Lang.RDFJSON);
    	
    	return model;
    }
}
