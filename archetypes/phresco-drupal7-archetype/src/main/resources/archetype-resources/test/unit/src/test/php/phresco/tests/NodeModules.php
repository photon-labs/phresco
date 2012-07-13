<?php
/*	

Author by {phresco} QA Automation Team	

*/

require 'tests/Node.php';

class NodeModules extends PHPUnit_Framework_TestSuite {

	protected function setUp() {
	}

	public static function suite()
	{
		$testSuite = new NodeModules('NodeModules');
		$testSuite->addTest(new Node("testLastChanged"));
		$testSuite->addTest(new Node("testNodeTitle"));
		return $testSuite;
	}
	protected function tearDown() {
	}

} 
?>