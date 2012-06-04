<?php
/*	Author by {phresco} QA Automation Team	*/


require_once 'Homepage.php';

class UserModules extends PHPUnit_Framework_TestSuite
{
	protected function setUp(){
		parent::setUp();
	}
	public static function suite(){
		$testSuite = new UserModules('HelloWorld');
		$testSuite->addTest(new Homepage("testhomepage"));
		return $testSuite;
    }
    protected function tearDown(){
		
	}
}
?>