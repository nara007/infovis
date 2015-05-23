package ontos.infovis.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ontos.infovis.service.db.FilesystemService;
import ontos.infovis.service.db.IPersistenceService;

import org.apache.jena.atlas.json.JSON;
import org.apache.jena.atlas.json.JsonObject;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
    	// test filesystem service
    	try {
	    	IPersistenceService pService = new FilesystemService();
	    	
	    	// test file
			File testFile = new File("ontology/local.ttl");
			URL testURL = testFile.toURI().toURL();
	    	
			// test writing
			String jsonString = "{\"http://example.org/book/book4\" : {\"http://purl.org/dc/elements/1.1/title\" : [{\"type\" : \"literal\", \"value\" : \"ThisIsATest\"}]}}";
	        InputStream inStream = new ByteArrayInputStream(jsonString.getBytes());
	    	JsonObject testComponent = JSON.parse(inStream);
	    	
	    	pService.saveComponent(testURL, testComponent);
	    	
	    	// test loading
	    	Query testQuery = QueryFactory.create("DESCRIBE ?book WHERE {?book <http://purl.org/dc/elements/1.1/title> ?title .}");
	    	testQuery.setLimit(20);
	    	testQuery.setOffset(0);
	    	
	    	return pService.loadComponent(testURL, testQuery).toString();
    	}
    	catch(MalformedURLException mUrlEx) {
    		System.out.println(mUrlEx);
    	}
    	
        return "Test";
    }
}
