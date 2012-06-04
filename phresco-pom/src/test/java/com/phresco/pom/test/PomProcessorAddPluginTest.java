package com.phresco.pom.test;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class PomProcessorAddPluginTest {
	
	@Before
	public void prepare() throws IOException {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void validAddPlugin() throws ParserConfigurationException, PhrescoPomException {
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.addPlugin("phresco", "photon","2.2");
			processor.addPlugin("Suresh", "marimuthu","1.1");
			processor.save();
			Assert.assertEquals(2, processor.getModel().getBuild().getPlugins().getPlugin().size());
		} catch (JAXBException e) {
			Assert.fail("Add Plugin Failed!");
		} catch (IOException e) {
			Assert.fail("Add Plugin Failed!");
		}
	}
	
	@After
	public void delete() {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}

}
