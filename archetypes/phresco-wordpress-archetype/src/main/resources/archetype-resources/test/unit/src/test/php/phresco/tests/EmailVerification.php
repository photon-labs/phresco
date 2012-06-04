<?php

/*	Author by {phresco} QA Automation Team	*/

require_once 'PHPUnit/Framework.php';
require_once 'phresco/tests/BaseScreen.php';
class EmailVerification extends PHPUnit_Framework_TestCase {
        public function setUp() 
		{
            $this->objValidator = new BaseScreen;
        }

        public function testEmail() 
		{
            $this->assertEquals(true, $this->objValidator->check_email('jhonson','co'));
        }
}
?>