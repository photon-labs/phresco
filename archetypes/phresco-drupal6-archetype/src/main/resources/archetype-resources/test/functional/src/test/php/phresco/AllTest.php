<?php

/*Author by Phresco_Drupal_QA_Team*/

require_once 'tests/CommonAll.php';
require_once 'tests/UserAll.php';
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
        $suite->addTestSuite(CommonAll::suite());
        $suite->addTestSuite(UserAll::suite());
        return $suite;
    }
 protected function tearDown()
    {
		parent::tearDown();
    }
}
?>






