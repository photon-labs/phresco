/*global $, QUnit, requestJSON */

var module = QUnit.module;
var equals = QUnit.equals;
var expect = QUnit.expect;
var ok = QUnit.ok;
var test = QUnit.test;

module("AjaxUtilsTest");

/**
 * Test requesting JSON. We mock the jQuery ajax function and call its success
 * and error callbacks so that we can test their logic.
 */
test("requestJSON", function() {
	var actualAjax, failureCount;

	failureCount = 0;

	actualAjax = $.ajax;
	$.ajax = function(opts) {
		opts.success('{"a": 1}');
		opts.error();
		opts.error();
	};
	try {
		requestJSON("GET", "something", "data", function(data) {
			equals(data.a, 1);
		}, function() {
			++failureCount;
		});

	} finally {
		$.ajax = actualAjax;
	}

	equals(failureCount, 1);

	expect(2);
});