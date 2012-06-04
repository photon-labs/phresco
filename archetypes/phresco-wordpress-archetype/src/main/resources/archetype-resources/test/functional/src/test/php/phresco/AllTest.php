<?php

/*
Author by Phresco_Drupal_QA_Team

*/

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
		$testSuite = new AllTest('DrupalAllTestSuite');
 		
		$testSuite->addTestSuite('CommonSuite');
		$testSuite->addTestSuite('UserSuite');
		
		return $testSuite;
    }
 protected function tearDown()
    {
		parent::tearDown();
    }
}
?>