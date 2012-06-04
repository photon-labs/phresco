/*
 * ###
 * phresco-pom
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
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
