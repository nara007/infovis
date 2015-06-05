package ontos.infovis.service.db;

import ontos.infovis.pojo.Component;
import ontos.infovis.pojo.Composition;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * This class provides helper functions for parsing Jena Models into Pojo Objects and back again.
 * @author Franz
 */
public class PojoModelParser {
	// namespace
	static private final String BASE_URL = "http://ontos.infovis/";
	
	/**
	 * @param model the {@link Model} containing components, which will be parsed into a {@link Component} Array
	 * @returns {@link Component} all Components found in the model
	 */
    static public Component[] parseAsComponents(Model model) {
    	// the return array
    	Component components[] = {};
    	
    	// find all resources of the given type
    	ResIterator resIt = model.listSubjectsWithProperty(RDF.type, BASE_URL+"Component");
    	
    	while(resIt.hasNext()) {
    		Component c = new Component();
    		
    		Resource r = resIt.next();
    		Model m = r.getModel();
    		
    		Literal title = m.getProperty(BASE_URL, "title").asLiteral();
    		
    		c.setTitle(title.getValue().toString());
    	}
    	
    	return components;
    }
    
	/**
	 * @param components the {@link Component} Array, which will be parsed into a {@link Model}
	 * @returns {@link Model} the model containing all components
	 */
    static public Model parseAsModel(Component[] components) {
    	// create an empty model
		Model model = ModelFactory.createDefaultModel();
    	
		// iterate trough all components
		for(Component c: components) {
			// create a resource for this component
			Resource r = model.createResource(c.getId());
			
			// set the type of this resource
			r.addProperty(RDF.type, BASE_URL+"Component");
			
			// get all fields and turn them into literals
			Literal title = model.createTypedLiteral(c.getTitle());
			Literal description = model.createTypedLiteral(c.getDescription());
			Literal version = model.createTypedLiteral(c.getVersion());
			Literal owner = model.createTypedLiteral(c.getOwner());
			Literal creationDate = model.createTypedLiteral(c.getCreation_date());
			Literal lastUpdate = model.createTypedLiteral(c.getLast_update());
			Literal screenshot = model.createTypedLiteral(c.getScreenshot());
			//Literal dependencies = model.createTypedLiteral(c.getDependencies());
			//Literal resources = model.createTypedLiteral(c.getResources());
			
			// create matching properties
			Property titleProp = model.createProperty(BASE_URL, "title");
			Property descriptionProp = model.createProperty(BASE_URL, "description");
			Property versionProp = model.createProperty(BASE_URL, "version");
			Property ownerProp = model.createProperty(BASE_URL, "owner");
			Property creationDateProp = model.createProperty(BASE_URL, "creationDate");
			Property lastUpdateProp = model.createProperty(BASE_URL, "lastUpdate");
			Property screenshotProp = model.createProperty(BASE_URL, "screenshot");
			//Property dependenciesProp = model.createProperty(BASE_URL, "dependencies");
			//Property resourcesProp = model.createProperty(BASE_URL, "resources");
			
			// add all literals with their properties
			r.addLiteral(titleProp, title);
			r.addLiteral(descriptionProp, description);
			r.addLiteral(versionProp, version);
			r.addLiteral(ownerProp, owner);
			r.addLiteral(creationDateProp, creationDate);
			r.addLiteral(lastUpdateProp, lastUpdate);
			r.addLiteral(screenshotProp, screenshot);
			//r.addLiteral(dependenciesProp, dependencies);
			//r.addLiteral(resourcesProp, resources);
		}
		
    	return model;
    }
}
