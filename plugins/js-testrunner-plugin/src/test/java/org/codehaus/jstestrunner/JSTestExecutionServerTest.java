/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

package org.codehaus.jstestrunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class JSTestExecutionServerTest {

	private JSTestExecutionServer server;

	/**
	 * Perform the minimum setup.
	 * 
	 * @throws IOException
	 *             if something goes wrong.
	 */
	@Before
	public void setup() throws IOException {
		server = new JSTestExecutionServer();
		server.setTestRunnerFilePath("target" + File.separator
				+ "js-testrunner");
		server.copyTestRunnerFileIfNotExists();
	}

	/**
	 * Test that command args are formatted properly using double quotes.
	 * 
	 * @throws IOException
	 *             if something goes wrong.
	 */
	@Test
	public void testGetCommandArgsWithDblQuotes() throws IOException {
		server.setCommandPattern("/Applications/phantomjs.app/Contents/MacOS/phantomjs \"%1$s'\" %2$s");
		List<URL> urls = new ArrayList<URL>();
		urls.add(new URL("http:/a.html"));
		urls.add(new URL("http:/b.html"));
		server.setUrls(urls);
		String[] args = server.getCommandArgs();
		assertEquals(3, args.length);
		assertEquals("/Applications/phantomjs.app/Contents/MacOS/phantomjs",
				args[0]);
		assertTrue(args[1].endsWith(server.getTestRunnerFilePath()
				+ File.separator + "run-qunit.js'"));
		assertEquals("http:/a.html,http:/b.html", args[2]);
	}

	/**
	 * Test that command args are formatted properly using single quotes.
	 * 
	 * @throws IOException
	 *             if something goes wrong.
	 */
	@Test
	public void testGetCommandArgsWithSingleQuotes() throws IOException {
		server.setCommandPattern("/Applications/phantomjs.app/Contents/MacOS/phantomjs '%1$s\"' %2$s");
		List<URL> urls = new ArrayList<URL>();
		server.setUrls(urls);
		String[] args = server.getCommandArgs();
		assertEquals(3, args.length);
		assertTrue(args[1].endsWith(server.getTestRunnerFilePath()
				+ File.separator + "run-qunit.js\""));
	}
}
