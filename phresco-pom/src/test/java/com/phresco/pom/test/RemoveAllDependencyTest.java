package com.phresco.pom.test;

import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Model.Dependencies;
import com.phresco.pom.util.PomProcessor;

public class RemoveAllDependencyTest {

	@Before
	public void prepare() {
		PomProcessorAddDependencyTest addTest = new PomProcessorAddDependencyTest();
		addTest.prepare();
	}

	@Test
	public void validRemoveAllDependency() {
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.removeAllDependencies();
			processor.save();
			Dependencies actual = processor.getModel().getDependencies();
			Dependencies expected = null;
			Assert.assertEquals(expected, actual);

		} catch (JAXBException e) {
			Assert.fail("Change Version Failed!");
		} catch (IOException e) {
			Assert.fail("Change Version Failed!");
		} catch (PhrescoPomException e) {
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
