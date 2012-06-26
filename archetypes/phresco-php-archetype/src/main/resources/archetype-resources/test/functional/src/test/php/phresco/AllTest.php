<?php
/*	Author by {phresco} QA Automation Team	*/

require_once 'tests/UserModules.php';

class AllTest extends PHPUnit_Framework_TestSuite 
{
	protected function setUp(){
		parent::setUp();
	}
	public static function suite(){
	
		$suite = new AllTest();
		$suite->setName('AllTestsuite');
        $suite->addTest(UserModules::suite());
		return $suite;
    }
	protected function tearDown(){
		parent::tearDown();
	}
}
?>
