package com.phresco.pom.test;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class PomProcessorAddModulesTest {

	@Before
	public void prepare() throws IOException {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void validAddModules() throws ArrayIndexOutOfBoundsException, PhrescoPomException{
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.addModule("Phresco");
			processor.addModule("Photon");
			processor.save();
			Assert.assertEquals(2, processor.getModel().getModules().getModule().size());
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
