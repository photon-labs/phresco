/*global $, QUnit, phantom */

/**
 * Manual test file for run-qunit.js
 * 
 * The HTML file for this test (run-qunitTest.html) should be run manually
 * to test run-qunit.js. We do this because running phantomjs from CI is
 * not currently possible.
 * 
 * You must use a WebKit based browser for these tests; phantomjs is webkit
 * based and there are some quirks with other browser engine's handling which
 * means that you'll see test failures in other browsers.
 *  Note that you cannot use Firefox to run this test because firefox doesn't
 * support the innerText property, which run-qunit.js uses. Most other
 * browsers support this property, including phantomjs which is what actually
 * runs run-qunit.js, so updating run-qunit.js to use textContent/innerText
 * would add complexity for little benefit.
 * 	You can't use Internet Explorer because it doesn't implement
 * XMLHttpRequest as a true JavaScript object so we can't override the
 * prototype methods which we want to mock.
 * 
 * The test coverage is not ideal; the sandboxed code inside WebPage.evaluate
 * in run-qunit.js was not possible to test directly so I'm testing it where
 * possible by using its interactions with globals or non-sandboxed parts of
 * run-qunit.js.
 * 
 * Note also the tests are tightly coupled with run-qunit.js, we wait for a
 * little longer than the timeout defined in that file in tests which expect
 * a timeout, for example.
 * 
 * @author Ben Jones
 */

// QUnit convenience definitions
var module = QUnit.module;
var equals = QUnit.equals;
var expect = QUnit.expect;
var ok = QUnit.ok;
var test = QUnit.test;

// Mock some phantom variables globally so when run-qunit.js is
// included we don't get misleading javascript errors from its 
// initial run.
phantom = {};
phantom.args = [];
phantom.exit = function() {};

/**
 * This test suite must be run from a WebKit based browser. This
 * test was created to give a quick indication of the problem if
 * it's run in a non-webkit environment.
 */
test("WebKit browser required", function() {
	ok($.browser.webkit, "Browser must be WebKit based");
});

/**
 * If 0 arguments are passed, phantom exits immediately
 */
asyncTest("Phantom argument parsing, >0 arguments expected", function() {
	var actualProcessTestAndLoadNext;
	
	// Setup mocked environment
	actualProcessTestAndLoadNext = processTestAndLoadNext;
	
	processTestAndLoadNext = function() {
		ok(false, "processTestAndLoadNext should not be called");
	};
	phantom = {};
	phantom.args = [];
	phantom.exit = function() {
		ok(true, "phantom.exit called");
	};
	
	// Run main run-qunit.js function
	main();

	// Wait for processing to complete
	setTimeout(function(){
		// Expect processTestAndLoadNext is called
		expect(1);
		start();
		
		// Restore mocked environment
		processTestAndLoadNext = actualProcessTestAndLoadNext;
		phantom = {};
	}, 500);	
});

/**
 * WebPage.open() called on 1 argument
 */
asyncTest("Phantom argument parsing, Webpage.open() called on 1 argument", function() {
	// Setup mocked environment
	phantom = {};
	phantom.exit = function() {
		ok(true, "phantom.exit called");
	};
	
	webPageData = {
		opened: []
	};
	WebPage = function(){};
	// Store all opened pages and run callback with success
	WebPage.prototype.open = function(URL, callback) {
		ok(true, "WebPage.open called");
		webPageData.opened.push(URL);
		callback("success");
	};
	// Always evaluate to true
	WebPage.prototype.evaluate = function() {
		ok(true, "WebPage.evaluate called");
		return true;
	};
	
	// Set one argument and call main function
	phantom.args = ["a"];
	main();	
	
	// Wait for processing to complete
	setTimeout(function(){
		// Expect one argument to be opened
		equals(webPageData.opened.length, 1, "One argument opened");
		equals(webPageData.opened[0], "a", "First argument opened");
		expect(5);
		start();
		
		// Restore mocked environment
		phantom = {};
		WebPage = undefined;
	}, 500);
});

/**
 * WebPage.open() called on >1 arguments
 */
asyncTest("Phantom argument parsing, Webpage.open() called on >1 arguments", function() {
	// Setup mocked environment
	phantom = {};
	phantom.exit = function() {
		ok(true, "phantom.exit called");
	};
	
	webPageData = {
		opened: []
	};
	WebPage = function(){};
	// Store all opened pages and run callback with success
	WebPage.prototype.open = function(URL, callback) {
		ok(true, "WebPage.open called");
		webPageData.opened.push(URL);
		callback("success");
	};
	// Always evaluate to true
	WebPage.prototype.evaluate = function() {
		ok(true, "WebPage.evaluate called");
		return true;
	};
	
	// Setup three arguments and call main
	phantom.args = ["a,b,c"];
	main();	
	
	// Wait for processing to complete
	setTimeout(function(){
		// Wait for processing to complete and verify that all three
		// documents were opened
		equals(webPageData.opened.length, 3, "Three arguments opened");
		equals(webPageData.opened[0], "a", "First argument opened");
		equals(webPageData.opened[1], "b", "First argument opened");
		equals(webPageData.opened[2], "c", "First argument opened");
		expect(11);
		start();
		
		// Restore mocked environment
		phantom = {};
		WebPage = undefined;
	}, 500);
});

