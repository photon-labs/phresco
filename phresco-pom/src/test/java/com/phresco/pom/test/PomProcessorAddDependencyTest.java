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
