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

public class PomProcessorAddDependencyTest {

	@Before
	public void prepare() {
		
		try {
			File file = new File("pomTest.xml");
			if(file.exists()) {
				file.delete();
			}
			PomProcessor processor = new PomProcessor(file);
			processor.addDependency("com.suresh.marimuthu", "artifact" ,"2.3");
			processor.addDependency("com.suresh.marimuthu1", "artifact1" ,"2.3");
			processor.addDependency("com.suresh.marimuthu2", "artifact2" ,"2.3");
			processor.save();
		} catch (JAXBException e) {
		} catch (IOException e) {
		} catch (PhrescoPomException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validAddDependency() throws PhrescoPomException{
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.addDependency("com.photon.phresco", "artifact","4.1.1");
			processor.save();
			Assert.assertEquals(4, processor.getModel().getDependencies().getDependency().size());
		} catch (JAXBException e) {
			Assert.fail("Add Dependency Failed!");
		} catch (IOException e) {
			Assert.fail("Add Dependency Failed!");
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
