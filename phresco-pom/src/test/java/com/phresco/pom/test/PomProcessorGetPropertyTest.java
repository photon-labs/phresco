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
import com.phresco.pom.util.POMErrorCode;
import com.phresco.pom.util.PomProcessor;

public class PomProcessorGetPropertyTest {

	@Before
	public void prepare() throws IOException{
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void validGetProperty() {
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.setProperty("photon", "phresco");
			processor.setProperty("phresco", "photon");
			processor.save();
			String actual = processor.getProperty("phresco");
			String expected = "photon";
			Assert.assertEquals(actual,expected);
		} catch (IOException e) {
			Assert.fail("Get Plugin Failed!");
		} catch (JAXBException e) {
		    Assert.fail("Get Plugin Failed!");
		} catch (PhrescoPomException e) {
			Assert.fail("Get Plugin Failed!");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void invalidGetProperty() {
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.setProperty("photon", "phresco");
			processor.setProperty("phresco", "photon");
			processor.getProperty("phre");
			processor.save();
		} catch (IOException e) {
			Assert.fail("Get Plugin Failed!");
		} catch (JAXBException e) {
		    Assert.fail("Get Plugin Failed!");
		} catch (ParserConfigurationException e) {
			Assert.fail("Get Plugin Failed!");
			e.printStackTrace();
		} catch (PhrescoPomException e) {
			Assert.assertTrue(e.getErrorCode() == POMErrorCode.PROPERTY_NOT_FOUND);
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
