package com.phresco.pom.test;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.POMErrorCode;
import com.phresco.pom.util.PomProcessor;

public class PomProcessorGetPluginTest {

	@Before
	public void prepare() throws IOException{
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void validGetPlugin() {
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.addPlugin("phresco", "photon","2.2");
			processor.addPlugin("Suresh", "marimuthu","1.1");
			processor.getPlugin("phresco", "photon");
			processor.save();
			String actual = processor.getModel().getBuild().getPlugins().getPlugin().get(0).getArtifactId();
			String expected = "photon";
			Assert.assertEquals(actual,expected);
		} catch (IOException e) {
			Assert.fail("Get Plugin Failed!");
		} catch (JAXBException e) {
			Assert.fail("Get Plugin Failed!");
			e.printStackTrace();
		} catch (PhrescoPomException e) {
			Assert.fail("Get Plugin Failed!");
			e.printStackTrace();
		}
	}
	
	@Test
	public void invalidGetPlugin(){
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.addPlugin("Suresh", "marimuthu","1.1");
			processor.addPlugin("phresco", "photon","2.2");
			processor.getPlugin("phres", "phot");
			Assert.assertTrue(true);
		} catch (IOException e) {
			Assert.fail("Get Plugin Failed!");
		} catch (JAXBException e) {
			Assert.fail("Get Plugin Failed!");
		} catch (PhrescoPomException e) {
			Assert.assertTrue(e.getErrorCode()==POMErrorCode.PLUGIN_NOT_FOUND);
		}
	}
	
	@After
	public void delete(){
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
}
