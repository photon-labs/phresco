<?php
/*	Author by {phresco} QA Automation Team	*/

require_once 'Dashboard_Cat.php';


class UserModules extends PHPUnit_Framework_TestSuite 
{
	protected function setUp(){
		parent::setUp();
	}
	public static function suite(){
	
		$testSuite = new UserModules();
		$testSuite->setName('UserModules');
		$testSuite->addTestSuite('Dashboard_Cat');
		return $testSuite;
    }
    protected function tearDown(){
		
	}
}

?>