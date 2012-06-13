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

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jstestrunner.TestResultProducer;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * Receive test results and store them in an internal cache.
 */
public class JSTestResultHandler extends AbstractHandler {

	/**
	 * Captures a test result.
	 */
	public static class JSTestResult {
		public int failures;
		public int passes;
		public String message;
	}

	/**
	 * Store a mapping of test results.
	 */
	private final Map<String, JSTestResult> jsTestResults = new HashMap<String, JSTestResult>();
	private final Lock jsTestResultsLock = new ReentrantLock();
	private final Condition newJsTestResults = jsTestResultsLock.newCondition();

	/**
	 * Get the test result associated with the given url and block until it
	 * becomes available, or we timeout.
	 * 
	 * @param url
	 *            the url of the test.
	 * @param testResultProducer
	 *            Used to determine whether we are in a position to wait for
	 *            results.
	 * @param time
	 *            the time to wait.
	 * @param unit
	 *            the unit of time to wait.
	 * @return the test result or null if it cannot be obtained.
	 */
	public JSTestResult getJsTestResult(URL url,
			TestResultProducer testResultProducer, long time, TimeUnit unit) {
		JSTestResult jsTestResult = null;
		jsTestResultsLock.lock();
		try {
			do {
				jsTestResult = jsTestResults.get(url.toString());
				if (jsTestResult == null) {
					boolean newJsTestResult;
					try {
						if (testResultProducer.isAvailable()) {
							newJsTestResult = newJsTestResults
									.await(time, unit);
						} else {
							newJsTestResult = false;
						}
					} catch (InterruptedException e) {
						newJsTestResult = false;
					}
					if (!newJsTestResult) {
						break;
					}
				}
			} while (jsTestResult == null);
		} finally {
			jsTestResultsLock.unlock();
		}
		return jsTestResult;
	}

	/**
	 * Handle a test result.
	 */
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (target.equals("/testResults") && request.getMethod().equals("POST")
				&& request.getContentType().contains("application/json")) {

			JSTestResult jsTestResult = null;

			JsonFactory f = new JsonFactory();
			JsonParser jp = f.createJsonParser(request.getReader());

			String testUrl = null;
			Integer failures = null;
			Integer passes = null;
			String message = null;

			try {
				jp.nextToken(); // will return JsonToken.START_OBJECT (verify?)
				while (jp.nextToken() != JsonToken.END_OBJECT) {
					String fieldname = jp.getCurrentName();
					jp.nextToken(); // move to value, or
									// START_OBJECT/START_ARRAY
					if (fieldname.equals("testUrl")) { // contains an object
						testUrl = jp.getText();
					} else if (fieldname.equals("failures")) {
						failures = jp.getIntValue();
					} else if (fieldname.equals("passes")) {
						passes = jp.getIntValue();
					} else if (fieldname.equals("message")) {
						message = jp.getText();
					}
				}
			} finally {
				jp.close();
			}

			// Store any failures.
			if (testUrl != null && failures != null && passes != null
					&& message != null) {

				jsTestResult = new JSTestResult();
				jsTestResult.failures = failures;
				jsTestResult.message = message;
				jsTestResult.passes = passes;

				response.setStatus(HttpServletResponse.SC_NO_CONTENT);

			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}

			response.setContentType("application/json");

			baseRequest.setHandled(true);

			// Minimally signal any threads to re-look at things. Store results
			// if we received them.
			jsTestResultsLock.lock();
			try {
				if (jsTestResult != null) {
					jsTestResults.put(testUrl, jsTestResult);
				}
				newJsTestResults.signalAll();
			} finally {
				jsTestResultsLock.unlock();
			}

		}
	}
}
