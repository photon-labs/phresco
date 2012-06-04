var nodeunit = require('nodeunit');
var reporter = nodeunit.reporters.junit;

var opts = {
    "error_prefix": "\u001B[31m",
    "error_suffix": "\u001B[39m",
    "ok_prefix": "\u001B[32m",
    "ok_suffix": "\u001B[39m",
    "bold_prefix": "\u001B[1m",
    "bold_suffix": "\u001B[22m",
    "assertion_prefix": "\u001B[35m",
    "assertion_suffix": "\u001B[39m"
}

opts.output = "target/surefire";

reporter.run(['source/test/unit'], opts);