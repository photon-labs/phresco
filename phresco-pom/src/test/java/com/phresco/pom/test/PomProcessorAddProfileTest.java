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

public class PomProcessorAddProfileTest {

	@Before
	public void prepare() throws IOException{
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void addProfile() {
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.addProfile("123");
			processor.save();
			String actual = processor.getProfile("123").getId();
			String expected = "123";
			Assert.assertEquals(actual,expected);
		} catch (IOException e) {
			Assert.fail("Get Plugin Failed!");
		} catch (JAXBException e) {
		    Assert.fail("Get Plugin Failed!");
		} catch (PhrescoPomException e) {
			Assert.fail("Get Plugin Failed!");
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
