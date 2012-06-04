<?php
/*	Author by {phresco} QA Automation Team	*/

require_once 'PhpCommonFun.php';
require_once 'PHPUnit/Extensions/SeleniumTestCase.php';

class Homepage extends PhpCommonFun
{
    protected function setUp(){ 
    	parent::setUp();
    }   
    public function testhomepage(){ 
    	parent::Browser();
	
    }
	public function tearDown(){
		parent::tearDown();
	}
	
}  
?>
