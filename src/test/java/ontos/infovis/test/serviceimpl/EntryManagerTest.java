package ontos.infovis.test.serviceimpl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import ontos.infovis.pojo.Component;
import ontos.infovis.pojo.ComponentDependency;
import ontos.infovis.pojo.ComponentInstance;
import ontos.infovis.pojo.ComponentResource;
import ontos.infovis.pojo.Composition;
import ontos.infovis.pojo.Right;
import ontos.infovis.service.db.FilesystemManager;
import ontos.infovis.serviceimpl.EntryException.EntryAlreadyExistsException;
import ontos.infovis.serviceimpl.EntryException.EntryNotFoundException;
import ontos.infovis.serviceimpl.EntryManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		org.apache.log4j.BasicConfigurator.configure();
		
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
    	for (int i = 1; i < cmpCount; i+=2) {
    		try {
    			String id = getComponentId(i);
    			String version = "1.0.0";
    			
    			Assert.assertTrue(entryManager.deleteComponent(id, version));
    		}
    		catch (EntryNotFoundException ex) {
    			Assert.fail("version 1.0.0 of component number "+i+" not found");
    		}
    	}
    	
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
    	
    	// add a resource and dependency to the first component
    	try {
			Component cmp = createComponent(0, "1.0.0");
			List<ComponentResource> resources = new ArrayList<ComponentResource>();
			ComponentResource cmpRes = new ComponentResource();
			cmpRes.setType("testType");
			cmpRes.setPath("testPath");
			resources.add(cmpRes);
			
			List<ComponentDependency> dependencies = new ArrayList<ComponentDependency>();
			ComponentDependency cmpDep = new ComponentDependency();
			cmpDep.setName("testName");
			cmpDep.setPath("testPath");
			dependencies.add(cmpDep);
			
			cmp.setResources(resources);
			cmp.setDependencies(dependencies);
			
			Assert.assertTrue(entryManager.updateComponent(cmp));
    	}
    	catch (EntryNotFoundException ex) {
    		Assert.fail("version 1.0.0 of component number 0 not found");
    	}
    	
    	// get the added resource and dependency
		try {
			Component cmp = entryManager.getComponent(getComponentId(0), "1.0.0");
			
			// check if all values are correct
			Assert.assertEquals("testType", cmp.getResources().get(0).getType());
			Assert.assertEquals("testPath", cmp.getResources().get(0).getPath());
			Assert.assertEquals("testName", cmp.getDependencies().get(0).getName());
			Assert.assertEquals("testPath", cmp.getDependencies().get(0).getPath());
		}
		catch (EntryNotFoundException ex) {
			Assert.fail("version 1.0.0 of component number 0 not found");
		}
		
		// create a composition with some components
		try {
			Composition cmp = new Composition();
			cmp.setId("testId");
			cmp.setTitle("testTitle");
			cmp.setDescription("testDescription");
			cmp.setVersion("3.0.0");
			cmp.setOwner("testOwner");
			cmp.setCreation_date(cmpCreationDate);
			cmp.setLast_update(cmpLastUpdate);
			cmp.setStructure("testStructure");
			
			List<Right> rights = new ArrayList<Right>();
			Right r = new Right();
			r.setRight("testRight");
			r.setUser("testUser");
			rights.add(r);
			cmp.setRights(rights);
			
			List<ComponentInstance> components = new ArrayList<ComponentInstance>();
			ComponentInstance cmpInst = new ComponentInstance();
			cmpInst.setId("testID");
			cmpInst.setInstance_id("testInstId");
			cmpInst.setVersion("1.0.0");
			components.add(cmpInst);
			cmp.setComponents(components);
			
			Assert.assertTrue(entryManager.registerComposition(cmp));
		}
		catch (EntryAlreadyExistsException ex) {
			Assert.fail("version 3.0.0 of the compostion already exists");
		}
		
    	// get the added composition
		try {
			Composition cmp = entryManager.getComposition("testId", "3.0.0");
			
			// check if all values are correct			
			Assert.assertEquals("testId", cmp.getId());
			Assert.assertEquals("testTitle", cmp.getTitle());
			Assert.assertEquals("testDescription", cmp.getDescription());
			Assert.assertEquals("3.0.0", cmp.getVersion());
			Assert.assertEquals("testOwner", cmp.getOwner());
			Assert.assertEquals(cmpCreationDate, cmp.getCreation_date());
			Assert.assertEquals(cmpLastUpdate, cmp.getLast_update());
			Assert.assertEquals("testStructure", cmp.getStructure());
		}
		catch (EntryNotFoundException ex) {
			Assert.fail("version 3.0.0 of the compistion not found");
		}
    }
	
	@After
	public void after() {
		// clear file after testing
		FilesystemManager.clearFile(entryManager.getTargetURL());
	}
}
