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

import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.codehaus.jstestrunner.TestResultProducer;
import org.codehaus.jstestrunner.jetty.JSTestResultHandler.JSTestResult;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;

/**
 * A server responsible for obtaining results from an execution.
 */
public class JSTestResultServer extends Object {

	/**
	 * The Jetty server instance.
	 */
	private Server webServer;

	/**
	 * The handler of test results (POST requests).
	 */
	private JSTestResultHandler jsTestResultHandler;

	private Integer port;

	private String contextPath;

	private String[] resourceBases;

	/**
	 * Set when the web server has been initialised.
	 */
	private boolean initedWebServer = false;

	/**
	 * Get a result for a given URL.
	 * 
	 * @param url
	 *            the url to obtain the result for.
	 * @param testResultProducer
	 *            Used to determine whether we are in a position to wait for
	 *            results.
	 * @return the test result or null if one cannot be obtained.
	 */
	public JSTestResult getJsTestResult(URL url,
			TestResultProducer testResultProducer) {
		return jsTestResultHandler.getJsTestResult(url, testResultProducer, 30,
				TimeUnit.SECONDS);
	}

	/**
	 * Initialise the web server.
	 */
	private void initWebServer() {

		webServer = new Server();

		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(port);
		webServer.addConnector(connector);

		Handler[] handlers = new Handler[resourceBases.length + 1];
		int i = 0;
		for (String resourceBase : resourceBases) {
			ResourceHandler resourceHandler = new ResourceHandler();
			resourceHandler.setResourceBase(resourceBase);
			handlers[i++] = resourceHandler;
		}
		jsTestResultHandler = new JSTestResultHandler();
		handlers[i] = jsTestResultHandler;

		HandlerList handlerList = new HandlerList();
		handlerList.setHandlers(handlers);

		ContextHandler contextHandler = new ContextHandler();
		contextHandler.setContextPath(contextPath);
		contextHandler.setHandler(handlerList);

		webServer.setHandler(contextHandler);

	}

	@Resource
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	@Resource
	public void setPort(Integer port) {
		this.port = port;
	}

	@Resource
	public void setResourceBases(String[] resourceBases) {
		this.resourceBases = resourceBases;
	}

	/**
	 * Start the server.
	 * 
	 * @throws Exception
	 *             if something goes wrong.
	 */
	public void start() throws Exception {
		if (!initedWebServer) {
			initWebServer();
			initedWebServer = true;
		}
		webServer.start();
	}

	/**
	 * Stop the server.
	 * 
	 * @throws Exception
	 *             if something goes wrong.
	 */
	public void stop() throws Exception {
		if (initedWebServer) {
			webServer.stop();
		}
	}

}
