<?php
/*Need to provide locations for each test case:

Author by {phresco} QA Automation Team

*/
require_once 'UserCreate_NewAC.php';



class  CommonSuite extends PHPUnit_Framework_TestSuite
{
 
 protected function setUp()
    {
	parent::setUp();
	}
 public static function suite()
    {
	$testSuite = new CommonSuite();
	$testSuite->setName('CommonTestSuite');
    $testSuite->addTestSuite('UserCreate_NewAC');
	return $testSuite;
	}
	}
?>