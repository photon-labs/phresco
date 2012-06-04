<?php
/*	

Author by {phresco} QA Automation Team	

*/

require_once 'tests/Home.php';
 
class AllTest extends PHPUnit_Framework_TestSuite
{
 
 protected function setUp()
    {
		
	}
 public static function suite()
    {
		$testSuite = new AllTest('Phpunittest');
		$testSuite->addTest(new Home("testString"));
 		
		return $testSuite;
    }
 protected function tearDown()
    {
		
    }
}

	


?>
