package ontos.infovis.service.db;

import java.util.ArrayList;

import ontos.infovis.pojo.Component;
import ontos.infovis.pojo.Composition;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.XSD;

/**
 * This class provides helper functions for parsing Jena Models into Pojo Objects and back again.
 * @author Franz
 */
public class PojoModelParser {
	// namespace
	static public final String BASE_URL = "http://ontos.infovis/";
	
	/**
	 * @param model the {@link Model} containing components, which will be parsed into a {@link Component} Array
	 * @returns {@link Component} all Components found in the model
	 */
    static public Component[] parseAsComponents(Model model) {
    	// the return array
    	ArrayList<Component> components= new ArrayList<Component>();

    	// find all resources of the given type
    	ResIterator resIt = model.listSubjectsWithProperty(RDF.type, BASE_URL+"Component");
    	
    	// parse all found resources into a component
    	while(resIt.hasNext()) {
    		Component c = new Component();

    		// the local name of the found component is its ID
    		Resource r = resIt.next();
    		c.setId(r.getLocalName());
    		
    		// the model is needed for iteration over all triples
    		Model m = r.getModel();
    		StmtIterator stmtIt = m.listStatements();
    		while(stmtIt.hasNext()) {
    			Statement s = stmtIt.next();
    			
    			// all triples that end with a literal might be component property
    			if(s.getObject().isLiteral()) {
    				Property p = s.getPredicate();
        			Literal l = s.getObject().asLiteral();
        			
        			// switch depending on the property name
        			switch(p.getLocalName()) {
        				case "title": c.setTitle(l.getString()); break;
        				case "description": c.setDescription(l.getString()); break;
        				case "version": c.setVersion(l.getString()); break;
        				case "owner": c.setOwner(l.getString()); break;
        				case "creationDate": c.setCreation_date(l.getInt()); break;
        				case "lastUpdate": c.setLast_update(l.getInt()); break;
        				case "screenshot": c.setScreenshot(l.getString()); break;
        				//case "dependencies": break;
        				//case "resources": break;
        				default: break; // properties that do not match a model field are ignored
        			}
    			}
    		}
    		
    		//c.setTitle(title.getValue().toString());
    		components.add(c);
    	}
    	
    	return components.toArray(new Component[components.size()]);
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
			Resource r = model.createResource(BASE_URL+c.getId());
			
			// set the type of this resource
			r.addProperty(RDF.type, BASE_URL+"Component");
			
			// get all fields and turn them into literals
			Literal title = model.createTypedLiteral(c.getTitle(), XSD.xstring.getURI());
			Literal description = model.createTypedLiteral(c.getDescription(), XSD.xstring.getURI());
			Literal version = model.createTypedLiteral(c.getVersion(), XSD.xstring.getURI());
			Literal owner = model.createTypedLiteral(c.getOwner(), XSD.xstring.getURI());
			Literal creationDate = model.createTypedLiteral(c.getCreation_date(), XSD.xint.getURI());
			Literal lastUpdate = model.createTypedLiteral(c.getLast_update(), XSD.xint.getURI());
			Literal screenshot = model.createTypedLiteral(c.getScreenshot(), XSD.xstring.getURI());
			//Literal dependencies = model.createTypedLiteral(c.getDependencies(), XSD.xstring.getURI());
			//Literal resources = model.createTypedLiteral(c.getResources(), XSD.xstring.getURI());
			
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
