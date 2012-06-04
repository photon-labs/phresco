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

public class PomProcessorRemoveModuleTest {

	@Before
	public void prepare() throws IOException, JAXBException {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}		
	}

	@Test
	public void validRemoveModule() {
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.addModule("photon");
			processor.addModule("phresco");
			processor.removeModule("phresco");
			processor.save();
			int actual = processor.getModel().getModules().getModule().size(); 
			int expected = 1;
			Assert.assertEquals(actual,expected);
		} catch (JAXBException e) {
			Assert.fail("Remove Module Failed!");
		} catch (IOException e) {
			Assert.fail("Remove Module Failed!");
		} catch(PhrescoPomException ph){
			Assert.fail("Remove Module Failed!");
		}
	}
	
	@Test
	public void invalidRemoveModule() {
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.addModule("photon");
			processor.addModule("phresco");
			processor.removeModule("DLF");
			processor.save();
		} catch (JAXBException e) {
			Assert.fail("Remove Module Failed!");
		} catch (IOException e) {
			Assert.fail("Remove Module Failed!");
		} catch(PhrescoPomException ph){
			Assert.assertTrue(ph.getErrorCode() == POMErrorCode.MODULE_NOT_FOUND);
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
