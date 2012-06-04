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


public class PomProcessorDeleteDependencyTest {
	
	@Before
	public void prepare() throws PhrescoPomException{
		PomProcessorAddDependencyTest addTest = new PomProcessorAddDependencyTest();
		addTest.prepare();
	}
	
	@Test
	public void validDelete(){
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.deleteDependency("com.suresh.marimuthu", "artifact");
			processor.save();
			Assert.assertEquals(2, processor.getModel().getDependencies().getDependency().size());
		} catch (JAXBException e) {
			Assert.fail("Delete Failed!");
		} catch (IOException e) {
			Assert.fail("Delete Failed!");
		} catch (PhrescoPomException e) {
			Assert.fail("Delete failed!");
		}
	}
	
	@Test
	public void invalidDelete(){
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.deleteDependency("invalid", "invalid");
			processor.save();
		} catch (JAXBException e) {
			Assert.fail("Delete failed!");
		} catch (IOException e) {
			Assert.fail("Delete failed!");
		} catch (PhrescoPomException e) {
			Assert.assertTrue(e.getErrorCode()== POMErrorCode.DEPENDENCY_NOT_FOUND);
		}
	}
	
	@After
	public void delete(){
		File file = new File("pomdeletedep.xml");
		if(file.exists()) {
			file.delete();
		}
	}
}
