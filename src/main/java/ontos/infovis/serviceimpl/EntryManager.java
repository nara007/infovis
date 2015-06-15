package ontos.infovis.serviceimpl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import ontos.infovis.pojo.Component;
import ontos.infovis.pojo.Composition;
import ontos.infovis.pojo.Param;
import ontos.infovis.service.db.FilesystemService;
import ontos.infovis.service.db.IPersistenceService;
import ontos.infovis.service.db.PojoModelParser;
import ontos.infovis.serviceimpl.EntryException.EntryAlreadyExistsException;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateRequest;
import com.hp.hpl.jena.vocabulary.RDF;

//TODO remove redundant code 

public class EntryManager {
	static private EntryManager instance; 
	
	// some URIs
	private String versionUri = PojoModelParser.BASE_URL + "version";
	private String versionsUri = PojoModelParser.BASE_URL + "versions";
	private String componentUri = PojoModelParser.BASE_URL + "Component";
	private String compositionUri = PojoModelParser.BASE_URL + "Composition";
	
	static public EntryManager getInstance() {
		if(instance == null) instance = new EntryManager();
		
		return instance;
	}
	
	private IPersistenceService pService = new FilesystemService();
	private URL targetURL = null;
	
	private EntryManager() {
		this.setTargetURL("src/main/resources/local-ontology.ttl");
	}

	public URL getTargetURL() {
		return targetURL;
	}

	public void setTargetURL(String targetURLPath) {
		try {
			this.targetURL = new File(targetURLPath).toURI().toURL();
		} catch (MalformedURLException mUrlEx) {
			// TODO handle MalformedURLException
			mUrlEx.printStackTrace(System.out);
		}
	}
	
	/* manage components */
	
	public boolean registerComponent(Component component) throws EntryAlreadyExistsException {
		String uri = PojoModelParser.BASE_URL + component.getId();
		String version = component.getVersion();
		
		Query askQuery = QueryFactory.create("ASK  {<"+uri+"> <"+versionsUri+"> ?version . ?version <"+RDF.type+"> \""+componentUri+"\" . ?version <"+versionUri+"> \""+version+"\" .}");
		
		Component[] components = {component};
		if(pService.checkComponent(targetURL, askQuery)) return false;
		else return pService.saveComponents(this.targetURL, components);
	}
	
	public boolean updateComponent(Component component) {
		String uri = PojoModelParser.BASE_URL + component.getId();
		String version = component.getVersion();
		
		Query askQuery = QueryFactory.create("ASK  {<"+uri+"> <"+versionsUri+"> ?version . ?version <"+RDF.type+"> \""+componentUri+"\" . ?version <"+versionUri+"> \""+version+"\" .}");
		
		if(!pService.checkComponent(targetURL, askQuery)) return false;
		else return false; // TODO implement this
	}

	public Component[] searchComponent(String searchString) {
		// TODO implement this
		Component[] components = {};
		return components;
	}
	
	public Component getComponent(String uri, String version) {
		uri = PojoModelParser.BASE_URL + uri;
		
		Query askQuery = QueryFactory.create("ASK  {<"+uri+"> <"+versionsUri+"> ?version . ?version <"+RDF.type+"> \""+componentUri+"\" . ?version <"+versionUri+"> \""+version+"\" .}");
		Query searchQuery = QueryFactory.create("CONSTRUCT {?version ?p ?o .} WHERE{<"+uri+"> <"+versionsUri+"> ?version . ?version ?p ?o . ?version <"+versionUri+"> \""+version+"\" .}");

		if(!pService.checkComponent(targetURL, askQuery)) return null;
		else return pService.loadComponents(targetURL, searchQuery)[0];
	}
	
	public Component[] getAllComponents() {
		Query searchQuery = QueryFactory.create("CONSTRUCT {?version ?p ?o .} WHERE{?component <"+versionsUri+"> ?version . ?version ?p ?o .}");
		
		return pService.loadComponents(targetURL, searchQuery);
	}

