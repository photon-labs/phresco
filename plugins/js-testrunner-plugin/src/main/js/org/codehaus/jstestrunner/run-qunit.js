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

/*global console, phantom, WebPage */

/**
 * Process the test.
 */
function processTestAndLoadNext(testUrls) {
	var testPage, testUrl, loadingTime, loadingTimeout;
	
	// loadingTimeout is how long (in MS) we wait for testPage.evaluate
	// to succesfully evaluate the QUnit HTML. If this time is exceeded,
	// then we proceed to the next test or end processing if no more tests
	// exist.
	loadingTime = new Date().getTime();
	// This should be less than the timeout declared in
	// JSTestResultServer.java (which is currently 30s)
	loadingTimeout = 20000;
	
	testUrl = testUrls[0];
	testUrls.splice(0, 1);

	testPage = new WebPage();
	
	testPage.onConsoleMessage = function(msg) {
		console.log(msg);
	};

	testPage.open(testUrl, function(status) {
		var testResultChecker;
		
		if (status === "success"){
			testResultChecker = setInterval(function() {
				var testResultsProcessed;
				
				testResultsProcessed = testPage.evaluate(function() {
					var testResultsProcessed, testResults, testResultElem;
			
					/**
					 * Notify the notifier using an HTTP POST. We send it asynchronously and don't
					 * bother about a response. If tests fail at the consuming end then this should
					 * an exception rather than the rule. Failing tests are better than false
					 * positives.
					 * 
					 * @param testUrl
					 *            the url of the test document the test relates to.
					 * @param moduleName
					 *            the module. Can be null.
					 * @param testName
					 *            the name of the test.
					 * @param failed
					 *            the number of assertions failing.
					 * @param passed
					 *            the number of assertions passing.
					 */
					function notify(testUrl, testResults) {

						var failures, i, j, message, messages, passes, testResult, xhr;

						failures = 0;
						passes = 0;
						messages = "";

						for (j = 0; j < testResults.length; ++j) {
							testResult = testResults[j];
							if (testResult.moduleName !== null) {
								message = "[" + testResult.moduleName + "] ";
							} else {
								message = "";
							}
							message += testResult.testName + ": failed: " + testResult.failed
									+ " passed: " + testResult.passed;
							for (i = 0; i < testResult.details.length; ++i) {
								message += "\n  " + testResult.details[i].message;
								if (testResult.details[i].expected !== undefined) {
									message += ", expected: " + testResult.details[i].expected;
								}
								if (testResult.details[i].actual !== undefined) {
									message += ", actual: " + testResult.details[i].actual;
								}
								if (testResult.details[i].diff !== undefined) {
									message += ", diff: " + testResult.details[i].diff;
								}
								if (testResult.details[i].sourceLine !== undefined) {
									message += ", source: " + testResult.details[i].sourceLine;
								}
							}

							messages += message + "\n";

							passes += testResult.passed;
							failures += testResult.failed;
						}

						xhr = new XMLHttpRequest();
						xhr.open("POST", "/testResults", false);
						xhr.setRequestHeader("Content-Type", "application/json");
						try {
							xhr.send(JSON.stringify({
								testUrl : testUrl,
								passes : passes,
								failures : failures,
								message : messages
							}));
						} catch (e) {
							// Just swallow exceptions as we can't do anything useful if there are
							// comms errors.
						}
					}

					/**
					 * Parse the document for test results.
					 */
					function getTestResults() {
						var details, failed, i, j, nodeList, message, messageElem, moduleName, passed, testsElem, testItemElem, testItemElems, testName, testResults;

						function getFailedTestText(node, className) {
							var elem;
							elem = node.getElementsByClassName(className);
							return (elem.length > 0? 
								elem[0].getElementsByTagName("pre")[0].innerText :
								undefined);
						}

						testResults = [];

						testsElem = document.getElementById("qunit-tests");
						if (testsElem !== null) {

							testItemElems = testsElem.getElementsByTagName("li");

							// For each test, collect the test results
							for (i = 0; i < testItemElems.length; ++i) {
								// Extract the microformatted data.
								testItemElem = testItemElems[i];

								// Not interested in the detailed messages.
								if (testItemElem.parentNode === testsElem) {
									nodeList = testItemElem.getElementsByClassName("module-name");
									if (nodeList.length === 1) {
										moduleName = nodeList[0].innerText;
									} else {
										moduleName = null;
									}
									nodeList = testItemElem.getElementsByClassName("test-name");
									if (nodeList.length === 1) {
										testName = nodeList[0].innerText;
									} else {
										testName = null;
									}
									nodeList = testItemElem.getElementsByClassName("failed");
									if (nodeList.length === 1) {
										failed = parseInt(nodeList[0].innerText, 10);
									} else {
										failed = null;
									}
									nodeList = testItemElem.getElementsByClassName("passed");
									if (nodeList.length === 1) {
										passed = parseInt(nodeList[0].innerText, 10);
									} else {
										passed = null;
									}
									nodeList = testItemElem.getElementsByTagName("li");
									details = [];
									for (j = 0; j < nodeList.length; ++j) {
										messageElem = nodeList[j].getElementsByClassName("test-message");
										if (messageElem.length > 0) {
											message = messageElem[0].innerText;
										} else {
											message = nodeList[j].innerText;
										}
										if (nodeList[j].className === "fail") {
											details.push({
												message : message,
												actual : getFailedTestText(nodeList[j], "test-actual"),
												diff : getFailedTestText(nodeList[j], "test-diff"),
												expected : getFailedTestText(nodeList[j], "test-expected"),
												sourceLine : getFailedTestText(nodeList[j], "test-source")
											});
										} else {
											details.push({
												message : message
											});
										}
									}
									
									testResults.push({
										moduleName : moduleName,
										testName : testName,
										failed : failed,
										passed : passed,
										details : details
									});
								}
							}

						} else {
							console.log("Cannot find #qunit-tests element. Skipping test results.");
							
						}
						
						return testResults;
					}

					testResultElem = document.getElementById("qunit-testresult");

					if (testResultElem && testResultElem.innerText.match("completed")) {

						// Tests are complete. Drill down and extract all the test
						// results from this one file.
			
						testResults = getTestResults();
						
						// Pass the data on for all tests per given test url.

						notify(document.location.href, testResults);

						// Signal that we're done processing the test results.
						
						testResultsProcessed = true;
						
					} else {
						testResultsProcessed = false;
					}
					
					return testResultsProcessed;
				});
			
				/**
				 * Continue with the next test, or exit if all tests are complete.
				 */
				function proceedWithTests() {
					// Discontinue our checking for test results as we now have them.
					
					clearInterval(testResultChecker);
					
					// Run the next test or exit if no more.

					if (testUrls.length === 0) {
						phantom.exit();
					} else {					
						processTestAndLoadNext(testUrls);
					}
				}
				
				if (testResultsProcessed) {
					proceedWithTests();
					
				} else {
					
					// If we're reached the timeout waiting for this test:
					if (new Date().getTime() >= loadingTime + loadingTimeout) {
						console.log("Unable to process test results, timed out");
						
						proceedWithTests();
					}
				}
				
			}, 100);

		} else {
			phantom.exit();
		}
	
	});
}

/**
 * Main control flow for Phantom.
 */
function main() {

	var testUrls;

	if (phantom.args.length === 1) {

		testUrls = phantom.args[0].split(',');

		if (testUrls.length > 0) {
			processTestAndLoadNext(testUrls);
		}

	} else {
		console.log("Usage: run-qunit.js URL[,URL]*");
		phantom.exit();

	}

}

main();