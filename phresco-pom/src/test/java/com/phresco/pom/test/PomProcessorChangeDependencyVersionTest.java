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

public class PomProcessorChangeDependencyVersionTest {

	@Before
	public void prepare() throws PhrescoPomException {
		PomProcessorAddDependencyTest addTest = new PomProcessorAddDependencyTest();
		addTest.prepare();
	}

	@Test
	public void validChangeDependencyVersion() {
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.changeDependencyVersion("com.suresh.marimuthu", "artifact","2.2.2");
			processor.save();
			String actual = processor.getModel().getDependencies().getDependency().get(0).getVersion();
			String expected = "2.2.2";
			Assert.assertEquals(expected, actual);

		} catch (JAXBException e) {
			Assert.fail("Change Version Failed!");
		} catch (IOException e) {
			Assert.fail("Change Version Failed!");
		} catch (PhrescoPomException e) {
			Assert.fail("Change Version Failed!");
		}
	}

	@Test
	public void invalidChangeDependencyVersion() {
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.changeDependencyVersion("com.photon.invalid", "artifact","2.2");
			processor.save();
			Assert.assertTrue("Invalid Dependency values", true);
		} catch (JAXBException e) {
			Assert.fail("Change Version Failed!");
		} catch (IOException e) {
			Assert.fail("Change Version Failed!");
		} catch (PhrescoPomException e) {
			
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
