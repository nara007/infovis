package ontos.infovis.serviceimpl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import ontos.infovis.pojo.Component;
import ontos.infovis.pojo.Composition;
import ontos.infovis.service.db.FilesystemService;
import ontos.infovis.service.db.IPersistenceService;
import ontos.infovis.service.db.PojoModelParser;
import ontos.infovis.serviceimpl.EntryException.EntryAlreadyExistsException;
import ontos.infovis.serviceimpl.EntryException.EntryNotFoundException;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateRequest;
import com.hp.hpl.jena.vocabulary.RDF;

public class EntryManager {
	static private EntryManager instance; 
	
	static public EntryManager getInstance() {
		if(instance == null) instance = new EntryManager();
		
		return instance;
	}
	
	private IPersistenceService pService = new FilesystemService();
	private URL targetURL = null;
	
	private EntryManager() {
		try {
			this.setTargetURL("src/main/resources/local-ontology.ttl");
		} catch (MalformedURLException mUrlEx) {
			// TODO handle MalformedURLException
			mUrlEx.printStackTrace(System.out);
		}
	}

	public URL getTargetURL() {
		return targetURL;
	}

	public void setTargetURL(String filePath) throws MalformedURLException {
		this.targetURL = new File(filePath).toURI().toURL();
	}
	
	public void setTargetURL(URL url) {
		this.targetURL = url;
	}
	
	/* manage components */
	
	public boolean registerComponent(Component component) throws EntryAlreadyExistsException {
		Query askQuery = askEntryQuery(component.getId(), component.getVersion(), "Component");
		Component[] components = {component};
		
		if(pService.checkComponent(targetURL, askQuery)) throw new EntryAlreadyExistsException();
		else return pService.saveComponents(this.targetURL, components);
	}
	
	public boolean updateComponent(Component component) throws EntryNotFoundException {
		Query askQuery = askEntryQuery(component.getId(), component.getVersion(), "Component");
		UpdateRequest deleteUpdateRequest = deleteEntryUpdateRequest(component.getId(), component.getVersion(), "Component");
		Component[] components = {component};
		
		if(!pService.checkComponent(targetURL, askQuery)) throw new EntryNotFoundException();
		else return pService.deleteComponents(this.targetURL, deleteUpdateRequest) && pService.saveComponents(this.targetURL, components);
	}
	
	public Component getComponent(String id, String version) throws EntryNotFoundException {
		Query askQuery = askEntryQuery(id, version, "Component");
		Query constructQuery = constructEntryQuery(id, version, "Component");

		if(!pService.checkComponent(targetURL, askQuery)) throw new EntryNotFoundException();
		else return pService.loadComponents(targetURL, constructQuery)[0];
	}
	
	public Component[] getAllComponents() {
		Query constructAllQuery = constructAllEntriesQuery("Component");
		
		return pService.loadComponents(targetURL, constructAllQuery);
	}

	public boolean deleteComponent(String uri, String version) throws EntryNotFoundException {
		Query askQuery = askEntryQuery(uri, version, "Component");
		UpdateRequest deleteUpdateRequest = deleteEntryUpdateRequest(uri, version, "Component");
		
		if(!pService.checkComponent(targetURL, askQuery)) throw new EntryNotFoundException();
		else return pService.deleteComponents(this.targetURL, deleteUpdateRequest);
	}
	
	/* manage compositions */
	
	public boolean registerComposition(Composition composition) throws EntryAlreadyExistsException {
		Query askQuery = askEntryQuery(composition.getId(), composition.getVersion(), "Composition");
		Composition[] compositions = {composition};
		
		if(pService.checkComposition(targetURL, askQuery)) throw new EntryAlreadyExistsException();
		else return pService.saveCompositions(this.targetURL, compositions);
	}
	
	public boolean updateComposition(Composition composition) throws EntryNotFoundException {
		Query askQuery = askEntryQuery(composition.getId(), composition.getVersion(), "Composition");
		UpdateRequest deleteUpdateRequest = deleteEntryUpdateRequest(composition.getId(), composition.getVersion(), "Composition");
		Composition[] compositions = {composition};
		
		if(!pService.checkComposition(targetURL, askQuery)) throw new EntryNotFoundException();
		else return pService.deleteCompositions(this.targetURL, deleteUpdateRequest) && pService.saveCompositions(this.targetURL, compositions);
	}
	
