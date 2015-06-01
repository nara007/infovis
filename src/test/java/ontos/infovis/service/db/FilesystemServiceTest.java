package ontos.infovis.service.db;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.Assert;

import org.apache.jena.atlas.json.JSON;
import org.apache.jena.atlas.json.JsonObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

public class FilesystemServiceTest {
	private URL fileURL;
	private IPersistenceService pService;
	
	// the relative path of the test file
	String filePath = "ontology/test.ttl";
	
	// the component we want to add to the ontology as stringified JSON and the query to find this entry again
	String json = "{\"http://example.org/book/book4\" : {\"http://purl.org/dc/elements/1.1/title\" : [{\"type\" : \"literal\", \"value\" : \"ThisIsATest\"}]}}";
	Query query = QueryFactory.create("DESCRIBE ?book WHERE {?book <http://purl.org/dc/elements/1.1/title> \"ThisIsATest\" .}");

	@Before
	public void testFilesystemServiceBefore() {
		// initialize file and persistence service
		try {
			fileURL = new File(filePath).toURI().toURL();
			pService = new FilesystemService();
		}
    	catch(MalformedURLException mUrlEx) {
    		// if the URL was malformed, this test fails
    		System.out.println(mUrlEx);
    		Assert.fail("local file not found, malformed URL");
    	}
	}
	
	@After
	public void testFilesystemServiceAfter() {
		// remove all entries from the test file
		FilesystemManager.clearFile(fileURL);
	}
	
    @Test
    public void testFilesystemService() {
		// parse the JSON component into a JsonObject
	    InputStream inStream = new ByteArrayInputStream(json.getBytes());
	    JsonObject component = JSON.parse(inStream);
	    	
	    // check if the component has been saved
	    boolean saved = pService.saveComponent(fileURL, component);
	    assertTrue("component saved", saved);
	    	
	    // check if the loaded component matches the previously saved
	    JsonObject loadedComponent = pService.loadComponent(fileURL, query);
	    assertEquals("loaded component", component, loadedComponent);
    }
}
