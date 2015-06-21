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
import com.hp.hpl.jena.vocabulary.DCTerms;
import com.hp.hpl.jena.vocabulary.DC_11;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.XSD;

//TODO remove redundant code 

/**
 * This class provides helper functions for parsing Jena Models into Pojo Objects and back again.
 * @author Franz
 */
public class PojoModelParser {
	// name space URI
	static public final String BASE_URL = "http://ontos.infovis/";
	
	// property URIs
	static public final String TITLE_PROP_URI = DC_11.title.getURI();
	static public final String DESCRIPTION_PROP_URI = DC_11.description.getURI();
	static public final String VERSION_PROP_URI = BASE_URL+"version";
	static public final String CREATOR_PROP_URI = DC_11.creator.getURI();
	static public final String CREATED_PROP_URI = DCTerms.created.getURI();
	static public final String MODIFIED_PROP_URI = DCTerms.modified.getURI();
	static public final String SCREENSHOT_PROP_URI = BASE_URL+"screenshot";
	static public final String STRUCTURE_PROP_URI = BASE_URL+"structure";
	static public final String VERSION_OF_PROP_URI = DCTerms.isVersionOf.getURI();
	static public final String HAS_VERSION_PROP_URI = DCTerms.hasVersion.getURI();
	
	// type URIs
	static public final String COMPONENT_TYPE_URI = BASE_URL+"Component";
	static public final String COMPOSITION_TYPE_URI = BASE_URL+"Composition";
	
