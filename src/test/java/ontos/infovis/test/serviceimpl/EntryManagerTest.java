package ontos.infovis.test.serviceimpl;

import java.net.MalformedURLException;

import junit.framework.Assert;
import ontos.infovis.service.db.FilesystemManager;
import ontos.infovis.serviceimpl.EntryManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EntryManagerTest {
	private final String filePath = "src/test/resources/local-ontology.ttl";
	private final EntryManager entryManager = EntryManager.getInstance();
	
	@Before
	public void before() {
		// set the local test file as target
		try {
			entryManager.setTargetURL(filePath);
		}
		catch (MalformedURLException mUrlEx) {
			Assert.fail("test file not found, malformed URL");
		}
		
		// clear all of its contents before testing
		FilesystemManager.clearFile(entryManager.getTargetURL());
	}
	
    @Test
    public void test() {
    	
    }
	
	@After
	public void after() {
		// also clear after testing
		FilesystemManager.clearFile(entryManager.getTargetURL());
	}
}