	public Composition getComposition(String id, String version) throws EntryNotFoundException {
		Query askQuery = askEntryQuery(id, version, "Composition");
		Query constructQuery = constructEntryQuery(id, version, "Composition");

		if(!pService.checkComposition(targetURL, askQuery)) throw new EntryNotFoundException();
		else return pService.loadCompositions(targetURL, constructQuery)[0];
	}
	
	public Composition[] getAllCompositions() {
		Query constructAllQuery = constructAllEntriesQuery("Composition");
		
		return pService.loadCompositions(targetURL, constructAllQuery);
	}

	public boolean deleteComposition(String uri, String version) throws EntryNotFoundException {
		Query askQuery = askEntryQuery(uri, version, "Composition");
		UpdateRequest deleteUpdateRequest = deleteEntryUpdateRequest(uri, version, "Composition");
		
		if(!pService.checkComposition(targetURL, askQuery)) throw new EntryNotFoundException();
		else return pService.deleteCompositions(this.targetURL, deleteUpdateRequest);
	}
	
	/* construct queries */
	
	private Query askEntryQuery(String id, String version, String entryType) {
		final String uri = PojoModelParser.BASE_URL + id;
		final String type = PojoModelParser.BASE_URL + entryType;
		
		final String query = "ASK  {"
				+ "<"+uri+"> <"+PojoModelParser.HAS_VERSION_PROP_URI+"> ?version . "
				+ "?version <"+RDF.type+"> \""+type+"\" . "
				+ "?version <"+PojoModelParser.VERSION_PROP_URI+"> \""+version+"\" ."
		+ "}";
		
		return QueryFactory.create(query);
	}
	
	private Query constructEntryQuery(String id, String version, String entryType) {
		final String uri = PojoModelParser.BASE_URL + id;
		final String type = PojoModelParser.BASE_URL + entryType;
		
		final String query = "CONSTRUCT {?version ?p ?o . ?dependency ?dependencyP ?dependencyO . ?resource ?resourceP ?resourceO .} WHERE{"
				+ "<"+uri+"> <"+PojoModelParser.HAS_VERSION_PROP_URI+"> ?version . "
				+ "?version ?p ?o . "
				+ "?version <"+RDF.type+"> \""+type+"\" . "
				+ "?version <"+PojoModelParser.VERSION_PROP_URI+"> \""+version+"\" ."
				// TODO this is only for components
				+ "OPTIONAL {?version <"+PojoModelParser.REQUIRES_PROP_URI+"> ?dependency ."
				+ "?dependency ?dependencyP ?dependencyO} ."
				+ "OPTIONAL {?version <"+PojoModelParser.REFERENCES_PROP_URI+"> ?resource ."
				+ "?resource ?resourceP ?resourceO} ."
		+ "}";
		
		return QueryFactory.create(query);
	}
	
	private Query constructAllEntriesQuery(String entryType) {
		final String type = PojoModelParser.BASE_URL + entryType;
		
		final String query = "CONSTRUCT {?version ?p ?o .} WHERE{"
				+ "?entry <"+PojoModelParser.HAS_VERSION_PROP_URI+"> ?version . "
				+ "?version ?p ?o ."
				+ "?version <"+RDF.type+"> \""+type+"\" . "
		+ "}";
		
		return QueryFactory.create(query);
	}
	
	private UpdateRequest deleteEntryUpdateRequest(String id, String version, String entryType) {
		final String uri = PojoModelParser.BASE_URL + id;
		final String type = PojoModelParser.BASE_URL + entryType;
		
		final String updateRequest = "DELETE WHERE{"
				+ "<"+uri+"> <"+PojoModelParser.HAS_VERSION_PROP_URI+"> ?version . "
				+ "?version ?p ?o . "
				+ "?version <"+PojoModelParser.VERSION_PROP_URI+"> \""+version+"\" ."
				+ "?version <"+RDF.type+"> \""+type+"\" . "
		+ "}";

		return UpdateFactory.create(updateRequest);
	}
}
