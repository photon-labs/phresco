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

package org.codehaus.jstestrunner.jetty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jstestrunner.TestResultProducer;
import org.codehaus.jstestrunner.jetty.JSTestResultHandler.JSTestResult;
import org.eclipse.jetty.server.Request;
import org.junit.Test;

public class JSTestResultHandlerTest {

	private final TestResultProducer testResultProducer = new TestResultProducer() {
		public boolean isAvailable() {
			return true;
		}
	};

	/**
	 * Test handling of an irregular message i.e. with no "failure" property in
	 * the result data.
	 * 
	 * @throws ServletException
	 *             if something goes wrong.
	 * @throws IOException
	 *             if something goes wrong.
	 */
	@Test
	public void testHandleIrregular() throws IOException, ServletException {

		// Setup the test.
		JSTestResultHandler handler = new JSTestResultHandler();

		String target = "/testResults";

		Request baseRequest = mock(Request.class);

		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod()).thenReturn("POST");
		when(request.getContentType()).thenReturn("application/json");
		String data = "{\"testUrl\":\"http:/a.html\",\"passes\":1,\"message\":\"some message\"}";
		when(request.getReader()).thenReturn(
				new BufferedReader(new StringReader(data)));

		HttpServletResponse response = mock(HttpServletResponse.class);

		// Test the handler.
		handler.handle(target, baseRequest, request, response);

		// Verify that things went to plan.
		verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
		verify(response).setContentType("application/json");

		verify(baseRequest).setHandled(true);

		// Now verify that the test result is what we're expecting.
		JSTestResult result = handler.getJsTestResult(new URL("http:/a.html"),
				testResultProducer, 0L, TimeUnit.MICROSECONDS);
		assertNull(result);
	}

	/**
	 * Test handling of a message that should not be processed.
	 * 
	 * @throws ServletException
	 *             if something goes wrong.
	 * @throws IOException
	 *             if something goes wrong.
	 */
	@Test
	public void testHandleNothing() throws IOException, ServletException {

		// Setup the test.
		JSTestResultHandler handler = new JSTestResultHandler();

		String target = "/someotherurl";

		Request baseRequest = mock(Request.class);

		HttpServletRequest request = mock(HttpServletRequest.class);

		HttpServletResponse response = mock(HttpServletResponse.class);

		// Test the handler.
		handler.handle(target, baseRequest, request, response);

		// Verify that things went to plan.
		verify(baseRequest, never()).setHandled(true);

		// Now verify that the test result is what we're expecting.
		JSTestResult result = handler.getJsTestResult(new URL("http:/a.html"),
				testResultProducer, 0L, TimeUnit.MICROSECONDS);
		assertNull(result);
	}

	/**
	 * Test successful handling of a regular message.
	 * 
	 * @throws ServletException
	 *             if something goes wrong.
	 * @throws IOException
	 *             if something goes wrong.
	 */
	@Test
	public void testHandleSuccess() throws IOException, ServletException {

		// Setup the test.
		JSTestResultHandler handler = new JSTestResultHandler();

		String target = "/testResults";

		Request baseRequest = mock(Request.class);

		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod()).thenReturn("POST");
		when(request.getContentType()).thenReturn("application/json");
		String data = "{\"testUrl\":\"http:/a.html\",\"failures\":0,\"passes\":1,\"message\":\"some message\"}";
		when(request.getReader()).thenReturn(
				new BufferedReader(new StringReader(data)));

		HttpServletResponse response = mock(HttpServletResponse.class);

		// Test the handler.
		handler.handle(target, baseRequest, request, response);

		// Verify that things went to plan.
		verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
		verify(response).setContentType("application/json");

		verify(baseRequest).setHandled(true);

		// Now verify that the test result is what we're expecting.
		JSTestResult result = handler.getJsTestResult(new URL("http:/a.html"),
				testResultProducer, 30L, TimeUnit.SECONDS);
		assertEquals(0, result.failures);
		assertEquals("some message", result.message);
		assertEquals(1, result.passes);
	}

	/**
	 * Test the timeout handling of a regular message.
	 * 
	 * @throws ServletException
	 *             if something goes wrong.
	 * @throws IOException
	 *             if something goes wrong.
	 */
	@Test
	public void testHandleTimeout() throws IOException, ServletException {

		// Setup the test.
		JSTestResultHandler handler = new JSTestResultHandler();

		// Now verify that the test result is what we're expecting.
		JSTestResult result = handler.getJsTestResult(new URL("http:/a.html"),
				testResultProducer, 1L, TimeUnit.SECONDS);
		assertNull(result);
	}
}
