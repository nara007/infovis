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

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateRequest;

//TODO remove redundant code 

public class EntryManager {
	static private EntryManager instance; 
	
	static public EntryManager getInstance() {
		if(instance == null) instance = new EntryManager();
		
		return instance;
	}
	
	private IPersistenceService pService = new FilesystemService();
	private URL targetURL = null;
	
	private EntryManager() {
		this.setTargetURL("ontology/local.ttl");
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
	
	public boolean registerComponent(Component component) {
		Component[] components = {component};
		return pService.saveComponents(this.targetURL, components);
	}
	
	public boolean updateComponent(Component component) {
		// TODO this delegates to registerComponent
		return registerComponent(component);
	}

	public Component[] searchComponent(String searchString) {
		// TODO implement this
		Component[] components = {};
		return components;
	}
	
	public Component getComponent(String uri, String version) {
		uri = PojoModelParser.BASE_URL + uri;
		String versionUri = PojoModelParser.BASE_URL + "version";
		String versionsUri = PojoModelParser.BASE_URL + "versions";
		Query searchQuery = QueryFactory.create("CONSTRUCT {?version ?p ?o .} WHERE{<"+uri+"> <"+versionsUri+"> ?version . ?version ?p ?o . ?version <"+versionUri+"> \""+version+"\" .}");
		
		return pService.loadComponents(targetURL, searchQuery)[0];
	}
	
	public Component[] getAllComponents() {
		String versionsUri = PojoModelParser.BASE_URL+"versions";
		Query searchQuery = QueryFactory.create("CONSTRUCT {?version ?p ?o .} WHERE{?component <"+versionsUri+"> ?version . ?version ?p ?o .}");
		
		return pService.loadComponents(targetURL, searchQuery);
	}

	public boolean deleteComponent(Param param) {
		String uri = param.getUri();
		String version = param.getVersion();
		
		uri = "dummy-communes";
		version = "0.0.1a";
		
		String versionUri = PojoModelParser.BASE_URL + "version";
		String versionsUri = PojoModelParser.BASE_URL + "versions";
		uri = PojoModelParser.BASE_URL + uri;
		UpdateRequest deleteUpdateRequest = UpdateFactory.create("DELETE WHERE{<"+uri+"> <"+versionsUri+"> ?version . ?version ?p ?o . ?version <"+versionUri+"> \""+version+"\" .}");
		
		return pService.deleteComponents(this.targetURL, deleteUpdateRequest);
	}
	
	/* manage compositions */
	
	public boolean registerComposition(Composition composition) {
		Composition[] compositions = {composition};
		return pService.saveCompositions(this.targetURL, compositions);
	}
	
	public boolean updateComposition(Composition composition) {
		// TODO this delegates to registerComposition
		return registerComposition(composition);
	}

	public Composition[] searchComposition(String searchString) {
		// TODO implement this
		Composition[] compositions = {};
		return compositions;
	}
	
	public Composition getComposition(String uri, String version) {
		uri = PojoModelParser.BASE_URL + uri;
		String versionUri = PojoModelParser.BASE_URL + "version";
		String versionsUri = PojoModelParser.BASE_URL + "versions";
		Query searchQuery = QueryFactory.create("CONSTRUCT {?version ?p ?o .} WHERE{<"+uri+"> <"+versionsUri+"> ?version . ?version ?p ?o . ?version <"+versionUri+"> \""+version+"\" .}");
		
		return pService.loadCompositions(targetURL, searchQuery)[0];
	}
	
	public Composition[] getAllCompositions() {
		String versionsUri = PojoModelParser.BASE_URL+"versions";
		Query searchQuery = QueryFactory.create("CONSTRUCT {?version ?p ?o .} WHERE{?composition <"+versionsUri+"> ?version . ?version ?p ?o .}");
		
		return pService.loadCompositions(targetURL, searchQuery);
	}

	public boolean deleteComposition(Param param) {
		String uri = param.getUri();
		String version = param.getVersion();
		
		String versionUri = PojoModelParser.BASE_URL + "version";
		String versionsUri = PojoModelParser.BASE_URL + "versions";
		uri = PojoModelParser.BASE_URL + uri;
		UpdateRequest deleteUpdateRequest = UpdateFactory.create("DELETE WHERE{<"+uri+"> <"+versionsUri+"> ?version . ?version ?p ?o . ?version <"+versionUri+"> \""+version+"\" .}");
		
		return pService.deleteCompositions(this.targetURL, deleteUpdateRequest);
	}
}
