<?php

/*	

Author by {phresco} QA Automation Team	

*/

require 'tests/NodeModules.php';
class AllTest extends PHPUnit_Framework_TestSuite {


	protected function setUp() {
	}

	public static function suite()
	{
		$testSuite = new AllTest('Phpunittest');
		$testSuite->addTestSuite('NodeModules');
		return $testSuite;
	}
	protected function tearDown() {
	}
	
}  
?>
