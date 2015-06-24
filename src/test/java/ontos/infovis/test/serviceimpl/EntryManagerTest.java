package ontos.infovis.test.serviceimpl;

import java.net.MalformedURLException;

import junit.framework.Assert;
import ontos.infovis.pojo.Component;
import ontos.infovis.service.db.FilesystemManager;
import ontos.infovis.serviceimpl.EntryException.EntryAlreadyExistsException;
import ontos.infovis.serviceimpl.EntryException.EntryNotFoundException;
import ontos.infovis.serviceimpl.EntryManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.research.ws.wadl.Param;

public class EntryManagerTest {
	private final String filePath = "src/test/resources/local-ontology.ttl";
	private final EntryManager entryManager = EntryManager.getInstance();
	
	private final long cmpCount = 50;
	
	private final long cmpCreationDate = 1434883049922l;
	private final long cmpLastUpdate = 1434883070130l;
	private final String cmpScreenshot = "http://image.pic/1234";
	
	private Component createComponent(int index, String version) {
		Component cmp = new Component();
		cmp.setId(getComponentId(index));
		cmp.setVersion(version);
		cmp.setTitle(getComponentTitle(index));
		cmp.setDescription(getComponentDescription(index));
		cmp.setCreation_date(cmpCreationDate);
		cmp.setLast_update(cmpLastUpdate);
		cmp.setScreenshot(cmpScreenshot);
		cmp.setOwner(getComponentOwner(index));
		
		return cmp;
	}
	
	private String getComponentId(int index) {
		return "component-"+index;
	}
	
	private String getComponentTitle(int index) {
		return "test component "+index;
	}
	
	private String getComponentDescription(int index) {
		return "this is test component number "+index;
	}

	private String getUpdatedComponentDescription(int index) {
		return "component "+index+" has been updated";
	}
	
	private String getComponentOwner(int index) {
		return "agent nr. "+index;
	}
	
	private void checkComponent(Component cmp, int index, String version) {
		Assert.assertEquals(getComponentId(index), cmp.getId());
		Assert.assertEquals(version, cmp.getVersion());
		Assert.assertEquals(getComponentTitle(index), cmp.getTitle());
		Assert.assertEquals(getComponentDescription(index), cmp.getDescription());
		Assert.assertEquals(cmpCreationDate, cmp.getCreation_date());
		Assert.assertEquals(cmpLastUpdate, cmp.getLast_update());
		Assert.assertEquals(cmpScreenshot, cmp.getScreenshot());
		Assert.assertEquals(getComponentOwner(index), cmp.getOwner());
	}
	
	private void checkUpdatedComponent(Component cmp, int index, String version) {
		Assert.assertEquals(getComponentId(index), cmp.getId());
		Assert.assertEquals(version, cmp.getVersion());
		Assert.assertEquals(getComponentTitle(index), cmp.getTitle());
		Assert.assertEquals(getUpdatedComponentDescription(index), cmp.getDescription());
		Assert.assertEquals(cmpCreationDate, cmp.getCreation_date());
		Assert.assertEquals(cmpLastUpdate, cmp.getLast_update());
		Assert.assertEquals(cmpScreenshot, cmp.getScreenshot());
		Assert.assertEquals(getComponentOwner(index), cmp.getOwner());
	}
	
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
    	// save all components as version 1.0.0
    	for (int i = 0; i < cmpCount; i++) {
    		try {
    			Assert.assertTrue(entryManager.registerComponent(createComponent(i, "1.0.0")));
    		}
    		catch (EntryAlreadyExistsException ex) {
    			Assert.fail("version 1.0.0 of component number "+i+" already exists");
    		}
    	}
    	
    	// add version 2.0.0 for every second component
    	for (int i = 1; i < cmpCount; i+=2) {
    		try {
    			Assert.assertTrue(entryManager.registerComponent(createComponent(i, "2.0.0")));
    		}
    		catch (EntryAlreadyExistsException ex) {
    			Assert.fail("version 2.0.0 of component number "+i+" already exists");
    		}
    	}
    	
    	// get all components as version 1.0.0
    	for (int i = 0; i < cmpCount; i++) {
    		try {
    			Component cmp = entryManager.getComponent(getComponentId(i), "1.0.0");
    			
    			// check if all values are correct
    			checkComponent(cmp, i, "1.0.0");
    		}
    		catch (EntryNotFoundException ex) {
    			Assert.fail("version 1.0.0 of component number "+i+" not found");
			}
    	}
    	
    	// get version 2.0.0 for every second component
    	for (int i = 1; i < cmpCount; i+=2) {
    		try {
    			Component cmp = entryManager.getComponent(getComponentId(i), "2.0.0");
    			
    			// check if all values are correct
    			checkComponent(cmp, i, "2.0.0");
    		}
    		catch (EntryNotFoundException ex) {
    			Assert.fail("version 2.0.0 of component number "+i+" not found");
			}
    	}
    	
    	// check if every first component has no version 2.0.0
    	for (int i = 0; i < cmpCount; i+=2) {
    		try {
    			Component cmp = entryManager.getComponent(getComponentId(i), "2.0.0");
    			
    			// there must be NO version 2.0.0 for these components
    			Assert.assertNull(cmp);
    		}
    		catch (EntryNotFoundException ex) {
    			// this exception is expected here
			}
    	}
    	
    	// delete version 1.0.0 of every second component
    	/*for (int i = 1; i < cmpCount; i+=2) {
    		try {
    			Param param = new Param();
    			param.setUri(getComponentId(i));
    			param.setVersion("1.0.0");
    			
    			//Assert.assertTrue(entryManager.deleteComponent(param));
    		}
    		catch (EntryNotFoundException ex) {
    			Assert.fail("version 1.0.0 of component number "+i+" not found");
    		}
    	}*/
    	
    	// update version 1.0.0 of every first component
    	for (int i = 0; i < cmpCount; i+=2) {
    		try {
    			Component cmp = createComponent(i, "1.0.0");
    			cmp.setDescription(getUpdatedComponentDescription(i));
    			Assert.assertTrue(entryManager.updateComponent(cmp));
    		}
    		catch (EntryNotFoundException ex) {
    			Assert.fail("version 1.0.0 of component number "+i+" not found");
    		}
    	}
    	
    	// check if version 1.0.0 of every second component has been deleted
    	for (int i = 1; i < cmpCount; i+=2) {
    		try {
    			Component cmp = entryManager.getComponent(getComponentId(i), "1.0.0");
    			
    			// there must be NO version 1.0.0 for these components
    			Assert.assertNull(cmp);
    		}
    		catch (EntryNotFoundException ex) {
    			// this exception is expected here
			}
    	}
    	
    	// check if version 1.0.0 of every first component has been updated
    	for (int i = 0; i < cmpCount; i+=2) {
    		try {
    			Component cmp = entryManager.getComponent(getComponentId(i), "1.0.0");
    			
    			// check if all values are correct
    			checkUpdatedComponent(cmp, i, "1.0.0");
    		}
    		catch (EntryNotFoundException ex) {
    			Assert.fail("version 1.0.0 of component number "+i+" not found");
    		}
    	}
    }
	
	@After
	public void after() {
		// clear file after testing
		FilesystemManager.clearFile(entryManager.getTargetURL());
	}
}
