<?php
/*Need to provide locations for each test case:

Author by {phresco} QA Automation Team

*/
require_once 'CreateNewAC.php';



class  CommonAll extends PHPUnit_Framework_TestSuite
{
 
 protected function setUp()
    {
	parent::setUp();
	}
 public static function suite()
    {
	$testSuite = new CommonAll('CommonTestSuite');
    $testSuite->addTest(new CreateNewAC("testNewAC"));
	return $testSuite;
	}
	}
?>