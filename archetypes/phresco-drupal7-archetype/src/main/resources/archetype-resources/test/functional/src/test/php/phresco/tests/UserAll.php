<?php
/*Author by {phresco} QA Automation Team
*/
require_once 'Login_NewUsers.php';


class UserAll extends PHPUnit_Framework_TestSuite
{
 
 protected function setUp()
    {
		parent::setUp();
    }
 public static function suite()
    {
	$testSuite= new UserAll('UserTestSuite');
	$testSuite->addTest(new Login_NewUsers("testLogin"));
	return $testSuite;
	}
	}
?>