	/**
	 * @param model the {@link Model} containing components, which will be parsed into a {@link Component} Array
	 * @returns {@link Component} all Components found in the model
	 */
    static public Component[] parseAsComponents(Model model) {
    	// the return array
    	ArrayList<Component> components= new ArrayList<Component>();

    	// find all resources of the given type
    	ResIterator resIt = model.listSubjectsWithProperty(RDF.type, COMPONENT_TYPE_URI);
    	
    	// parse all found resources into a component
    	while(resIt.hasNext()) {
    		Component c = new Component();

    		// the URI of the parent component is the ID
    		Resource r = resIt.next();
    		Statement parent = r.getProperty(model.createProperty(VERSION_OF_PROP_URI));
    		c.setId(parent.asTriple().getObject().getLocalName());
    		
    		// iterate over all triples
    		StmtIterator stmtIt = r.listProperties();
    		while(stmtIt.hasNext()) {
    			Statement s = stmtIt.next();
    			
    			// all triples that end with a literal might be a component property
    			if(s.getObject().isLiteral()) {
    				Property p = s.getPredicate();
        			Literal l = s.getObject().asLiteral();
        			
        			// set depending on the property URI
        			if(p.getURI().equals(TITLE_PROP_URI)) c.setTitle(l.getString());
        			else if(p.getURI().equals(DESCRIPTION_PROP_URI)) c.setDescription(l.getString());
        			else if(p.getURI().equals(VERSION_PROP_URI)) c.setVersion(l.getString());
        			else if(p.getURI().equals(CREATOR_PROP_URI)) c.setOwner(l.getString());
        			else if(p.getURI().equals(CREATED_PROP_URI)) c.setCreation_date(l.getLong());
        			else if(p.getURI().equals(MODIFIED_PROP_URI)) c.setLast_update(l.getLong());
        			else if(p.getURI().equals(SCREENSHOT_PROP_URI)) c.setScreenshot(l.getString());
        			// TODO dependencies and resources
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
			componentResource.addProperty(RDF.type, COMPONENT_TYPE_URI);
			componentResource.addProperty(model.createProperty(VERSION_OF_PROP_URI), r);
			
			// add this version and set it as the latest
			Property versions = model.createProperty(HAS_VERSION_PROP_URI);
			r.addProperty(versions, componentResource);
			// TODO this is currently overwritten by the model merge in FilesystemManager.java
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
			// TODO dependencies and resources
			
			// create matching properties
			Property titleProp = model.createProperty(TITLE_PROP_URI);
			Property descriptionProp = model.createProperty(DESCRIPTION_PROP_URI);
			Property versionProp = model.createProperty(VERSION_PROP_URI); 
			Property ownerProp = model.createProperty(CREATOR_PROP_URI);
			Property creationDateProp = model.createProperty(CREATED_PROP_URI);
			Property lastUpdateProp = model.createProperty(MODIFIED_PROP_URI);
			Property screenshotProp = model.createProperty(SCREENSHOT_PROP_URI); 
			// TODO dependencies and resources
			
			// the blank node holds all literals with their properties
			componentResource.addLiteral(titleProp, title);
			componentResource.addLiteral(descriptionProp, description);
			componentResource.addLiteral(versionProp, version);
			componentResource.addLiteral(ownerProp, owner);
			componentResource.addLiteral(creationDateProp, creationDate);
			componentResource.addLiteral(lastUpdateProp, lastUpdate);
			componentResource.addLiteral(screenshotProp, screenshot);
			// TODO dependencies and resources
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
    	ResIterator resIt = model.listSubjectsWithProperty(RDF.type, COMPOSITION_TYPE_URI);
    	
    	// parse all found resources into a composition
    	while(resIt.hasNext()) {
    		Composition c = new Composition();

    		// the URI of the parent component is the ID
    		Resource r = resIt.next();
    		Statement parent = r.getProperty(model.createProperty(VERSION_OF_PROP_URI));
    		c.setId(parent.asTriple().getObject().getLocalName());
    		
    		// iterate over all triples
    		StmtIterator stmtIt = r.listProperties();
    		while(stmtIt.hasNext()) {
    			Statement s = stmtIt.next();
    			
    			// all triples that end with a literal might be a composition property
    			if(s.getObject().isLiteral()) {
    				Property p = s.getPredicate();
        			Literal l = s.getObject().asLiteral();
        			
        			// set depending on the property URI
        			if(p.getURI().equals(TITLE_PROP_URI)) c.setTitle(l.getString());
        			else if(p.getURI().equals(DESCRIPTION_PROP_URI)) c.setDescription(l.getString());
        			else if(p.getURI().equals(VERSION_PROP_URI)) c.setVersion(l.getString());
        			else if(p.getURI().equals(CREATOR_PROP_URI)) c.setOwner(l.getString());
        			else if(p.getURI().equals(CREATED_PROP_URI)) c.setCreation_date(l.getLong());
        			else if(p.getURI().equals(MODIFIED_PROP_URI)) c.setLast_update(l.getLong());
        			else if(p.getURI().equals(STRUCTURE_PROP_URI)) c.setStructure(l.getString());
        			// TODO rights and components
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
			compositionResource.addProperty(RDF.type, COMPOSITION_TYPE_URI);
			compositionResource.addProperty(model.createProperty(VERSION_OF_PROP_URI), r);
			
			// add this version and set it as the latest
			Property versions = model.createProperty(HAS_VERSION_PROP_URI);
			r.addProperty(versions, compositionResource);
			// TODO this is currently overwritten by the model merge in FilesystemManager.java
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
			// TODO rights and components
			
			// create matching properties
			Property titleProp = model.createProperty(TITLE_PROP_URI);
			Property descriptionProp = model.createProperty(DESCRIPTION_PROP_URI);
			Property versionProp = model.createProperty(VERSION_PROP_URI);
			Property ownerProp = model.createProperty(CREATOR_PROP_URI);
			Property creationDateProp = model.createProperty(CREATED_PROP_URI);
			Property lastUpdateProp = model.createProperty(MODIFIED_PROP_URI);
			Property structureProp = model.createProperty(STRUCTURE_PROP_URI);
			// TODO rights and components
			
			// the blank node holds all literals with their properties
			compositionResource.addLiteral(titleProp, title);
			compositionResource.addLiteral(descriptionProp, description);
			compositionResource.addLiteral(versionProp, version);
			compositionResource.addLiteral(ownerProp, owner);
			compositionResource.addLiteral(creationDateProp, creationDate);
			compositionResource.addLiteral(lastUpdateProp, lastUpdate);
			compositionResource.addLiteral(structureProp, structure);
			// TODO rights and components
		}
		
    	return model;
    }
}
