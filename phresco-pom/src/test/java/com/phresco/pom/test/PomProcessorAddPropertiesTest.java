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

public class PomProcessorAddPropertiesTest {
	
	@Before
	public void prepare() throws IOException{
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void validAddProperties() throws ParserConfigurationException, PhrescoPomException{
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.setProperty("Photon", "Phresco");
			processor.save();
			int actual = processor.getModel().getProperties().getAny().size();
			int expected = 1;
			Assert.assertEquals(expected, actual);
		} catch (JAXBException e) {
			Assert.fail("Change Version Failed!");
		} catch (IOException e) {
			Assert.fail("Change Version Failed!");
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
