<?php
/*

Author by {phresco} QA Automation Team

*/
require_once 'Welcompage.php';



class  CommonSuite extends PHPUnit_Framework_TestSuite
{
 
 protected function setUp()
    {
	parent::setUp();
	}
 public static function suite()
    {
	$testSuite = new CommonSuite('CommonTestSuite');
    $testSuite->addTest(new Welcompage("testNewAC"));
	return $testSuite;
	}
	}
?>