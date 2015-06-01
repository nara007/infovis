package ontos.infovis.service;

import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MainTest {
    private HttpServer httpServer;
    private Client client;
    private WebTarget webTarget;
	
    @Before
    public void testServerBefore() throws Exception {
        // set up server, client and target
        httpServer = Main.startServer();
        client = ClientBuilder.newClient();
        webTarget = client.target(Main.BASE_URI);
    }

    @After
    public void testServerAfter() throws Exception {
    	// shut down server
        httpServer.shutdownNow();
    }

    @Test
    public void testServer() {
    	// check if server has started
    	assertTrue("server is active", httpServer.isStarted());
    	
    	// check if server is available
        Response response = webTarget.path("status").request().options();
        assertEquals("http status code", 200, response.getStatus());
    }
}
