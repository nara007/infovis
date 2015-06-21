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

//TODO remove redundant code 

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

    		// the URI of the parent component is the ID
    		Resource r = resIt.next();
    		Statement parent = r.getProperty(model.createProperty(BASE_URL, "parent"));
    		c.setId(parent.asTriple().getObject().getLocalName());
    		
    		// iterate over all triples
    		StmtIterator stmtIt = r.listProperties();
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
        				case "creationDate": c.setCreation_date(l.getLong()); break;
        				case "lastUpdate": c.setLast_update(l.getLong()); break;
        				case "screenshot": c.setScreenshot(l.getString()); break;
        				//case "dependencies": break;
        				//case "resources": break;
        				default: break; // properties that do not match a model field are ignored
        			}
    			}
    		}
    		
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
			// create or get a resource for all versions of this component
			Resource r = model.createResource(BASE_URL+c.getId());
			
			// a resource of type Component represents this version of the component
			Resource componentResource = model.createResource(BASE_URL+c.getId()+"/"+c.getVersion());
			componentResource.addProperty(RDF.type, BASE_URL+"Component");
			componentResource.addProperty(model.createProperty(BASE_URL, "parent"), r);
			
			// add this version and set it as the latest
			Property versions = model.createProperty(BASE_URL, "versions");
			r.addProperty(versions, componentResource);
			// TODO: This is currently overwritten by the model merge in FilesystemManager.java
			Property latestVersion = model.createProperty(BASE_URL, "latestVersion");
			r.removeAll(latestVersion);
			r.addProperty(latestVersion, componentResource);
			
			
			// get all fields and turn them into literals
			Literal title = model.createTypedLiteral(c.getTitle(), XSD.xstring.getURI());
			Literal description = model.createTypedLiteral(c.getDescription(), XSD.xstring.getURI());
			Literal version = model.createTypedLiteral(c.getVersion(), XSD.xstring.getURI());
			Literal owner = model.createTypedLiteral(c.getOwner(), XSD.xstring.getURI());
			Literal creationDate = model.createTypedLiteral(c.getCreation_date(), XSD.xlong.getURI());
			Literal lastUpdate = model.createTypedLiteral(c.getLast_update(), XSD.xlong.getURI());
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
			
			// the blank node holds all literals with their properties
			componentResource.addLiteral(titleProp, title);
			componentResource.addLiteral(descriptionProp, description);
			componentResource.addLiteral(versionProp, version);
			componentResource.addLiteral(ownerProp, owner);
			componentResource.addLiteral(creationDateProp, creationDate);
			componentResource.addLiteral(lastUpdateProp, lastUpdate);
			componentResource.addLiteral(screenshotProp, screenshot);
			//componentResource.addLiteral(dependenciesProp, dependencies);
			//componentResource.addLiteral(resourcesProp, resources);
		}
		
    	return model;
    }

	/**
	 * @param model the {@link Model} containing compositions, which will be parsed into a {@link Composition} Array
	 * @returns {@link Composition} all Compositions found in the model
	 */
    static public Composition[] parseAsCompositions(Model model) {
    	// the return array
    	ArrayList<Composition> compositions= new ArrayList<Composition>();

    	// find all resources of the given type
    	ResIterator resIt = model.listSubjectsWithProperty(RDF.type, BASE_URL+"Composition");
    	
    	// parse all found resources into a composition
    	while(resIt.hasNext()) {
    		Composition c = new Composition();

    		// the URI of the parent component is the ID
    		Resource r = resIt.next();
    		Statement parent = r.getProperty(model.createProperty(BASE_URL, "parent"));
    		c.setId(parent.asTriple().getObject().getLocalName());
    		
    		// iterate over all triples
    		StmtIterator stmtIt = r.listProperties();
    		while(stmtIt.hasNext()) {
    			Statement s = stmtIt.next();
    			
    			// all triples that end with a literal might be composition property
    			if(s.getObject().isLiteral()) {
    				Property p = s.getPredicate();
        			Literal l = s.getObject().asLiteral();
        			
        			// switch depending on the property name
        			switch(p.getLocalName()) {
        				case "title": c.setTitle(l.getString()); break;
        				case "description": c.setDescription(l.getString()); break;
        				case "version": c.setVersion(l.getString()); break;
        				case "owner": c.setOwner(l.getString()); break;
        				case "creationDate": c.setCreation_date(l.getLong()); break;
        				case "lastUpdate": c.setLast_update(l.getLong()); break;
        				case "structure": c.setStructure(l.getString()); break;
        				//case "rights": break;
        				//case "components": break;
        				default: break; // properties that do not match a model field are ignored
        			}
    			}
    		}
    		
    		compositions.add(c);
    	}
    	
    	return compositions.toArray(new Composition[compositions.size()]);
    }
    
	/**
	 * @param compositions the {@link Composition} Array, which will be parsed into a {@link Model}
	 * @returns {@link Model} the model containing all compositions
	 */
    static public Model parseAsModel(Composition[] compositions) {
    	// create an empty model
		Model model = ModelFactory.createDefaultModel();
    	
		// iterate trough all compositions
		for(Composition c: compositions) {
			// create or get a resource for all versions of this composition
			Resource r = model.createResource(BASE_URL+c.getId());
			
			// a resource of type Composition represents this version of the composition
			Resource compositionResource = model.createResource(BASE_URL+c.getId()+"/"+c.getVersion());
			compositionResource.addProperty(RDF.type, BASE_URL+"Composition");
			compositionResource.addProperty(model.createProperty(BASE_URL, "parent"), r);
			
			// add this version and set it as the latest
			Property versions = model.createProperty(BASE_URL, "versions");
			r.addProperty(versions, compositionResource);
			// TODO: This is currently overwritten by the model merge in FilesystemManager.java
			Property latestVersion = model.createProperty(BASE_URL, "latestVersion");
			r.removeAll(latestVersion);
			r.addProperty(latestVersion, compositionResource);
			
			
			// get all fields and turn them into literals
			Literal title = model.createTypedLiteral(c.getTitle(), XSD.xstring.getURI());
			Literal description = model.createTypedLiteral(c.getDescription(), XSD.xstring.getURI());
			Literal version = model.createTypedLiteral(c.getVersion(), XSD.xstring.getURI());
			Literal owner = model.createTypedLiteral(c.getOwner(), XSD.xstring.getURI());
			Literal creationDate = model.createTypedLiteral(c.getCreation_date(), XSD.xlong.getURI());
			Literal lastUpdate = model.createTypedLiteral(c.getLast_update(), XSD.xlong.getURI());
			Literal structure = model.createTypedLiteral(c.getStructure(), XSD.xstring.getURI());
			//Literal rights = model.createTypedLiteral(c.getRights(), XSD.xstring.getURI());
			//Literal components = model.createTypedLiteral(c.getComponents(), XSD.xstring.getURI());
			
			// create matching properties
			Property titleProp = model.createProperty(BASE_URL, "title");
			Property descriptionProp = model.createProperty(BASE_URL, "description");
			Property versionProp = model.createProperty(BASE_URL, "version");
			Property ownerProp = model.createProperty(BASE_URL, "owner");
			Property creationDateProp = model.createProperty(BASE_URL, "creationDate");
			Property lastUpdateProp = model.createProperty(BASE_URL, "lastUpdate");
			Property structureProp = model.createProperty(BASE_URL, "structure");
			//Property rightsProp = model.createProperty(BASE_URL, "rights");
			//Property componentsProp = model.createProperty(BASE_URL, "components");
			
			// the blank node holds all literals with their properties
			compositionResource.addLiteral(titleProp, title);
			compositionResource.addLiteral(descriptionProp, description);
			compositionResource.addLiteral(versionProp, version);
			compositionResource.addLiteral(ownerProp, owner);
			compositionResource.addLiteral(creationDateProp, creationDate);
			compositionResource.addLiteral(lastUpdateProp, lastUpdate);
			compositionResource.addLiteral(structureProp, structure);
			//compositionResource.addLiteral(rightsProp, rights);
			//compositionResource.addLiteral(componentsProp, components);
		}
		
    	return model;
    }
}
