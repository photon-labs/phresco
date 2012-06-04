<?php

/*	Author by {phresco} QA Automation Team	*/

require_once 'tests/UsernameValidation.php';
require_once 'tests/DateValidation.php';
require_once 'tests/EmailVerification.php';
 
class AllTest extends PHPUnit_Framework_TestSuite
{
 
 protected function setUp()
    {
		
	}
 public static function suite()
    {
		$testSuite = new AllTest('Phpunittest');
		$testSuite->addTest(new UsernameValidation("testValidation"));
		$testSuite->addTest(new DateValidation("testDate"));
		$testSuite->addTest(new EmailVerification("testEmail"));
 		
		return $testSuite;
    }
 protected function tearDown()
    {
		
}

}

	


?>