/**
 * An error from phantom trying to load the page exits immediately
 */
asyncTest("Error loading page exits", function() {
	// Setup mocked environment
	phantom = {};
	phantom.exit = function() {
		ok(true, "phantom.exit called");
	};
	
	webPageData = {
		opened: []
	};
	WebPage = function(){};
	// Store all opened pages and run callback with failure
	WebPage.prototype.open = function(URL, callback) {
		ok(true, "WebPage.open called");
		webPageData.opened.push(URL);
		callback("failure");
	};
	
	// Set one argument and call main
	phantom.args = ["a"];
	main();	
	
	setTimeout(function(){
		// Expect an exit call
		expect(2);
		start();
		
		// Restore mocked environment
		phantom = {};
		WebPage = undefined;
	}, 25000);
});

/**
 * If we wait for too long to see the QUnit results in the HTML,
 * the test should time out and continue processing
 */
asyncTest("Waiting for page evaluation exits with 1 argument", function() {
	// Setup mocked environment
	phantom = {};
	phantom.exit = function() {
		ok(true, "phantom.exit called");
	};
	
	webPageData = {
		opened: []
	};
	WebPage = function(){};
	// Store all opened pages and run callback with success
	WebPage.prototype.open = function(URL, callback) {
		ok(true, "WebPage.open called");
		webPageData.opened.push(URL);
		callback("success");
	};
	// Always evaluate to true
	WebPage.prototype.evaluate = function() {
		return false;
	};
	
	// Set one argument and call main
	phantom.args = ["a"];
	main();	
	
	// Wait for timeout to complete and expect one page to have been opened
	setTimeout(function(){
		equals(webPageData.opened.length, 1, "One page opened");
		equals(webPageData.opened[0], "a", "First argument opened");
		expect(4);
		start();
		
		// Restore mocked environment
		phantom = {};
		WebPage = undefined;
	}, 25000);
});

/**
 * If we wait for too long to see the QUnit results in the HTML,
 * the test should time out and continue processing
 */
asyncTest("Waiting for page evaluation exits with >1 arguments", function() {
	// Setup mocked environment
	phantom = {};
	phantom.exit = function() {
		ok(true, "phantom.exit called");
	};
	
	webPageData = {
		opened: [],
		evaluated: false
	};
	WebPage = function(){};
	// Store all opened pages and run callback with success
	WebPage.prototype.open = function(URL, callback) {
		ok(true, "WebPage.open called");
		webPageData.opened.push(URL);
		callback("success");
	};
	// Evaluate to false first time, then true
	WebPage.prototype.evaluate = function() {
		if (webPageData.evaluated) {
			return true;
		} else {
			webPageData.evaluated = true;
			return false;
		}
	};
	
	// Set two arguments and call main
	phantom.args = ["a,b"];
	main();	
	
	// Expect first argument to time out and second to complete, verify
	// that both were opened.
	setTimeout(function(){
		equals(webPageData.opened.length, 2, "Two pages opened");
		equals(webPageData.opened[0], "a", "First argument opened");
		equals(webPageData.opened[1], "b", "Second argument opened");
		expect(6);
		start();
		
		// Restore mocked environment
		phantom = {};
		WebPage = undefined;
	}, 25000);
});

/**
 * QUnit completed success is correctly interpreted and test
 * completes.
 */
asyncTest("QUnit success state", function() {
	var qunitResults, actualXMLHttpRequest;
	
	// Setup mocked environment
	phantom = {};
	phantom.exit = function() {
		ok(true, "phantom.exit called");
	};
	
	webPageData = {
		opened: [],
		evaluated: false
	};
	WebPage = function(){};
	// Store all opened pages and run callback with success
	WebPage.prototype.open = function(URL, callback) {
		ok(true, "WebPage.open called");
		webPageData.opened.push(URL);
		callback("success");
	};
	// Run callback to evaluate
	WebPage.prototype.evaluate = function(callback) {
		ok(true, "WebPage.evaluate called");
		return callback();
	};
	// Set QUnit success code
	qunitResults = $("<span id='qunit-testresult'>completed</span>");
	$('body').append(qunitResults);
	// Mock three methods on XMLHttpRequest which all should be called
	actualXMLHttpRequest = {};
	actualXMLHttpRequest.open = XMLHttpRequest.prototype.open;
	actualXMLHttpRequest.setRequestHeader = XMLHttpRequest.prototype.setRequestHeader;
	actualXMLHttpRequest.send = XMLHttpRequest.prototype.send;
	XMLHttpRequest.prototype.open = function() {
		ok(true, "XMLHttpRequest.open called");
	};
	XMLHttpRequest.prototype.setRequestHeader = function() {
		ok(true, "XMLHttpRequest.setRequestHeader called");
	};
	XMLHttpRequest.prototype.send = function() {
		ok(true, "XMLHttpRequest.send called");
	};
	
	// Set one argument and call main
	phantom.args = ["a"];
	main();	
	
	// Expect 3 ajax calls + exit
	setTimeout(function(){
		expect(6);
		start();
		
		// Restore mocked environment
		qunitResults.remove();
		XMLHttpRequest.prototype.open = actualXMLHttpRequest.open;
		XMLHttpRequest.prototype.setRequestHeader = actualXMLHttpRequest.setRequestHeader;
		XMLHttpRequest.prototype.send = actualXMLHttpRequest.send;
		phantom = {};
		WebPage = undefined;
	}, 500);
	
});

