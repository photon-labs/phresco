<?php

/*
Author by Phresco_Drupal_QA_Team

*/

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
		$testSuite = new AllTest('DrupalAllTestSuite');
 		
		$testSuite->addTestSuite('CommonAll');
		$testSuite->addTestSuite('UserAll');
		
		return $testSuite;
    }
 protected function tearDown()
    {
		parent::tearDown();
    }
}
?>