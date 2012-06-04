<?php

/*	Author by {phresco} QA Automation Team	*/

require_once 'PHPUnit/Framework.php';
require_once 'phresco/tests/BaseScreen.php';
class DateValidation extends PHPUnit_Framework_TestCase {
        public function setUp() 
		{
            $this->objValidator = new BaseScreen;
        }

        public function testDate() 
		{
            $this->assertEquals(true, $this->objValidator->check_dateformat('2012-01-03 08:00:00'));
        }
}
?>