/**
 * QUnit completed failure is correctly interpreted and leads
 * to a timeout.
 */
asyncTest("QUnit failure state", function() {
	var qunitResults, actualXMLHttpRequest;
	
	// Setup mocked environment
	phantom = {};
	phantom.exit = function() {
		ok(true, "phantom.exit called");
	};
	
	webPageData = {
		opened: [],
		evaluated: false
	};
	WebPage = function(){};
	// Store all opened pages and run callback with success
	WebPage.prototype.open = function(URL, callback) {
		ok(true, "WebPage.open called");
		webPageData.opened.push(URL);
		callback("success");
	};
	// Run callback to evaluate
	WebPage.prototype.evaluate = function(callback) {
		return callback();
	};
	// Set QUnit success code
	qunitResults = $("<span id='qunit-testresult'>failed</span>");
	$('body').append(qunitResults);
	// Mock three methods on XMLHttpRequest which all should not be called
	actualXMLHttpRequest = {};
	actualXMLHttpRequest.open = XMLHttpRequest.prototype.open;
	actualXMLHttpRequest.setRequestHeader = XMLHttpRequest.prototype.setRequestHeader;
	actualXMLHttpRequest.send = XMLHttpRequest.prototype.send;
	XMLHttpRequest.prototype.open = function() {
		ok(false, "XMLHttpRequest.open should not be called");
	};
	XMLHttpRequest.prototype.setRequestHeader = function() {
		ok(false, "XMLHttpRequest.setRequestHeader should not be called");
	};
	XMLHttpRequest.prototype.send = function() {
		ok(false, "XMLHttpRequest.send should not be called");
	};
	
	// Set one argument and call main
	phantom.args = ["a"];
	main();	
	
	// Expect 0 ajax calls + exit
	setTimeout(function(){
		expect(2);
		start();
		
		// Restore mocked environment
		qunitResults.remove();
		XMLHttpRequest.prototype.open = actualXMLHttpRequest.open;
		XMLHttpRequest.prototype.setRequestHeader = actualXMLHttpRequest.setRequestHeader;
		XMLHttpRequest.prototype.send = actualXMLHttpRequest.send;
		phantom = {};
		WebPage = undefined;
	}, 25000);
	
});

/**
 * Test the getTestResults and notify functions.
 * We can't access those functions in the sandboxed function so we
 * test them via the XMLHttpRequest.send method.
 */
asyncTest("QUnit test parsing", function() {
	var actualXMLHttpRequest;
	
	// Setup mocked environment
	phantom = {};
	phantom.exit = function() {
		ok(true, "phantom.exit called");
	};
	
	webPageData = {
		opened: [],
		evaluated: false
	};
	WebPage = function(){};
	// Store all opened pages and run callback with success
	WebPage.prototype.open = function(URL, callback) {
		ok(true, "WebPage.open called");
		webPageData.opened.push(URL);
		callback("success");
	};
	// Always evaluate to true
	WebPage.prototype.evaluate = function(callback) {
		ok(true, "WebPage.evaluate called");
		return callback();
	};
	// Set QUnit success code
	qunitResults = $("<span id='qunit-testresult'>completed</span>");
	$('body').append(qunitResults);
	// Mock three methods on XMLHttpRequest which all should not be called
	actualXMLHttpRequest = {};
	actualXMLHttpRequest.send = XMLHttpRequest.prototype.send;
	XMLHttpRequest.prototype.send = function(JSON) {
		var parsedJSON = $.parseJSON(JSON);
		equals(typeof parsedJSON.passes, "number", "Expect passes to be numeric");
		equals(typeof parsedJSON.failures, "number", "Expect failures to be numeric");
		equals(typeof parsedJSON.message, "string", "Expect messages to be a string");
		equals(typeof parsedJSON.testUrl, "string", "Expect testUrl to be a string");
	};
	
	// Set one argument and call main
	phantom.args = ["a"];
	main();	
	
	// Expect 0 ajax calls + exit
	setTimeout(function(){
		expect(7);
		start();
		
		// Restore mocked environment
		qunitResults.remove();
		XMLHttpRequest.prototype.send = actualXMLHttpRequest.send;
		phantom = {};
		WebPage = undefined;
	}, 500);
	
});
