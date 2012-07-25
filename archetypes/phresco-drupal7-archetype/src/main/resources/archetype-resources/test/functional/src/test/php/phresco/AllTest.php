<?php

/*Author by Phresco_Drupal_QA_Team*/

require_once 'tests/CommonSuite.php';
require_once 'tests/UserSuite.php';


class AllTest extends PHPUnit_Framework_TestSuite
{
 
 protected function setUp()
    {
		parent::setUp();
		}
 public static function suite()
    {
		$suite = new AllTest();
 		$suite->setName('AllTestsuite');
		$suite->addTestSuite(CommonSuite::suite());
		$suite->addTestSuite(UserSuite::suite());
		return $suite;
		
    }
 protected function tearDown()
    {
		parent::tearDown();
    }
}
?>