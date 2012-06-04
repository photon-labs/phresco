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

public class PomProcessorChangePluginVersionTest {

	@Before
	public void prepare() throws IOException{
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void validchangePluginVersion() {
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.addPlugin("photon", "phresco", "1.2.2");
			processor.addPlugin("phresco", "photon", "1.2");
			processor.changePluginVersion("phresco", "photon", "2.2.2");
			processor.save();
			String actual = processor.getModel().getBuild().getPlugins().getPlugin().get(1).getVersion();
			String expected = "2.2.2";
			Assert.assertEquals(actual,expected);
		} catch (IOException e) {
			Assert.fail("Get Plugin Failed!");
		} catch (JAXBException e) {
		    Assert.fail("Get Plugin Failed!");
		} catch (PhrescoPomException e) {
			Assert.fail("Get Plugin Failed!");
		}
	}
	
	@Test
	public void invalidchangePluginVersion() {
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.addPlugin("photon", "phresco", "1.2.2");
			processor.addPlugin("phresco", "photon", "1.2");
			processor.changePluginVersion("invalid", "photon", "2.2.2");
			processor.save();
		} catch (IOException e) {
			Assert.fail("Get Plugin Failed!");
		} catch (JAXBException e) {
		    Assert.fail("Get Plugin Failed!");
		} catch (PhrescoPomException e) {
			Assert.assertTrue(e.getErrorCode() == POMErrorCode.PLUGIN_NOT_FOUND);
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
