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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.Test;

public class JSTestSuiteRunnerServiceTest {

	@Test
	public void testGetFormattedPath() throws MalformedURLException {
		assertEquals("a.html",
				JSTestSuiteRunnerService.getFormattedPath(new URL(
						"http:/a.html")));
	}

	@Test
	public void testScanTestFiles() {
		String host = "localhost";
		int port = 9080;
		String[] resourceBases = new String[] { "target" + File.separator
				+ "test-classes" };
		String[] includes = new String[] { "**/QUnitTest.html" };
		String[] excludes = new String[0];
		List<URL> urls = JSTestSuiteRunnerService.scanTestFiles(host, port,
				resourceBases, includes, excludes);
		assertEquals(1, urls.size());
		assertEquals("/QUnitTest.html", urls.get(0).getPath());
	}
}
