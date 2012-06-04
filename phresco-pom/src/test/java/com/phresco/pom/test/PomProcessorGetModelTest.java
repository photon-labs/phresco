package com.phresco.pom.test;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.util.PomProcessor;

public class PomProcessorGetModelTest {

	@Before
	public void prepare() throws IOException, JAXBException{
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}	
	}
	
	@Test
	public void validGetModel() {
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.getModel().setArtifactId("phresco");
			processor.getModel().setGroupId("photon");
			processor.getModel();
			processor.save();
			String actual = processor.getModel().getArtifactId();
			String expected = "phresco";
			Assert.assertEquals(actual,expected);
		} catch (IOException e) {
			Assert.fail("Get Plugin Failed!");
		} catch (JAXBException e) {
			Assert.fail("Get Plugin Failed!");
			e.printStackTrace();
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