	public boolean deleteComponent(Param param) {
		String uri = PojoModelParser.BASE_URL + param.getUri();
		String version = param.getVersion();

		Query askQuery = QueryFactory.create("ASK  {<"+uri+"> <"+versionsUri+"> ?version . ?version <"+RDF.type+"> \""+componentUri+"\" . ?version <"+versionUri+"> \""+version+"\" .}");
		UpdateRequest deleteUpdateRequest = UpdateFactory.create("DELETE WHERE{<"+uri+"> <"+versionsUri+"> ?version . ?version ?p ?o . ?version <"+versionUri+"> \""+version+"\" .}");
		
		if(!pService.checkComponent(targetURL, askQuery)) return false;
		else return pService.deleteComponents(this.targetURL, deleteUpdateRequest);
	}
	
	/* manage compositions */
	
	public boolean registerComposition(Composition composition) {
		String uri = PojoModelParser.BASE_URL + composition.getId();
		String version = composition.getVersion();
		
		Query askQuery = QueryFactory.create("ASK  {<"+uri+"> <"+versionsUri+"> ?version . ?version <"+RDF.type+"> \""+compositionUri+"\" . ?version <"+versionUri+"> \""+version+"\" .}");
		
		Composition[] compositions = {composition};
		if(pService.checkComposition(targetURL, askQuery)) return false;
		else return pService.saveCompositions(this.targetURL, compositions);
	}
	
	public boolean updateComposition(Composition composition) {
		String uri = PojoModelParser.BASE_URL + composition.getId();
		String version = composition.getVersion();
		
		Query askQuery = QueryFactory.create("ASK  {<"+uri+"> <"+versionsUri+"> ?version . ?version <"+RDF.type+"> \""+compositionUri+"\" . ?version <"+versionUri+"> \""+version+"\" .}");
		
		if(!pService.checkComposition(targetURL, askQuery)) return false;
		else return registerComposition(composition); // TODO this delegates to registerComposition
	}
	
	public Composition getComposition(String uri, String version) {
		uri = PojoModelParser.BASE_URL + uri;
		
		Query askQuery = QueryFactory.create("ASK  {<"+uri+"> <"+versionsUri+"> ?version . ?version <"+RDF.type+"> \""+compositionUri+"\" . ?version <"+versionUri+"> \""+version+"\" .}");
		Query searchQuery = QueryFactory.create("CONSTRUCT {?version ?p ?o .} WHERE{<"+uri+"> <"+versionsUri+"> ?version . ?version ?p ?o . ?version <"+versionUri+"> \""+version+"\" .}");

		if(!pService.checkComposition(targetURL, askQuery)) return null;
		else return pService.loadCompositions(targetURL, searchQuery)[0];
	}
	
	public Composition[] getAllCompositions() {
		Query searchQuery = QueryFactory.create("CONSTRUCT {?version ?p ?o .} WHERE{?composition <"+versionsUri+"> ?version . ?version ?p ?o .}");
		
		return pService.loadCompositions(targetURL, searchQuery);
	}

	public boolean deleteComposition(Param param) {
		String uri = PojoModelParser.BASE_URL + param.getUri();
		String version = param.getVersion();

		Query askQuery = QueryFactory.create("ASK  {<"+uri+"> <"+versionsUri+"> ?version . ?version <"+RDF.type+"> \""+compositionUri+"\" . ?version <"+versionUri+"> \""+version+"\" .}");
		UpdateRequest deleteUpdateRequest = UpdateFactory.create("DELETE WHERE{<"+uri+"> <"+versionsUri+"> ?version . ?version ?p ?o . ?version <"+versionUri+"> \""+version+"\" .}");
		
		if(!pService.checkComposition(targetURL, askQuery)) return false;
		else return pService.deleteCompositions(this.targetURL, deleteUpdateRequest);
	}
}
